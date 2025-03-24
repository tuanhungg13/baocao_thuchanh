package com.dev.tlucontactkotlin.ui.contact

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.tlucontactkotlin.R
import com.dev.tlucontactkotlin.models.Staff
import com.dev.tlucontactkotlin.models.UnitContact
import com.dev.tlucontactkotlin.utils.ContactItem
import com.google.firebase.firestore.FirebaseFirestore

class ContactActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyText: TextView
    private val db = FirebaseFirestore.getInstance()
    private val contactList = mutableListOf<ContactItem>()
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        recyclerView = findViewById(R.id.rcv_contact)
        emptyText = findViewById(R.id.emptyText)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val contactType = intent.getStringExtra("CONTACT_TYPE") ?: return

        adapter = ContactAdapter(
            contactList,
            contactType = "CONTACT_TYPE"
        )
        recyclerView.adapter = adapter


        fetchContacts(contactType)
    }

    private fun fetchContacts(contactType: String) {
        db.collection("contacts")
            .whereEqualTo("type", contactType)
            .get()
            .addOnSuccessListener { documents ->
                contactList.clear()
                for (document in documents) {
                    try {
                        when (contactType) {
                            "staff" -> {
                                document.let {
                                    val staff = Staff(
                                        fullName = it.getString("name") ?: "Không có tên",
                                        position = it.getString("position") ?: "Không có chức vụ",
                                        phone = it.getString("phone") ?: "Không có số điện thoại",
                                        email = it.getString("email") ?: "Không có email",
                                        photoURL = it.getString("photoURL") ?: ""
                                    )
                                    contactList.add(ContactItem.StaffItem(staff))
                                }
                            }

                            "unit" -> {
                                document.let {
                                    val unit = UnitContact(
                                        name = it.getString("name") ?: "Không có tên",
                                        phone = it.getString("phone") ?: "Không có số điện thoại",
                                        logoURL = it.getString("logoURL") ?: ""
                                    )
                                    contactList.add(ContactItem.UnitItem(unit))
                                }
                            }

                            "student" -> {
                                // Xử lý danh bạ sinh viên nếu cần
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

