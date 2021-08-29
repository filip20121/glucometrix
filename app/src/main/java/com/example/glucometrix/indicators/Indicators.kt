package com.example.glucometrix.indicators

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.glucometrix.*
import kotlin.math.roundToInt

class Indicators : AppCompatActivity() {
    private val manager = supportFragmentManager

  @SuppressLint("ShowToast")
  override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_indicators)
      setSupportActionBar(findViewById(R.id.my_toolbar))

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
    fun avg(days: String, avgTxt: TextView, box:TextView){
        if(days == "0"){
            avgTxt.setTextColor(Color.rgb(255, 0, 0))
            val text = "Ilość dni musi być większa od 0"
            avgTxt.text = text
            return
        }else {
            val numDays = DatabaseHandler(this).showNumDays()
            if (numDays >= days.toInt()) {
                val avg = DatabaseHandler(this).showAvgGlucose(days)

                if (avg < 126.0F) {
                    box.setBackgroundColor(Color.rgb(0, 255, 0))
                }
                if (avg in 127.0F..154.0F) {
                    box.setBackgroundColor(Color.rgb(137, 255, 0))
                }
                if (avg in 155.0F..183.0F) {
                    box.setBackgroundColor(Color.rgb(232, 232, 0))
                }
                if (avg in 184.0F..212.0F) {
                    box.setBackgroundColor(Color.rgb(255, 239, 0))
                }
                if (avg in 213.0F..240.0F) {
                    box.setBackgroundColor(Color.rgb(255, 171, 0))
                }
                if (avg in 241.0F..269.0F) {
                    box.setBackgroundColor(Color.rgb(255, 128, 0))
                }
                if (avg > 270.0F) {
                    box.setBackgroundColor(Color.rgb(255, 0, 0))
                }
                avgTxt.text = avg.roundToInt().toString()
            } else {
                avgTxt.setTextColor(Color.rgb(255, 0, 0))
                val text = "max ilość dni wynosi $numDays"
                avgTxt.text = text
                return
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_glucose -> {
            val thread = Thread{
                startActivity(Intent(this, AddGluco::class.java))
            }
            thread.start()
            true
        }

        R.id.action_account -> {
            val thread = Thread{
                startActivity(Intent(this, Account::class.java))
            }
            thread.start()
            true
        }
        R.id.action_logout -> {
            val thread = Thread{
                //val bundle = 3
                //intent.putExtra(DESC, bundle);
                startActivity(Intent(this, Login::class.java))
                finish()
            }
            thread.start()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        title = "Wskaźniki"
        return super.onCreateOptionsMenu(menu)
    }
}