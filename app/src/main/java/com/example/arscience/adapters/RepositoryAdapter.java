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

import com.example.arscience.AddModelToClass;
import com.example.arscience.R;
import com.example.arscience.classes.Repository;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder>{

    private Context ctx;
    private List<Repository> list;

    public RepositoryAdapter(Context ctx, List<Repository> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.repository_list,viewGroup,false);
        return new RepositoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Repository repository=list.get(i);
        viewHolder.image.setImageResource(repository.getImage());
        viewHolder.name.setText(repository.getName());
        viewHolder.type.setText(repository.getType());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int imageuri=repository.getImage();
                String image = String.valueOf(imageuri);
                Intent intent=new Intent(ctx, AddModelToClass.class);
                intent.putExtra("imagename",repository.getName());
                intent.putExtra("image",repository.getImage());
                intent.putExtra("desc",repository.getDescription());
                intent.putExtra("realmodelname",repository.getModel_name());
                intent.putExtra("type",repository.getType());
                ctx.startActivity(intent);
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
