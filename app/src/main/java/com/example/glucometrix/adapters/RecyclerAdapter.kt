package com.example.glucometrix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.example.glucometrix.R
import com.example.glucometrix.dataClass.DateGlucose


class RecyclerAdapter(private var itemList: List<DateGlucose>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private val viewPool = RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(itemViewHolder: ViewHolder, position: Int) {
        val item = itemList[position]
        itemViewHolder.itemDate.text = item.getItemDate()

        val layoutManager = LinearLayoutManager(
                itemViewHolder.recyclerView.context,
                LinearLayoutManager.VERTICAL,
                false
        )
        layoutManager.initialPrefetchItemCount = item.getSubItemList().size

        val subItemAdapter = RecyclerSubAdapter(item.getSubItemList())

        itemViewHolder.recyclerView.layoutManager = layoutManager
        itemViewHolder.recyclerView.adapter = subItemAdapter
        itemViewHolder.recyclerView.setRecycledViewPool(viewPool)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemDate:TextView = itemView.findViewById(R.id.textDate)
        var recyclerView:RecyclerView = itemView.findViewById(R.id.recycler_view_results_glucose_sub)
    }

}