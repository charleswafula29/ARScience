package com.example.arscience.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arscience.Animation;
import com.example.arscience.MainActivity;
import com.example.arscience.PreviewClassModel;
import com.example.arscience.R;
import com.example.arscience.classes.Studentmodelslist;

import java.util.List;

public class StudentmodelslistAdapter extends RecyclerView.Adapter<StudentmodelslistAdapter.ViewHolder>{

    private Context ctx;
    private List<Studentmodelslist> list;

    public StudentmodelslistAdapter(Context ctx, List<Studentmodelslist> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.repository_list,viewGroup,false);
        return new StudentmodelslistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Studentmodelslist student = list.get(i);
        viewHolder.image.setImageResource(student.getUri());
        viewHolder.name.setText(student.getName());
        viewHolder.type.setText(student.getType());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(student.getType().equals("Static")){
                    Intent intent1 = new Intent(ctx, MainActivity.class);
                    intent1.putExtra("modelname",student.getSfb());
                    ctx.startActivity(intent1);
                }else if(student.getType().equals("Animated")){
                    Intent intent2 = new Intent(ctx, Animation.class);
                    intent2.putExtra("modelname",student.getSfb());
                    ctx.startActivity(intent2);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView type,name;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.Repository_image);
            type=itemView.findViewById(R.id.Repository_status);
            name=itemView.findViewById(R.id.Repository_modelName);
            layout=itemView.findViewById(R.id.Repository_layout);
        }
    }
}
