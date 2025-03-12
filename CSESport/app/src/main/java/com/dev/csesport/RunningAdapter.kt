package com.dev.csesport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RunningAdapter(private val runs: List<RunItem>) :
    RecyclerView.Adapter<RunningAdapter.RunViewHolder>() {

    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtRunType)
        val time: TextView = itemView.findViewById(R.id.txtDate)
        val date: TextView = itemView.findViewById(R.id.txtTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_running, parent, false)
        return RunViewHolder(view)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = runs[position]
        holder.name.text = run.type
        holder.time.text = run.time
        holder.date.text = run.date
    }

    override fun getItemCount() = runs.size
}

