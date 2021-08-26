package com.example.glucometrix.indicators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.glucometrix.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AverageGlucose : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
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

        button.setOnClickListener{
            avg.text = days.text.toString().toInt().toString()
            (activity as Indicators?)!!.avg(avg.text as String, avg)
        }
        return view
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            AverageGlucose().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}