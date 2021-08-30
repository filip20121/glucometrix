package com.example.glucometrix

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glucometrix.adapters.RecyclerAdapter
import java.util.*


//TODO - add new measure of glucose and displaying results down-to not up-to and display correctly two recycler views
class GlucoResults : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gluco_results)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val array = DatabaseHandler(this).showDate()
        //Recycler view z datami
        val rvItem: RecyclerView = findViewById(R.id.recycler_view_results_glucose)
        val layoutManager = LinearLayoutManager(this@GlucoResults)
        val itemAdapter = RecyclerAdapter(array)
        rvItem.adapter = itemAdapter
        rvItem.layoutManager = layoutManager

        //changeColor()
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
        title = "Dzienniczek"
        return super.onCreateOptionsMenu(menu)
    }
}