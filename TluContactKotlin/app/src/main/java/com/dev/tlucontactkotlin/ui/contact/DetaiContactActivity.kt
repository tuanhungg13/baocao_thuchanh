package com.dev.tlucontactkotlin.ui.contact

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.tlucontactkotlin.R
import com.dev.tlucontactkotlin.models.Staff
import com.dev.tlucontactkotlin.models.Student
import com.dev.tlucontactkotlin.models.UnitContact

class DetailActivity : AppCompatActivity() {

    private lateinit var txtName: TextView
    private lateinit var txtIdOrCode: TextView
    private lateinit var txtPositionOrClass: TextView
    private lateinit var txtPhone: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtAddress: TextView
    private lateinit var imgLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val txtName = findViewById<TextView>(R.id.txt_name)
        val imgLogo = findViewById<ImageView>(R.id.img_avatar)

        // Truy cập từng dòng thông tin
        val rowId = findViewById<View>(R.id.row_id)
        val rowPosition = findViewById<View>(R.id.row_position)
        val rowPhone = findViewById<View>(R.id.row_phone)
        val rowEmail = findViewById<View>(R.id.row_email)
        val rowAddress = findViewById<View>(R.id.row_address)

        // Gán label cho từng dòng (1 lần duy nhất)
        rowId.findViewById<TextView>(R.id.txt_label).text = "Mã:"
        rowPhone.findViewById<TextView>(R.id.txt_label).text = "SĐT:"
        rowEmail.findViewById<TextView>(R.id.txt_label).text = "Email:"
        rowAddress.findViewById<TextView>(R.id.txt_label).text = "Địa chỉ:"

        val type = intent.getStringExtra("type")
        when (type) {
            "staff" -> {
                val staff = intent.getSerializableExtra("data") as? Staff
                staff?.let {
                    rowPosition.findViewById<TextView>(R.id.txt_label).text = "Chức vụ:"
                    txtName.text = it.fullName
                    rowId.findViewById<TextView>(R.id.txt_value).text = it.id
                    rowPosition.findViewById<TextView>(R.id.txt_value).text = it.position
                    rowPhone.findViewById<TextView>(R.id.txt_value).text = it.phone
                    rowEmail.findViewById<TextView>(R.id.txt_value).text = it.email
//                    rowAddress.findViewById<TextView>(R.id.txt_value).text = it.address
                    Glide.with(this)
                        .load(it.photoURL) // thay bằng biến ảnh từ object
                        .placeholder(R.drawable.logo) // ảnh mặc định khi chưa tải xong
                        .error(R.drawable.logo)       // ảnh khi tải lỗi
                        .into(imgLogo)
                }
            }

            "student" -> {
                val student = intent.getSerializableExtra("data") as? Student
                student?.let {
                    rowPosition.findViewById<TextView>(R.id.txt_label).text = "Lớp:"
                    txtName.text = it.fullName
                    rowId.findViewById<TextView>(R.id.txt_value).text = it.id
                    rowPosition.findViewById<TextView>(R.id.txt_value).text = it.className
                    rowPhone.findViewById<TextView>(R.id.txt_value).text = it.phone
                    rowEmail.findViewById<TextView>(R.id.txt_value).text = it.email
                    rowAddress.findViewById<TextView>(R.id.txt_value).text = it.address
                    Glide.with(this)
                        .load(it.photoURL) // thay bằng biến ảnh từ object
                        .placeholder(R.drawable.logo) // ảnh mặc định khi chưa tải xong
                        .error(R.drawable.logo)       // ảnh khi tải lỗi
                        .into(imgLogo)
                }
            }

            "unit" -> {
                val unit = intent.getSerializableExtra("data") as? UnitContact
                unit?.let {
                    txtName.text = it.name
                    rowId.visibility = View.GONE
                    rowPosition.findViewById<TextView>(R.id.txt_value).text = ""
                    rowPhone.findViewById<TextView>(R.id.txt_value).text = it.phone
                    rowEmail.findViewById<TextView>(R.id.txt_value).text = it.email
                    rowAddress.findViewById<TextView>(R.id.txt_value).text = it.address
                    imgLogo.setImageResource(R.drawable.logo)
                }
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish() // 👈 Quay lại ContactActivity mà không tạo Intent mới
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}
