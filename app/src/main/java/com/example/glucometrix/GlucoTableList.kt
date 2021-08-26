package com.example.glucometrix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.glucometrix.R.id.recycler_view
import com.example.glucometrix.adapters.RecyclerAdapter
import com.example.glucometrix.dataClass.DateGlucose
import com.example.glucometrix.dataClass.GlucoseData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class GlucoTableList : Fragment(){

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private val hour = listOf("08:00",
            "10:00", "12:00", "14:00",
            "16:00", "18:00", "20:00",
            "22:00")

    private val glucose = listOf("120", "145",
            "101", "93",
            "168", "67",
            "113", "123")

    private val description = listOf("120", "145",
        "101", "93",
        "168", "67",
        "113", "123")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_gluco_table, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycler_view.apply {

            val listGlucoseDate : MutableList<GlucoseData> = ArrayList()
            for (i in 1..8){
               val item = GlucoseData(hour[i], glucose[i], description[i])
                listGlucoseDate.add(item)
            }
            val listGlucose = listOf(DateGlucose(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString(), listGlucoseDate))

            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapter(listGlucose)

        }
    }
}