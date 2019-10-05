package com.example.arscience.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arscience.R;
import com.example.arscience.classes.Singlestudent;

import java.util.List;

public class SinglestudentAdapter extends RecyclerView.Adapter<SinglestudentAdapter.ViewHolder> {

    private Context Mctx;
    private List<Singlestudent> list;

    public SinglestudentAdapter(Context mctx, List<Singlestudent> list) {
        Mctx = mctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(Mctx);
        View view= layoutInflater.inflate(R.layout.studentslist,viewGroup,false);
        return new SinglestudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Singlestudent singlestudent=list.get(i);
        viewHolder.names.setText(singlestudent.getStudentname());
        viewHolder.admission.setText(singlestudent.getAdmission());
        viewHolder.status.setText(singlestudent.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView admission,names,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            names=itemView.findViewById(R.id.SingleStudent_name);
            admission=itemView.findViewById(R.id.SingleStudent_admissionNo);
            status=itemView.findViewById(R.id.SingleStudent_status);

        }
    }
}
