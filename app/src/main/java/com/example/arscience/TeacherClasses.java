package com.example.arscience;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
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
import com.example.arscience.adapters.TeacherClassAdapter;
import com.example.arscience.classes.BaseURL;
import com.example.arscience.classes.TeacherClass;
import com.example.arscience.classes.codeGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TeacherClasses extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    int view = R.layout.activity_teacher_classes;
    RecyclerView recyclerView;
    TeacherClassAdapter adapter;
    List<TeacherClass> classList;
    ProgressBar mBar;
    TextView text;
    ImageView image;
    Button refresh;
    String teacher_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        getSupportActionBar().setElevation(0);

        Paper.init(TeacherClasses.this);
        teacher_id=Paper.book().read("Teacher_id");

        recyclerView=findViewById(R.id.TeacherClassesRecycler);
        mBar=findViewById(R.id.TeacherProgressBar);
        text=findViewById(R.id.TeacherNotFoundText);
        image=findViewById(R.id.Teachernotfound);
        refresh=findViewById(R.id.TeacherRefresh);

        fetch(BaseURL.getTeacherClasses(teacher_id));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(BaseURL.getTeacherClasses(teacher_id));
            }
        });


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                =new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    private void fetch(String url) {
        text.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        refresh.setVisibility(View.GONE);
        mBar.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        classList=new ArrayList<>();

        StringRequest stringRequest= new StringRequest(
                Request.Method.GET, url,
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
                                refresh.setVisibility(View.VISIBLE);
                                Toast.makeText(TeacherClasses.this, "You don't have any classes", Toast.LENGTH_SHORT).show();
                            }

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                classList.add(new TeacherClass(object.getString("name"),
                                        object.getString("description"),
                                        "Code: "+object.getString("code")));
                            }

                            adapter=new TeacherClassAdapter(TeacherClasses.this,classList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            classList.clear();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBar.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.VISIBLE);

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
        }
        );

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
        if (item.getItemId() == R.id.Menu_addclass) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherClasses.this);
            LayoutInflater inflater= getLayoutInflater();
            View view= inflater.inflate(R.layout.addnewclass,null);
            builder.setView(view);
            final AlertDialog dialog=builder.create();
            dialog.show();

            TextView classname,classdesc;
            ProgressBar mpBar;

            Button create;


            classname= view.findViewById(R.id.New_classname);
            classdesc=view.findViewById(R.id.New_description);
            create=view.findViewById(R.id.Create);
            mpBar=view.findViewById(R.id.New_progressbar);

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    create.setVisibility(View.GONE);
                    mpBar.setVisibility(View.VISIBLE);

                    String name,desc,code;
                    code= codeGenerator.generateRandomString(8);
                    name=classname.getText().toString().trim();
                    desc=classdesc.getText().toString().trim();


                    String URL= BaseURL.getNewClass(code,name,desc,teacher_id);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("created")){
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(TeacherClasses.this, "New Class created", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                fetch(BaseURL.getTeacherClasses(teacher_id));
                            }else if(response.equals("name already in use")){
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                create.setVisibility(View.VISIBLE);
                                mpBar.setVisibility(View.GONE);
                                Toast.makeText(TeacherClasses.this, "Name already exists", Toast.LENGTH_LONG).show();
                                classname.setText("");
                            }else if(response.equals("not created")){
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                create.setVisibility(View.VISIBLE);
                                mpBar.setVisibility(View.GONE);
                                Toast.makeText(TeacherClasses.this, "Class not created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            create.setVisibility(View.VISIBLE);
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

                    RequestQueue requestQueue= Volley.newRequestQueue(TeacherClasses.this);
                    requestQueue.add(stringRequest);
                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof TeacherClassAdapter.ViewHolder){
            String classnameremoved = classList.get(viewHolder.getAdapterPosition()).getClassname();
            String classcoderemoved = classList.get(viewHolder.getAdapterPosition()).getClasscode();
            String trimmedcode= classcoderemoved.substring(6,14);
            deleteclass(trimmedcode);
            //adapter.removeclass(viewHolder.getAdapterPosition());
        }

    }

    private void deleteclass(String code) {
        mBar.setVisibility(View.VISIBLE);
        String url= BaseURL.getDeleteClass(code,teacher_id);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Class deleted")){
                            Toast.makeText(TeacherClasses.this, "Class deleted", Toast.LENGTH_SHORT).show();
                            fetch(BaseURL.getTeacherClasses(teacher_id));
                        }else if(response.equals("Class not deleted")){
                            Toast.makeText(TeacherClasses.this, "Class not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

        RequestQueue requestQueue= Volley.newRequestQueue(TeacherClasses.this);
        requestQueue.add(stringRequest);
    }
}
