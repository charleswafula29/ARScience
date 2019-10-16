package com.example.arscience.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arscience.R;
import com.example.arscience.classes.SingleModel;

import java.util.List;

public class SingleModelAdapter extends RecyclerView.Adapter<SingleModelAdapter.ViewHolder> {

    private Context ctx;
    private List<SingleModel> list;

    public SingleModelAdapter(Context ctx, List<SingleModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater= LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.ar_student_models_list,null);
        return new SingleModelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final SingleModel model=list.get(i);
        viewHolder.image.setImageResource(model.getImage());
        viewHolder.name.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.SingleModel_image);
            name=itemView.findViewById(R.id.SingleModel_text);
        }
    }
}
