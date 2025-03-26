package com.dev.tlucontactkotlin.ui.contact

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.tlucontactkotlin.R
import com.dev.tlucontactkotlin.models.Staff
import com.dev.tlucontactkotlin.models.Student
import com.dev.tlucontactkotlin.models.UnitContact
import com.dev.tlucontactkotlin.utils.ContactItem
import com.google.firebase.firestore.FirebaseFirestore

class ContactActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyText: TextView
    private val db = FirebaseFirestore.getInstance()
    private val contactList = mutableListOf<ContactItem>()
    private lateinit var adapter: ContactAdapter
    private lateinit var contactType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        recyclerView = findViewById(R.id.rcv_contact)
        emptyText = findViewById(R.id.emptyText)

        recyclerView.layoutManager = LinearLayoutManager(this)
        contactType = intent.getStringExtra("CONTACT_TYPE") ?: return

        adapter = ContactAdapter(
            contactList,
            contactType = contactType
        ) { item ->
            when (item) {
                is ContactItem.StaffItem -> {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("type", "staff")
                    intent.putExtra("data", item.staff)
                    startActivity(intent)
                }

                is ContactItem.StudentItem -> {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("type", "student")
                    intent.putExtra("data", item.student)
                    startActivity(intent)
                }

                is ContactItem.UnitItem -> {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("type", "unit")
                    intent.putExtra("data", item.unit)
                    startActivity(intent)
                }
            }
        }

        recyclerView.adapter = adapter


        fetchContacts(contactType)
    }

    override fun onResume() {
        super.onResume()
        fetchContacts(contactType) // ✅ gọi load lại dữ liệu mỗi lần quay lại activity
    }

    private fun fetchContacts(contactType: String) {
        val collectionName = when (contactType) {
            "staff" -> "staffs"
            "unit" -> "units"
            "student" -> "students"
            else -> {
                emptyText.text = "Loại danh bạ không hợp lệ."
                emptyText.visibility = TextView.VISIBLE
                return
            }
        }

        db.collection(collectionName)
            .get()
            .addOnSuccessListener { documents ->
                contactList.clear()
                for (document in documents) {
                    try {
                        when (contactType) {
                            "staff" -> {
                                val staff = Staff(
                                    fullName = document.getString("fullName") ?: "Không có tên",
                                    position = document.getString("position") ?: "Không có chức vụ",
                                    phone = document.getString("phone") ?: "Không có số điện thoại",
                                    email = document.getString("email") ?: "Không có email",
                                    photoURL = document.getString("photoURL") ?: ""
                                )
                                contactList.add(ContactItem.StaffItem(staff))
                            }

                            "unit" -> {
                                val unit = UnitContact(
                                    name = document.getString("name") ?: "Không có tên",
                                    phone = document.getString("phone") ?: "Không có số điện thoại",
                                    logoURL = document.getString("logoURL") ?: "",
                                    address = document.getString("address") ?: "Không có địa chỉ",
                                    email = document.getString("email") ?: "Không có email"
                                )
                                contactList.add(ContactItem.UnitItem(unit))
                            }

                            "student" -> {
                                val student = Student(
                                    fullName = document.getString("fullName") ?: "Không có tên",
                                    phone = document.getString("phone") ?: "Không có số điện thoại",
                                    photoURL = document.getString("photoURL") ?: "",
                                    email = document.getString("email") ?: "Không có email",
                                    className = document.getString("className") ?: "",
                                )
                                contactList.add(ContactItem.StudentItem(student))
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                adapter.updateContacts(contactList)
                emptyText.visibility =
                    if (contactList.isEmpty()) TextView.VISIBLE else TextView.GONE
            }
            .addOnFailureListener { exception ->
                emptyText.text = "Lỗi khi tải danh bạ: ${exception.localizedMessage}"
                emptyText.visibility = TextView.VISIBLE
            }
    }

}

