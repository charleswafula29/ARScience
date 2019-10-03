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
import android.widget.Switch;
import android.widget.Toast;

import com.example.arscience.Helper.RecyclerItemTouchHelper;
import com.example.arscience.Helper.RecyclerItemTouchHelperListener;
import com.example.arscience.adapters.TeacherClassAdapter;
import com.example.arscience.classes.TeacherClass;

import java.util.ArrayList;
import java.util.List;

public class TeacherClasses extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    int view = R.layout.activity_teacher_classes;
    RecyclerView recyclerView;
    TeacherClassAdapter adapter;
    List<TeacherClass> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        getSupportActionBar().setElevation(0);

        recyclerView=findViewById(R.id.TeacherClassesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        classList=new ArrayList<>();

        classList.add(new TeacherClass("Social Informatics","Standard 5 South","22 Students"));
        classList.add(new TeacherClass("Web Security","Standard 8 South","25 Students"));
        classList.add(new TeacherClass("Neural Networks","Standard 7","15 Students"));
        classList.add(new TeacherClass("Corporate Governance","Standard 7","15 Students"));

        adapter=new TeacherClassAdapter(TeacherClasses.this,classList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                =new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof TeacherClassAdapter.ViewHolder){
            String classnameremoved = classList.get(viewHolder.getAdapterPosition()).getClassname();
            adapter.removeclass(viewHolder.getAdapterPosition());
        }

    }
}
