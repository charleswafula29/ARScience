package com.example.arscience.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arscience.MainActivity;
import com.example.arscience.R;
import com.example.arscience.Single_Teacher_Class_Homepage;
import com.example.arscience.StudentHomepage;
import com.example.arscience.classes.StudentClass;

import java.util.List;

import io.paperdb.Paper;

public class StudentClassAdapter extends RecyclerView.Adapter<StudentClassAdapter.ViewHolder>{

    private Context mCtx;
    private List<StudentClass> list;

    public StudentClassAdapter(Context mCtx, List<StudentClass> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(mCtx);
        View view=layoutInflater.inflate(R.layout.student_classes_list,viewGroup,false);
        return new StudentClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final StudentClass studentClass= list.get(i);
        viewHolder.classname.setText(studentClass.getClassname());
        viewHolder.classdesc.setText(studentClass.getClassdesc());
        viewHolder.classcode.setText(studentClass.getClasscode());
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String longcode = studentClass.getClasscode();
                String code= longcode.substring(6,14);
                Intent intent=new Intent(mCtx, StudentHomepage.class);
                //Intent intent=new Intent(mCtx, MainActivity.class);
                Paper.init(mCtx);
                Paper.book().write("Clickedcode",code);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView classname,classdesc,classcode;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            classname=itemView.findViewById(R.id.SingleStudentClassList_classname);
            classdesc=itemView.findViewById(R.id.SingleStudentClassList_desc);
            classcode=itemView.findViewById(R.id.SingleStudentClassList_classcode);
            relativeLayout=itemView.findViewById(R.id.SingleStudentClassList_layout);
        }
    }


}
