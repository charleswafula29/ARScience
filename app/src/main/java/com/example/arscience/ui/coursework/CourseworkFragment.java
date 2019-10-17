package com.example.arscience.ui.coursework;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.example.arscience.R;
import com.example.arscience.adapters.ModelsAdapter;
import com.example.arscience.adapters.SinglestudentAdapter;
import com.example.arscience.classes.BaseURL;
import com.example.arscience.classes.ModelsClass;
import com.example.arscience.classes.Singlestudent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CourseworkFragment extends Fragment {

    ModelsAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar mBar;
    List<ModelsClass> list;
    ImageView image;
    TextView text;
    FloatingActionButton refresh;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_coursework, container, false);

        Paper.init(root.getContext());
        String code=Paper.book().read("Classcode");

        recyclerView=root.findViewById(R.id.CourseworkFragment_RecyclerView);
        image=root.findViewById(R.id.CourseworkFragment_sorryimage);
        text=root.findViewById(R.id.CourseworkFragment_sorrytext);
        mBar=root.findViewById(R.id.CourseworkFragment_progressbar);
        refresh=root.findViewById(R.id.CourseworkFragment_Refresh);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),3));
        list= new ArrayList<>();


        feth(BaseURL.getCoursework(code),root);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feth(BaseURL.getCoursework(code),root);
            }
        });

        return root;
    }

    private void feth(String url, View root) {

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
                                list.add(new ModelsClass(Integer.parseInt(object.getString("uri")),object.getString("model_name"),
                                        object.getString("sfb_name"),object.getString("type"),object.getString("description")));
                            }
                            adapter=new ModelsAdapter(root.getContext(),list);
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
                    Toast.makeText(root.getContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
                Toast.makeText(root.getContext(),message,Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(root.getContext());
        requestQueue.add(stringRequest);

    }
}