package com.example.arscience;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.paperdb.Paper;

public class StudentClasses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classes);

        Paper.init(this);
        Paper.book().write("Teacher_id","none");
    }
}
