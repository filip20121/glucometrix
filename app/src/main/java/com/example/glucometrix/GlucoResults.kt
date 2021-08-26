package com.example.glucometrix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glucometrix.adapters.RecyclerAdapter
import com.example.glucometrix.dataClass.DateGlucose
import com.example.glucometrix.dataClass.GlucoseData
import java.util.*


//TODO - add new measure of glucose and displaying results down-to not up-to and display correctly two recycler views
class GlucoResults : AppCompatActivity() {
    var manager = supportFragmentManager
    var glucoseList = mutableListOf("120", "145",
            "101", "93",
            "168", "67",
            "113", "123")

    var descriptionList = mutableListOf("after wake up",
            "before breakfast", "after breakfast", "before dinner",
            "after dinner", "before supper", "after supper",
            "before sleep")
    var hourList = mutableListOf("08:00",
            "10:00", "12:00", "14:00",
            "16:00", "18:00", "20:00",
            "22:00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gluco_results)
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
            showGluco()
        }
        //changeColor()
    }
    private fun buildItemList(): List<DateGlucose> {
        val itemList: MutableList<DateGlucose> = ArrayList()
        for (i in glucoseList.size-1 downTo 0) {
           val item = DateGlucose("26.08.2021", buildSubItemList())
            itemList.add(item)
        }
        return itemList
    }

    private fun buildSubItemList(): List<GlucoseData> {
        val subItemList: MutableList<GlucoseData> = ArrayList()
        for (i in glucoseList.size-1 downTo 0) {
            val subItem = GlucoseData(hourList[i], glucoseList[i], descriptionList[i])
            subItemList.add(subItem)
        }
        return subItemList
    }
    private fun showGluco() {
        val transaction = manager.beginTransaction()
        val fragment = GlucoTableList()

       // transaction.replace(R.id.fragment_date_glucose, fragment)

        transaction.addToBackStack(null)
        transaction.commit()
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