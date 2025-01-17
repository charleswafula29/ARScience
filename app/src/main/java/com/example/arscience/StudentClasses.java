package com.example.arscience;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.arscience.Helper.RecyclerItemTouchHelper;
import com.example.arscience.Helper.RecyclerItemTouchHelperListener;
import com.example.arscience.adapters.StudentClassAdapter;
import com.example.arscience.adapters.TeacherClassAdapter;
import com.example.arscience.classes.BaseURL;
import com.example.arscience.classes.StudentClass;
import com.example.arscience.classes.TeacherClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class StudentClasses extends AppCompatActivity {

    String Snumber, Snames;
    RecyclerView recyclerView;
    StudentClassAdapter adapter;
    List<StudentClass> list;
    ProgressBar mBar;
    TextView text;
    ImageView image;
    FloatingActionButton refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classes);
        getSupportActionBar().setElevation(0);

        Paper.init(this);
        Snumber=Paper.book().read("Snumber");
        Snames=Paper.book().read("Snames");

        recyclerView=findViewById(R.id.StudentClassRecycler);
        mBar=findViewById(R.id.StudentClassProgressBar);
        text=findViewById(R.id.StudentClassnotfoundText);
        image=findViewById(R.id.StudentClassnotfoundImage);
        refresh=findViewById(R.id.StudentClassRefresh);


        fetchclasses(BaseURL.getStudentClasses(Snumber));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchclasses(BaseURL.getStudentClasses(Snumber));
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();

    }

    private void fetchclasses(String url) {
        text.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        mBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mBar.setVisibility(View.GONE);

                        try {

                            JSONObject jsonObject= new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("Classes");

                            if(jsonArray.length() == 0){
                                text.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                                Toast.makeText(StudentClasses.this, "You don't have any classes", Toast.LENGTH_LONG).show();
                            }

                            list.clear();

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                list.add(new StudentClass(object.getString("classname"),
                                        object.getString("classdesc"),
                                        "Code: "+object.getString("classcode")));
                            }

                            adapter=new StudentClassAdapter(StudentClasses.this,list);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            list.clear();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mBar.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teachermenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Menu_addclass){

            final AlertDialog.Builder builder = new AlertDialog.Builder(StudentClasses.this);
            LayoutInflater inflater= getLayoutInflater();
            View view= inflater.inflate(R.layout.joinclasslayout,null);
            builder.setView(view);
            final AlertDialog dialog=builder.create();
            dialog.show();

            TextView code;
            ProgressBar mpBar;
            Button join;

            join=view.findViewById(R.id.JoinClassButton);
            mpBar=view.findViewById(R.id.JoinClassprogressbar);
            code=view.findViewById(R.id.JoinClassCode);


            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String classcode=code.getText().toString();
                    if(classcode.isEmpty()){
                        code.setError("Enter class code first");
                    }else{
                        String url=BaseURL.getJoinClass(classcode,Snumber,Snames);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        mpBar.setVisibility(View.VISIBLE);

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("joined class")){
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            Toast.makeText(StudentClasses.this, "Joined class", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            list.clear();
                                            fetchclasses(BaseURL.getStudentClasses(Snumber));
                                        }else if(response.equals("class not joined")){
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            Toast.makeText(StudentClasses.this, "Failed to join class", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }else if(response.equals("class does not exist")){
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            Toast.makeText(StudentClasses.this, "class does not exist", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }else if(response.equals("Already joined class!")){
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            Toast.makeText(StudentClasses.this, "Already joined class!", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                mpBar.setVisibility(View.GONE);

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

                        RequestQueue requestQueue= Volley.newRequestQueue(StudentClasses.this);
                        requestQueue.add(stringRequest);
                    }
                }

            });

        }
        else if(item.getItemId() == R.id.Menu_logout){

            final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(StudentClasses.this);
            builder.setMessage("Are you sure you want to End Session?");
            builder.setCancelable(true);
            builder.setIcon(R.drawable.circlelogo);
            builder.setTitle("End Session");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Paper.book().write("Teacher_id","none");
                    Paper.book().write("Snumber","none");
                    startActivity(new Intent(StudentClasses.this,StudentLogin.class));
                    finish();
                }
            });
            android.support.v7.app.AlertDialog alertDialog= builder.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

}
