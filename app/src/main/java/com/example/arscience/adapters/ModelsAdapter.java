package com.example.arscience.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arscience.AddModelToClass;
import com.example.arscience.MainActivity;
import com.example.arscience.PreviewClassModel;
import com.example.arscience.R;
import com.example.arscience.classes.ModelsClass;

import java.util.List;

public class ModelsAdapter extends RecyclerView.Adapter<ModelsAdapter.ViewHolder>{

    private Context ctx;
    private List<ModelsClass> list;

    public ModelsAdapter(Context ctx, List<ModelsClass> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.repository_list,viewGroup,false);
        return new ModelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ModelsClass modelsClass=list.get(i);
        viewHolder.image.setImageResource(modelsClass.getUri());
        viewHolder.name.setText(modelsClass.getName());
        viewHolder.type.setText(modelsClass.getType());
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx, PreviewClassModel.class);
                intent.putExtra("imagename",modelsClass.getName());
                intent.putExtra("image",modelsClass.getUri());
                intent.putExtra("desc",modelsClass.getDescription());
                intent.putExtra("realmodelname",modelsClass.getSfb());
                intent.putExtra("type",modelsClass.getType());
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
