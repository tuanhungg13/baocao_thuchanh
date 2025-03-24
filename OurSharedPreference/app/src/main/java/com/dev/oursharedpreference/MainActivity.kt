package com.dev.oursharedpreference

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
import com.dev.oursharedpreference.helper.PreferenceHelper
import com.dev.oursharedpreference.ui.theme.OurSharedPreferenceTheme

class MainActivity : ComponentActivity() {
    private lateinit var edtUsername : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnSave :Button
    private lateinit var btnLoad: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        edtUsername =findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        btnLoad = findViewById(R.id.btn_load)
        btnSave = findViewById(R.id.btn_save)
        btnDelete = findViewById(R.id.btn_delete)
//        txtInfo = findViewById(R.id.)

        val sharedPreferences = PreferenceHelper(getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE))

        btnSave.setOnClickListener{
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()
            sharedPreferences.saveData("USERNAME", username)
            sharedPreferences.saveData("PASSWORD", password)
            Toast.makeText(this,"Lưu thành công",Toast.LENGTH_SHORT).show()
            edtUsername.setText("")
            edtPassword.setText("")

        }

        btnLoad.setOnClickListener{
            val username = sharedPreferences.getData("USERNAME", "")
            val password = sharedPreferences.getData("PASSWORD", "")
            edtUsername.setText(username)
            edtPassword.setText(password)
        }

        btnDelete.setOnClickListener{
            val username = sharedPreferences.getData("USERNAME", "")
            val password = sharedPreferences.getData("PASSWORD", "")
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Chưa có dữ liệu hiển thị", Toast.LENGTH_SHORT).show()
            }
            else{
                sharedPreferences.removeData("USERNAME")
                sharedPreferences.removeData("PASSWORD")
                Toast.makeText(this,"Xóa thành công",Toast.LENGTH_SHORT).show()
                edtUsername.setText("")
                edtPassword.setText("")

            }
        }

    }
}
