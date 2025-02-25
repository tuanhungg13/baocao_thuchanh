package com.example.testproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RadioButton rdoMale, rdoFemale;
    private EditText edtAge,edtFeet,edtInches,edtWeight,edtCm;
    private Button btnCalculate;
    private TextView txtBmi,txtBmiMessage;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //B2:
        rdoMale = (RadioButton) findViewById(R.id.rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.rdo_female);
        edtAge = (EditText) findViewById(R.id.edt_age);
        edtFeet = (EditText) findViewById(R.id.edt_feet);
        edtInches= (EditText) findViewById(R.id.edt_inches);
        edtCm = (EditText) findViewById(R.id.edt_cm);
        edtWeight = (EditText) findViewById(R.id.edt_weights);
        btnCalculate = (Button) findViewById(R.id.btn_calculate);
        txtBmi = (TextView) findViewById(R.id.txt_bmi);
        txtBmiMessage = (TextView) findViewById(R.id.txt_bmi_message);

        //B3: Xử lý sự kiện click button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        //B4
        if (edtCm.getText().toString().isEmpty() || edtFeet.getText().toString().isEmpty() || edtInches.getText().toString().isEmpty() || edtWeight.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        int feet = Integer.parseInt(edtFeet.getText().toString());
        int inches = Integer.parseInt(edtInches.getText().toString());
        float weight = Float.parseFloat(edtWeight.getText().toString());
        float heightInMeters ;
        if(Float.parseFloat(edtCm.getText().toString()) > 0){
            heightInMeters = Float.parseFloat(edtCm.getText().toString())/100;
        }
        else{
            heightInMeters = (float) ((feet * 12 + inches) * 0.0254);
        }
        float bmi = weight / (heightInMeters * heightInMeters);
        txtBmi.setText(String.format("BMI: %.2f", bmi));
        String message;
        if (bmi < 18.5) {
            message = "Bạn bị thiếu cân.";
        } else if (bmi < 24.9) {
            message = "Bạn có cân nặng bình thường.";
        } else if (bmi < 29.9) {
            message = "Bạn bị thừa cân.";
        } else {
            message = "Bạn bị béo phì.";
        }
        txtBmiMessage.setText(message);
    }


}