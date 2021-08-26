package com.example.glucometrix.Chart

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.example.glucometrix.DatabaseHandler
import com.example.glucometrix.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class MyMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private var iter: Int = 0
    var glucoseList = listOf<String>()
    var descriptionList = listOf<String>()

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
            descriptionList = DatabaseHandler(context).showDescList()
               glucoseList = DatabaseHandler(context).showGlucos()
        for(el in 0 .. glucoseList.size){
            if(glucoseList[el].toFloat() == highlight?.y){
                iter = el
                break
            }
        }
        tvContent.text = descriptionList[iter]
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF? {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}