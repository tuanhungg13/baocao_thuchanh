package com.dev.contactapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.RecyclerView
import com.dev.contactapp.adapter.ContactAdapter
import com.dev.contactapp.ui.theme.ContactAppTheme
import com.dev.contactapp.utils.DatabaseHelper
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    private lateinit var edtUsername : EditText
    private lateinit var edtPhone : EditText
    private lateinit var btnSave: Button
    private lateinit var btnView: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var edtId: EditText
    private lateinit var rcvContact : RecyclerView
    private lateinit var contactAdapter: ContactAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val db = DatabaseHelper(this)

        edtUsername = findViewById(R.id.edt_username)
        edtPhone = findViewById(R.id.edt_phone)
        btnSave = findViewById(R.id.btn_save)
        btnView = findViewById(R.id.btn_load)
        btnDelete = findViewById(R.id.btn_delete)
        edtId = findViewById(R.id.edt_id)
        btnUpdate = findViewById(R.id.btn_update)
        rcvContact = findViewById(R.id.rcv_contact)

        btnSave.setOnClickListener {
            if(edtId.text.toString().toIntOrNull() == null){
                Toast.makeText(this,"Id phải là số", Toast.LENGTH_SHORT).show()
            }
            else if (db.isIdExists(edtId.text.toString().toInt())){
                Toast.makeText(this,"Id đã tồn tại", Toast.LENGTH_SHORT).show()
            }
            else{
                val id = edtId.text.toString().toInt()
                val name = edtUsername.text.toString()
                val phone = edtPhone.text.toString()
                db.CreateContact(id,name, phone)
                getAllContacts()
                edtId.text.clear()
                edtUsername.text.clear()
                edtPhone.text.clear()
                Toast.makeText(this,"Lưu thành công", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            val name = edtUsername.text.toString()
            val phone = edtPhone.text.toString()
            val id = edtId.text.toString().toInt()
            db.UpdateContact(id,name, phone)
            getAllContacts()
            Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_SHORT).show()
        }

        btnDelete.setOnClickListener {
            val id = edtId.text.toString().toInt()
            db.deleteContact(id)
            getAllContacts()
            Toast.makeText(this,"Xóa thành công", Toast.LENGTH_SHORT).show()
        }

        btnView.setOnClickListener {
            getAllContacts()
        }

    }

    fun getAllContacts(){
        val db = DatabaseHelper(this)
        val contacts = db.getAllContacts()
        contactAdapter = ContactAdapter(contacts)
        rcvContact.adapter = contactAdapter
    }

}

