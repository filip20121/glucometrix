package com.example.glucometrix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.glucometrix.R
import com.example.glucometrix.dataClass.GlucoseData

class RecyclerSubAdapter(private var subItemList: List<GlucoseData>)
    : RecyclerView.Adapter<RecyclerSubAdapter.SubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_sub_item, parent, false)
        return SubViewHolder(v)
    }


    override fun onBindViewHolder(subItemViewHolder: SubViewHolder, position: Int) {
        val subItem: GlucoseData = subItemList[position]
        subItemViewHolder.itemHour.text = subItem.getSubItemHour()
        subItemViewHolder.itemGlucose.text = subItem.getSubItemGlucose()
        subItemViewHolder.itemDescription.text = subItem.getSubItemDesc()
    }

    override fun getItemCount(): Int {
        return subItemList.size;
    }

    inner class SubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemHour: TextView = itemView.findViewById(R.id.textHour)
        var itemGlucose: TextView = itemView.findViewById(R.id.textGlucose)
        var itemDescription: TextView = itemView.findViewById(R.id.textDescription)
    }
}