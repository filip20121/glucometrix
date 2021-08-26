package com.example.glucometrix.indicators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.glucometrix.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 *

 */
class Hb41cFragm : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hb41c, container, false)
        val button : Button = view.findViewById(R.id.buttonHbA1c)
        val HbA1c = view.findViewById<TextView>(R.id.HbA1cInsert)
        val HbA1ctext = view.findViewById<TextView>(R.id.HbA1c)

        button.setOnClickListener(){
            HbA1ctext.text = calcHbA1c(HbA1c.text.toString().toFloat()).toString()
        }
        return view
    }

    private fun calcHbA1c(avg: Float): Float {
        var result: Float = 0.0F
        if(avg in 96.0F..126.0F) {
            result =  (avg - 96.0F) / (126.0F - 96.0F) + 5.0F
        }
        if(avg in 127.0F..154.0F) {
            result =  (avg - 127.0F) / (154.0F - 127.0F) + 6.0F
        }
        if(avg in 155.0F..183.0F) {
            result =  (avg - 155.0F) / (183.0F - 155.0F) + 7.0F
        }
        if(avg in 184.0F..212.0F) {
            result = (avg - 184.0F) / (212.0F - 184.0F) + 8.0F
        }
        if(avg in 213.0F..240.0F) {
            result = (avg - 213.0F) / (240.0F - 213.0F) + 9.0F
        }
        if(avg in 241.0F..269.0F) {
            result = (avg - 241.0F) / (269.0F - 241.0F) + 10.0F
        }
        if(avg in 270.0F..300.0F) {
            result = (avg - 270.0F) / (300.0F - 270.0F) + 11.0F
        }
        return result
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Hb41cFragm.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Hb41cFragm().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun calcHbA1c(){

    }
}