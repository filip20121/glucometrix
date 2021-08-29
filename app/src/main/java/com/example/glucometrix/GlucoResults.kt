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
        val itemAdapter = RecyclerAdapter(array)//buildItemList())
        rvItem.adapter = itemAdapter
        rvItem.layoutManager = layoutManager

       /* val recycler_view_sub = findViewById<RecyclerView>(R.id.recycler_view_results_glucose_sub)
        recycler_view_sub.layoutManager = LinearLayoutManager(this)
        recycler_view_sub.adapter = RecyclerSubAdapter(buildSubItemList())*/

        rvItem.setOnClickListener(){

        }
        //changeColor()
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_glucose -> {
            val thread = Thread{
                //val bundle = 3
                //intent.putExtra(DESC, bundle);
                startActivity(Intent(this, AddGluco::class.java))
            }
            thread.start()
            true
        }

        R.id.action_account -> {
            val thread = Thread{
                //val bundle = 3
                //intent.putExtra(DESC, bundle);
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
        title = "Dzienniczek"
        return super.onCreateOptionsMenu(menu)
    }
    //TODO - add red color for low and high glucose
    /*private fun changeColor() {
        for(i in 0..1) {
            //val name = "textView$i"
            val id = resources.getIdentifier(R.id.textGlucose.toString(), "id", packageName)
            if (id != 0) {
                val glucoseText = findViewById<TextView>(id)
                val glucose = glucoseText.text.toString()

                if (glucose.toInt() < 70 || glucose.toInt() > 199) {
                    glucoseText.setTextColor(Color.RED)
                }
            }
        }
    }*/

}