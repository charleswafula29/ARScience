package com.example.arscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.paperdb.Paper;

public class StudentHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homepage);

        Paper.init(this);
        String code= Paper.book().read("Clickedcode");

        Toast.makeText(this, code, Toast.LENGTH_LONG).show();

    }
}
