package com.example.glucometrix.indicators

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.glucometrix.R


class AverageGlucose : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_average_glucose, container, false)
        val button: Button = view.findViewById(R.id.butttonAVG)
        val days: EditText = view.findViewById(R.id.daysAverage)
        val avg: TextView = view.findViewById(R.id.resultAverage)
        val box = view.findViewById<TextView>(R.id.AvgBlock)

        button.setOnClickListener {
            if (days.text.toString() != "") {
                avg.text = days.text.toString().toInt().toString()
                (activity as Indicators?)!!.avg(avg.text as String, avg, box)
            }
            else{
                avg.setTextColor(Color.rgb(255, 0, 0))
                avg.text = "Pole nie może być puste"
            }
        }
        return view
    }

}