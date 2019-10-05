package com.example.arscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arscience.classes.BaseURL;

import io.paperdb.Paper;

public class StudentLogin extends AppCompatActivity {

    ProgressBar mBar;
    EditText names,Snumber;
    Button Continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        Paper.init(this);
        Paper.book().write("Teacher_id","none");
        Paper.book().write("Snumber","none");


        mBar=findViewById(R.id.StudentLoginProgressBar);
        names=findViewById(R.id.StudentLoginNames);
        Snumber=findViewById(R.id.StudentLoginNumber);
        Continue=findViewById(R.id.StudentLoginContinue);


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number,name;
                number=Snumber.getText().toString().trim();
                name=names.getText().toString().trim();

                if(name.isEmpty()){
                    names.setError("Kindly enter names first");
                }else if(number.isEmpty()){
                    Snumber.setError("Kindly enter admission number first");
                }else{
                    login(number,name,BaseURL.getNewStudent(number,name));
                }

            }
        });
    }

    private void login(String sno,String name, String url) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        mBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        if(response.equals("student created")){
                            Paper.book().write("Snumber",sno);
                            Paper.book().write("Snames",name);
                            Toast.makeText(StudentLogin.this, "Welcome "+name, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(StudentLogin.this,StudentClasses.class));
                            finish();
                        }else if(response.equals("student not created")){
                            Toast.makeText(StudentLogin.this, "Unknown error occurred, try again", Toast.LENGTH_LONG).show();
                        }else if(response.equals("student already exists")){
                            Paper.book().write("Snumber",sno);
                            Paper.book().write("Snames",name);
                            Toast.makeText(StudentLogin.this, "Welcome Back "+name, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(StudentLogin.this,StudentClasses.class));
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                String message = null;
                if (error instanceof NetworkError) {
                    message = "Network Error, cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Auth Failure, Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Invalid Credentials! Please try again!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }else{
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
