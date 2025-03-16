package com.dev.tlucontactkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
import com.dev.tlucontactkotlin.ui.auth.AuthActivity
import com.dev.tlucontactkotlin.ui.contact.ContactActivity
import com.dev.tlucontactkotlin.ui.theme.TluContactKotlinTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private lateinit var btnUnitContact: Button
    private lateinit var btnStaffContact: Button
    private lateinit var btnStudentContact: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        btnUnitContact = findViewById(R.id.btn_unit_contact)
        btnStaffContact = findViewById(R.id.btn_staff_contact)
        btnStudentContact = findViewById(R.id.btn_students_contact)

        if (auth.currentUser == null) {
            // Nếu chưa đăng nhập, chuyển đến AuthActivity
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        btnUnitContact.setOnClickListener{
            openContactActivity("unit")
        }
        btnStudentContact.setOnClickListener{
            openContactActivity("student")
        }
        btnStaffContact.setOnClickListener{
            openContactActivity("staff")
        }

    }

    private fun openContactActivity(contactType: String) {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("CONTACT_TYPE", contactType)
        startActivity(intent)
    }
}