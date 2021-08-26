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
          if (checkBoxAverage.isChecked) {
              checkBoxAverage.isChecked = false
              val transaction = manager.beginTransaction()
              val fragment = AverageGlucose()

              transaction.replace(R.id.Wyniki, fragment)

              transaction.addToBackStack(null)
              transaction.commit()
          }

          if (checkBoxHb41c.isChecked) {

              checkBoxHb41c.isChecked = false
              val transaction = manager.beginTransaction()
              val fragment = Hb41cFragm()

              transaction.replace(R.id.Wyniki2, fragment)

              transaction.addToBackStack(null)
              transaction.commit()
          }

      }
  }
    fun avg(days:String, avgTxt: TextView){
        val numDays = DatabaseHandler(this).showNumDays()

        if (numDays >= days.toInt()){
            val avg = DatabaseHandler(this).showAllGlucose(days)
            avgTxt.text = avg.toString()
        }else{
            avgTxt.setTextColor(Color.rgb(255,0,0))
            val text = "max ilość dni wynosi $numDays"
            avgTxt.text = text
        }
    }
}