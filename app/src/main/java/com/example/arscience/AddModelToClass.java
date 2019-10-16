package com.example.arscience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import io.paperdb.Paper;

public class AddModelToClass extends AppCompatActivity {

    int imageUri;
    String imagename,realname,type,description,code;
    ImageView imageView;
    TextView txtname, txttype, txtdesc;
    Button btnadd,preview;
    ProgressBar mBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_model_to_class);

        Paper.init(this);
        code=Paper.book().read("Classcode");

        imageView=findViewById(R.id.AddModel_image);
        txtname=findViewById(R.id.AddModel_name);
        txttype=findViewById(R.id.AddModel_type);
        txtdesc=findViewById(R.id.AddModel_description);
        btnadd=findViewById(R.id.AddModel_btnAdd);
        preview=findViewById(R.id.AddModel_view);
        mBar=findViewById(R.id.AddModel_progress);

        Intent intent= getIntent();
        imagename=intent.getStringExtra("imagename");
        realname=intent.getStringExtra("realmodelname");
        type=intent.getStringExtra("type");
        description=intent.getStringExtra("desc");
        imageUri=intent.getExtras().getInt("image");

        txtname.setText(imagename);
        txttype.setText(type);
        txtdesc.setText(description);
        imageView.setImageResource(imageUri);

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Static")){
                    Intent intent1 = new Intent(AddModelToClass.this,MainActivity.class);
                    intent1.putExtra("modelname",realname);
                    startActivity(intent1);
                }
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                String uri= String.valueOf(imageUri);
                String URL= BaseURL.getAddmodeltoclass(imagename,uri,realname,type,description,code);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("model added")){
                                    Toast.makeText(AddModelToClass.this, "Model added to classroom", Toast.LENGTH_LONG).show();
                                    finish();
                                }else if(response.equals("not added")){
                                    Toast.makeText(AddModelToClass.this, "Model not added!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else if(response.equals("already exists")){
                                    Toast.makeText(AddModelToClass.this, "Model already exists in classroom", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mBar.setVisibility(View.GONE);

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

                RequestQueue requestQueue= Volley.newRequestQueue(AddModelToClass.this);
                requestQueue.add(stringRequest);


            }
        });



    }
}
