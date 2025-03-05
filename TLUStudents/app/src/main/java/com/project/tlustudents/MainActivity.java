package com.project.tlustudents;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
private RecyclerView rcvStudents;
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

        Student[] students = {
                new Student("01", "Nguyen Van A", R.drawable.calculator),
                new Student("03", "Tran Thi B", R.drawable.tape),
                new Student("04", "Le Van C", R.drawable.getting_started),
                new Student("05", "Pham Minh D", R.drawable.hungry_developer),
                new Student("06", "Nguyen Thi E",  R.drawable.getting_started),
                new Student("07", "Hoang Van F", R.drawable.tape),
                new Student("08", "Bui Thi G",  R.drawable.getting_started),
                new Student("09", "Vu Van H",  R.drawable.hungry_developer),
                new Student("10", "Do Thi I", R.drawable.tape)
        };

        rcvStudents = (RecyclerView) findViewById(R.id.rcv_students);
        StudentsAdapter myAdapter = new StudentsAdapter(students);
        rcvStudents.setAdapter(myAdapter);

    }
}