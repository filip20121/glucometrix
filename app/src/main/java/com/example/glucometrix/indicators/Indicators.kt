package com.example.glucometrix.indicators

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.glucometrix.DatabaseHandler
import com.example.glucometrix.R

class Indicators : AppCompatActivity() {
    private val manager = supportFragmentManager

  @SuppressLint("ShowToast")
  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_indicators)
        val checkBoxAverage = findViewById<CheckBox>(R.id.checkBoxAverage)
        val checkBoxHb41c = findViewById<CheckBox>(R.id.checkBoxHbA1)
        val button = findViewById<Button>(R.id.butttonSubmit)

      button.setOnClickListener() {
          if(checkBoxHb41c.isChecked && checkBoxAverage.isChecked){
              val transaction = manager.beginTransaction()
              val fragment = AverageGlucose()
              val fragment1 = Hb41cFragm()

              transaction.replace(R.id.Wyniki2, fragment1)
              transaction.replace(R.id.Wyniki, fragment)
              transaction.addToBackStack(null)
              transaction.commit()
          }else {
              if (checkBoxAverage.isChecked) {
                  //checkBoxAverage.isChecked = false
                  val transaction = manager.beginTransaction()
                  val fragment = AverageGlucose()

                  transaction.replace(R.id.Wyniki, fragment)

                  transaction.addToBackStack(null)
                  transaction.commit()
              }

              if (checkBoxHb41c.isChecked) {

                  //checkBoxHb41c.isChecked = false
                  val transaction = manager.beginTransaction()
                  val fragment = Hb41cFragm()

                  transaction.replace(R.id.Wyniki2, fragment)

                  transaction.addToBackStack(null)
                  transaction.commit()
              }
          }

      }
  }
    override fun onBackPressed() {
        val checkBoxAverage = findViewById<CheckBox>(R.id.checkBoxAverage)
        val checkBoxHb41c = findViewById<CheckBox>(R.id.checkBoxHbA1)
        if(checkBoxHb41c.isChecked){
            checkBoxHb41c.isChecked = false
        }
        if(checkBoxAverage.isChecked){
            checkBoxAverage.isChecked = false
        }
            super.onBackPressed()

    }
    fun avg(days: String, avgTxt: TextView){
        val numDays = DatabaseHandler(this).showNumDays()

        if (numDays >= days.toInt()){
            val avg = DatabaseHandler(this).showAvgGlucose(days)

            if(avg < 126.0F) {
                avgTxt.setTextColor(Color.rgb(0,255,0))
            }
            if(avg in 127.0F..154.0F) {
                avgTxt.setTextColor(Color.rgb(137,255,0))
            }
            if(avg in 155.0F..183.0F) {
                avgTxt.setTextColor(Color.rgb(232,232,0))
            }
            if(avg in 184.0F..212.0F) {
                avgTxt.setTextColor(Color.rgb(255,239,0))
            }
            if(avg in 213.0F..240.0F) {
                avgTxt.setTextColor(Color.rgb(255,171,0))
            }
            if(avg in 241.0F..269.0F) {
                avgTxt.setTextColor(Color.rgb(255,128,0))
            }
            if(avg > 270.0F) {
                avgTxt.setTextColor(Color.rgb(255,0,0))
            }
            avgTxt.text = avg.toString()
        }else{
            avgTxt.setTextColor(Color.rgb(255, 0, 0))
            val text = "max ilość dni wynosi $numDays"
            avgTxt.text = text
        }
    }
}