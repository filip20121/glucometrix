package com.example.glucometrix.calendar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glucometrix.*
import com.example.glucometrix.adapters.RecyclerAdapterEvent


class Calendar : AppCompatActivity() {

    private lateinit var curDate: String
    private var listDate = mutableListOf<String>()
    private var listDesc = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val rvItem: RecyclerView = findViewById(R.id.recycler_event)
        val layoutManager = LinearLayoutManager(this)
        val descView = findViewById<EditText>(R.id.CalendarDesc)
        val itemAdapter = RecyclerAdapterEvent(DatabaseHandler(this).showEventsDate(), DatabaseHandler(this).showEventsDesc())
        rvItem.adapter = itemAdapter
        rvItem.layoutManager = layoutManager

        val button: Button = findViewById(R.id.calendarButton)
           val calendarView: CalendarView = findViewById(R.id.myCalendar)
           calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
               button.setOnClickListener() {
                   curDate = "$dayOfMonth." + (month + 1).toString()
                   if (descView.text.toString() != "") {
                       DatabaseHandler(this).addEvent(curDate, descView.text.toString())
                       val itemAdapter = RecyclerAdapterEvent(DatabaseHandler(this).showEventsDate(), DatabaseHandler(this).showEventsDesc())
                       rvItem.adapter = itemAdapter
                       rvItem.layoutManager = layoutManager
                   }
               }
           })
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_glucose -> {
            val thread = Thread {
                startActivity(Intent(this, AddGluco::class.java))
            }
            thread.start()
            true
        }

        R.id.action_account -> {
            val thread = Thread {
                startActivity(Intent(this, Account::class.java))
            }
            thread.start()
            true
        }
        R.id.action_logout -> {
            val thread = Thread{
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
        title="Kalendarz"
        return super.onCreateOptionsMenu(menu)
    }
}