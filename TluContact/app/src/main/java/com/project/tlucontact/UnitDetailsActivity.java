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

import com.project.tlucontact.model.Unit;

public class UnitDetailsActivity extends AppCompatActivity {
    private TextView txtUnitName, txtUnitPhone, txtUnitEmail, txtUnitAddress, txtUid;
    private ImageView imgLogo;

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.unit_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgLogo = findViewById(R.id.img_logo_unit);
        txtUnitName = findViewById(R.id.txt_unit_name);
        txtUnitPhone = findViewById(R.id.txt_unit_phone);
        txtUnitEmail = findViewById(R.id.txt_unit_email);
        txtUnitAddress = findViewById(R.id.txt_unit_address);
        txtUid = findViewById(R.id.txt_unit_uid);

        // Nhận dữ liệu từ Intent
        Unit unit = (Unit) getIntent().getSerializableExtra("unit");
        if (unit != null) {
            imgLogo.setImageResource(unit.getLogoUnit());
            txtUnitName.setText(unit.getName());
            txtUnitPhone.setText(unit.getPhone());
            txtUnitEmail.setText(unit.getEmail());
            txtUnitAddress.setText(unit.getAddress());
        }
    }
}