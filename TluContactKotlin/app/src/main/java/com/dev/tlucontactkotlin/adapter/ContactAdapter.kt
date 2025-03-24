package com.dev.tlucontactkotlin.ui.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.tlucontactkotlin.R
import com.dev.tlucontactkotlin.models.Staff
import com.dev.tlucontactkotlin.models.UnitContact
import com.dev.tlucontactkotlin.utils.ContactItem

class ContactAdapter(
    private var contacts: List<ContactItem>,
    private val contactType: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_STAFF = 1
        private const val TYPE_STUDENT = 2
        private const val TYPE_UNIT = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (contacts[position]) {
            is ContactItem.StaffItem -> TYPE_STAFF
            is ContactItem.StudentItem -> TYPE_STUDENT
            is ContactItem.UnitItem -> TYPE_UNIT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_STAFF -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_staff, parent, false)
                StaffViewHolder(view)
            }

//            TYPE_STUDENT -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_student, parent, false)
//                StudentViewHolder(view)
//            }

            TYPE_UNIT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_unit, parent, false)
                UnitViewHolder(view)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val contact = contacts[position]) {
            is ContactItem.StaffItem -> (holder as StaffViewHolder).bind(contact.staff)
            is ContactItem.UnitItem -> (holder as UnitViewHolder).bind(contact.unit)
            is ContactItem.StudentItem -> TODO()
        }
    }

    override fun getItemCount(): Int = contacts.size

    fun updateContacts(newContacts: List<ContactItem>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    class StaffViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgAvatar: ImageView = view.findViewById(R.id.img_logo_staff)
        private val txtName: TextView = view.findViewById(R.id.txt_staff_name)
        private val txtPosition: TextView = view.findViewById(R.id.txt_staff_position)
        private val txtPhone: TextView = view.findViewById(R.id.txt_staff_phone)
        private val txtEmail: TextView = view.findViewById(R.id.txt_staff_mail)

        fun bind(staff: Staff) {
            txtName.text = staff.fullName
            txtPosition.text = staff.position
            txtPhone.text = staff.phone
            txtEmail.text = staff.email
            Glide.with(itemView.context)
                .load(staff.photoURL ?: R.drawable.logo)
                .into(imgAvatar)
        }
    }

//    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val imgAvatar: ImageView = view.findViewById(R.id.img_avatar)
//        private val tvName: TextView = view.findViewById(R.id.tv_name)
//        private val tvClass: TextView = view.findViewById(R.id.tv_class)
//        private val tvPhone: TextView = view.findViewById(R.id.tv_phone)
//        private val tvEmail: TextView = view.findViewById(R.id.tv_email)
//
//        fun bind(student: Student) {
//            tvName.text = student.fullName
//            tvClass.text = "Lớp: ${student.className}"
//            tvPhone.text = "📞 ${student.phone}"
//            tvEmail.text = "✉️ ${student.email}"
//            Glide.with(itemView.context).load(student.photoURL).into(imgAvatar)
//        }
//    }

    class UnitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgLogo: ImageView = view.findViewById(R.id.img_logo_unit)
        private val txtName: TextView = view.findViewById(R.id.txt_unit_name)
        private val txtPhone: TextView = view.findViewById(R.id.txt_unit_phone)


        fun bind(unit: UnitContact) {
            txtName.text = unit.name
            txtPhone.text = unit.phone
            Glide.with(itemView.context).load(unit.logoURL).into(imgLogo)
        }
    }
}
