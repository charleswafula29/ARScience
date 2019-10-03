package com.example.arscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class SelectLevel extends AppCompatActivity {

    Button Teacher, Student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        Paper.init(this);

        Teacher= findViewById(R.id.Teacher);
        Student=findViewById(R.id.Student);

        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("user_type","Teacher");
                startActivity(new Intent(SelectLevel.this,Login.class));
                finish();
            }
        });

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("user_type","Student");
                startActivity(new Intent(SelectLevel.this,StudentClasses.class));
                finish();
            }
        });

    }
}
