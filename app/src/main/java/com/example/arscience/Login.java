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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.arscience.classes.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    Button BtnLogin;
    TextView TxtRegister;
    EditText email,password;
    ImageView viewpass;
    ProgressBar mpBar;
    int setType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        Paper.book().write("Teacher_id","none");
        Paper.book().write("Snumber","none");

        BtnLogin= findViewById(R.id.Login);
        TxtRegister=findViewById(R.id.Register);
        email=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        viewpass=findViewById(R.id.ViewPassword);
        mpBar=findViewById(R.id.loginprogressbar);

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

        TxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
                finish();
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(email.getText().toString().trim(),password.getText().toString().trim());
            }
        });
    }

    private void validate(String useremail, String userpass) {
        mpBar.setVisibility(View.VISIBLE);
        if(useremail.isEmpty()){
            mpBar.setVisibility(View.GONE);
            email.setError("Email is required!");
        }else if(userpass.isEmpty()){
            mpBar.setVisibility(View.GONE);
            password.setError("Password is required!");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
            mpBar.setVisibility(View.GONE);
            email.setError("Enter a valid email address");
        }else{
            login(BaseURL.getLogin(useremail,userpass));
        }

    }

    private void login(String url) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mpBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        try{

                            JSONArray jsonArray= response.getJSONArray("User");
                            String Teacher_id=jsonArray.get(0).toString();
                            Paper.book().write("Teacher_id",Teacher_id);
                            Toast.makeText(getApplicationContext(),"Welcome back, "+jsonArray.get(2).toString(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, TeacherClasses.class));
                            finish();
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Login Failed! Try again",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mpBar.setVisibility(View.GONE);
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
