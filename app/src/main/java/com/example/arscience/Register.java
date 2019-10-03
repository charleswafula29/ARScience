package com.example.arscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class Register extends AppCompatActivity {

    Button btnRegister;
    EditText names,email,password,confirmpass;
    ImageView viewpass,confirmviewpass;
    ProgressBar mBar;
    TextView txtLogin;
    int setType,setType2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setType=1;
        setType2=1;

        btnRegister= findViewById(R.id.RegisterBtn);
        txtLogin=findViewById(R.id.BacktoLogin);
        names=findViewById(R.id.Names);
        email=findViewById(R.id.RegisterEmailAddress);
        password=findViewById(R.id.RegisterPassword);
        confirmpass=findViewById(R.id.RegisterConfirmPassword);
        viewpass=findViewById(R.id.RegisterViewPassword);
        confirmviewpass=findViewById(R.id.RegisterConfirmViewPassword);
        mBar=findViewById(R.id.RegisterProgressBar);

        viewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setType==1) {
                    setType = 0;
                    password.setTransformationMethod(null);
                    if (password.getText().length() > 0)
                        password.setSelection(password.getText().length());
                }else{
                    setType=1;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    if(password.getText().length() > 0)
                        password.setSelection(password.getText().length());
                }
            }
        });
        confirmviewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setType2==1) {
                    setType2 = 0;
                    confirmpass.setTransformationMethod(null);
                    if (confirmpass.getText().length() > 0)
                        confirmpass.setSelection(confirmpass.getText().length());
                }else{
                    setType2=1;
                    confirmpass.setTransformationMethod(new PasswordTransformationMethod());
                    if(confirmpass.getText().length() > 0)
                        confirmpass.setSelection(confirmpass.getText().length());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate(names.getText().toString().trim(),
                        email.getText().toString().trim(),
                        password.getText().toString().trim(),
                        confirmpass.getText().toString().trim());
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });
    }

    private void Validate(String Names, String Email, String Pass, String ConfirmPass) {
        mBar.setVisibility(View.VISIBLE);
        if(Names.isEmpty()){
            mBar.setVisibility(View.GONE);
            names.setError("Please enter full names");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            mBar.setVisibility(View.GONE);
            email.setError("Enter a valid Email address");
        }else if(Email.isEmpty()){
            mBar.setVisibility(View.GONE);
            email.setError("Kindly enter Email address");
        }else if(Pass.equals(ConfirmPass)){
            mBar.setVisibility(View.GONE);
            confirmpass.setError("Passwords don't match");
        }else if(Pass.isEmpty()){
            mBar.setVisibility(View.GONE);
            password.setError("Password is required");
        }else if(ConfirmPass.isEmpty()){
            mBar.setVisibility(View.GONE);
            confirmpass.setError("Password is required");
        }else{
            registeruser(BaseURL.getRegister(Email,Names,Pass));
        }

    }

    private void registeruser(String url) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        if(response.equals("inserted")){
                            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,Login.class));
                        }else if(response.equals("not inserted")){
                            Toast.makeText(Register.this, "Registration Unsuccessful, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
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
