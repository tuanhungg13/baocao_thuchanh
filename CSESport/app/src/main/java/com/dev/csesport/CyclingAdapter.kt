package com.dev.csesport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CyclingAdapter(private val runs: List<CycleItem>) :
    RecyclerView.Adapter<CyclingAdapter.CycleViewHolder>() {

    class CycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtCycleType)
        val time: TextView = itemView.findViewById(R.id.txtDate)
        val date: TextView = itemView.findViewById(R.id.txtTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CycleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_running, parent, false)
        return CycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CycleViewHolder, position: Int) {
        val run = runs[position]
        holder.name.text = run.type
        holder.time.text = run.time
        holder.date.text = run.date
    }

    override fun getItemCount() = runs.size
}
