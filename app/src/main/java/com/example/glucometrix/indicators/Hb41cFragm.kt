package com.example.glucometrix.indicators

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.glucometrix.R

class Hb41cFragm : Fragment() {

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hb41c, container, false)
        val button : Button = view.findViewById(R.id.buttonHbA1c)
        val HbA1c = view.findViewById<TextView>(R.id.HbA1cInsert)
        val HbA1ctext = view.findViewById<TextView>(R.id.HbA1c)
        val box = view.findViewById<TextView>(R.id.HbA1cBlock)

        button.setOnClickListener {
            if (HbA1c.text.toString() != "") {
                calcHbA1c(HbA1c.text.toString().toFloat(), HbA1ctext, box)
            } else{
                HbA1ctext.setTextColor(Color.rgb(255, 0, 0))
                HbA1ctext.text = "Pole nie może być puste"
            }
        }
        return view
    }

    private fun calcHbA1c(avg: Float, HbA1ctext: TextView, box:TextView) {
        var result = 0.0F
        if(avg < 126.0F) {
            result =  (avg - 96.0F) / (126.0F - 96.0F) + 5.0F
            box.setBackgroundColor(Color.rgb(0,255,0))
        }
        if(avg in 127.0F..154.0F) {
            result =  (avg - 127.0F) / (154.0F - 127.0F) + 6.0F
            box.setBackgroundColor(Color.rgb(137,255,0))
        }
        if(avg in 155.0F..183.0F) {
            result =  (avg - 155.0F) / (183.0F - 155.0F) + 7.0F
            box.setBackgroundColor(Color.rgb(232,232,0))
        }
        if(avg in 184.0F..212.0F) {
            result = (avg - 184.0F) / (212.0F - 184.0F) + 8.0F
            box.setBackgroundColor(Color.rgb(255,239,0))
        }
        if(avg in 213.0F..240.0F) {
            result = (avg - 213.0F) / (240.0F - 213.0F) + 9.0F
            box.setBackgroundColor(Color.rgb(255,171,0))
        }
        if(avg in 241.0F..269.0F) {
            result = (avg - 241.0F) / (269.0F - 241.0F) + 10.0F
            box.setBackgroundColor(Color.rgb(255,128,0))
        }
        if(avg > 270.0F) {
            result = (avg - 270.0F) / (300.0F - 270.0F) + 11.0F
            box.setBackgroundColor(Color.rgb(255,0,0))
        }
        HbA1ctext.text =  result.toString()
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            Hb41cFragm().apply {
                arguments = Bundle().apply {
                }
            }
    }
}