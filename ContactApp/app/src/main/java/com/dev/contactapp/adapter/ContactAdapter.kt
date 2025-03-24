package com.dev.contactapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.contactapp.R
import com.dev.contactapp.models.Contact

class ContactAdapter(private val contacts: List<Contact>):
RecyclerView.Adapter<ContactAdapter.ContactViewHolder>()
{
    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txt_name)
        val txtPhone: TextView = view.findViewById(R.id.txt_phone)
        val txtId: TextView = view.findViewById(R.id.txt_id)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactAdapter.ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.txtId.text = contact.id.toString()
        holder.txtName.text = contact.name
        holder.txtPhone.text = contact.phone
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}