package com.example.arscience.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arscience.MainActivity;
import com.example.arscience.R;
import com.example.arscience.classes.TeacherClass;

import java.util.List;

public class TeacherClassAdapter extends RecyclerView.Adapter<TeacherClassAdapter.ViewHolder> {
    private Context mCtx;
    private List<TeacherClass> teacherClassList;

    public TeacherClassAdapter(Context mCtx, List<TeacherClass> teacherClassList) {
        this.mCtx = mCtx;
        this.teacherClassList = teacherClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(mCtx);
        View view=layoutInflater.inflate(R.layout.teacher_classes_list,viewGroup,false);
        return new TeacherClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final TeacherClass teacherClass= teacherClassList.get(i);
        viewHolder.name.setText(teacherClass.getClassname());
        viewHolder.desc.setText(teacherClass.getDescription());
        viewHolder.code.setText(teacherClass.getClasscode());
        viewHolder.view_foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(mCtx, Single_Teacher_Class_Homepage.class);
                Intent intent=new Intent(mCtx, MainActivity.class);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherClassList.size();
    }

    public void removeclass(int i){

        teacherClassList.remove(i);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,desc, code;
        public RelativeLayout view_foreground,view_background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.Class_name);
            desc=itemView.findViewById(R.id.Class_description);
            code =itemView.findViewById(R.id.Class_code);
            view_foreground=itemView.findViewById(R.id.SingleTeacherClass);
            view_background=itemView.findViewById(R.id.view_background);
        }
    }
}
