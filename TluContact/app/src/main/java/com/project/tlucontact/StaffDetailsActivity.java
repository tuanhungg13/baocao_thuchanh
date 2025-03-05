package com.project.tlucontact;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.tlucontact.model.Staff;
import com.project.tlucontact.model.Unit;

public class StaffDetailsActivity extends AppCompatActivity {
    private TextView txtStaffFullName, txtStaffPhone, txtStaffEmail, txtStaffAddress, txtPosition, txtStaffId;
    private ImageView imgAvatar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_staff_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgAvatar = findViewById(R.id.img_logo_staff);
        txtStaffFullName = findViewById(R.id.txt_staff_name);
        txtStaffPhone = findViewById(R.id.txt_staff_phone);
        txtStaffEmail = findViewById(R.id.txt_staff_email);
        txtStaffAddress = findViewById(R.id.txt_staff_address);
        txtPosition = findViewById(R.id.txt_staff_position);
        txtStaffId = findViewById(R.id.txt_staff_id);

        // Nhận dữ liệu từ Intent
        Staff staff = (Staff) getIntent().getSerializableExtra("staff");
        if (staff != null) {
            imgAvatar.setImageResource(staff.getAvatar());
            txtStaffFullName.setText(staff.getFullName());
            txtStaffPhone.setText(staff.getPhone());
            txtStaffEmail.setText(staff.getEmail());
            txtStaffAddress.setText(staff.getAddress());
            txtPosition.setText(staff.getPosition());
            txtStaffId.setText(staff.getStaffId());
        }
    }
}