package com.example.arscience;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.example.arscience.adapters.ModelsAdapter;
import com.example.arscience.adapters.StudentmodelslistAdapter;
import com.example.arscience.classes.BaseURL;
import com.example.arscience.classes.ModelsClass;
import com.example.arscience.classes.Studentmodelslist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class StudentHomepage extends AppCompatActivity {

    private String code;
    FloatingActionButton refresh;
    RecyclerView recyclerView;
    ProgressBar mBar;
    ImageView image;
    TextView text;
    StudentmodelslistAdapter adapter;
    List<Studentmodelslist> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homepage);

        Paper.init(this);
        code= Paper.book().read("Clickedcode");

        recyclerView=findViewById(R.id.StudentHomepage_RecyclerView);
        refresh=findViewById(R.id.StudentHomepage_Refresh);
        mBar=findViewById(R.id.StudentHomepage_progressbar);
        image=findViewById(R.id.StudentHomepage_sorryimage);
        text=findViewById(R.id.StudentHomepage_sorrytext);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(StudentHomepage.this,3));
        list= new ArrayList<>();

        fetch(BaseURL.getCoursework(code));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(BaseURL.getCoursework(code));
            }
        });
    }

    private void fetch(String url) {

        text.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        mBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mBar.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray= jsonObject.getJSONArray("Models");

                            if(jsonArray.length() == 0){
                                text.setVisibility(View.VISIBLE);
                                image.setVisibility(View.VISIBLE);
                            }

                            list.clear();

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);

                                list.add(new Studentmodelslist(Integer.parseInt(object.getString("uri")),object.getString("model_name"),
                                        object.getString("sfb_name"),object.getString("type")));
                            }

                            adapter=new StudentmodelslistAdapter(StudentHomepage.this,list);
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
                    Toast.makeText(StudentHomepage.this, error.toString(), Toast.LENGTH_LONG).show();
                }
                Toast.makeText(StudentHomepage.this,message,Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(StudentHomepage.this);
        requestQueue.add(stringRequest);

    }
}
