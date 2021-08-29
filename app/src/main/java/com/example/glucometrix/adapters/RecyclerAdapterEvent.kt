package com.example.glucometrix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.glucometrix.R


class RecyclerAdapterEvent(private var listDate: List<String>, private var listDesc: List<String>)
    : RecyclerView.Adapter<RecyclerAdapterEvent.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterEvent.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listDate.size;
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemDate:TextView = itemView.findViewById(R.id.EventDate)
        var itemDesc:TextView = itemView.findViewById(R.id.EventTitle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDate.text = listDate[position]
        holder.itemDesc.text = listDesc[position]
    }

}