package com.example.arscience;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.paperdb.Paper;

public class Splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2500;
    SharedPreferences prefs = null;
    String User, Teacher_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        prefs = getSharedPreferences("com.example.charlie.arscience", MODE_PRIVATE);
        Paper.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splashscreen.this,SelectLevel.class));
                    finish();
                }
            },SPLASH_TIME_OUT);
            prefs.edit().putBoolean("firstrun", false).apply();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkSession();
                }
            },SPLASH_TIME_OUT);
        }
    }

    private void checkSession() {
        User=Paper.book().read("user_type").toString();
        Teacher_id=Paper.book().read("Teacher_id").toString();
        if(User.equals("Student")){
            startActivity(new Intent(Splashscreen.this,StudentClasses.class));
            finish();
        }else if(User.equals("Teacher")){
            if(Teacher_id.equals("none")){
                startActivity(new Intent(Splashscreen.this,Login.class));
                finish();
            }else{
                startActivity(new Intent(Splashscreen.this,TeacherClasses.class));
                finish();
            }
        }
    }
}
