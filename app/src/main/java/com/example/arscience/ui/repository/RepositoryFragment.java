package com.example.arscience.ui.repository;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.arscience.R;
import com.example.arscience.adapters.RepositoryAdapter;
import com.example.arscience.classes.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<Repository> list;
    RepositoryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_repository, container, false);

        recyclerView=root.findViewById(R.id.RepositoryRecycler);

        String desc="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Leo vel orci porta non pulvinar neque. Risus nullam eget felis eget nunc lobortis. Elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at augue. Nibh praesent tristique magna sit amet. Pulvinar proin gravida hendrerit lectus a. Elit ullamcorper dignissim cras tincidunt. Id leo in vitae turpis massa sed. Mauris pharetra et ultrices neque ornare. Ac feugiat sed lectus vestibulum mattis ullamcorper velit sed ullamcorper.";

        list=new ArrayList<>();
        list.add(new Repository(R.drawable.earth,"Earth planet","Static",desc,"earth_ball.sfb"));
        list.add(new Repository(R.drawable.jupiter,"Jupiter planet","Static",desc,"13905_Jupiter_V1_l3.sfb"));
        list.add(new Repository(R.drawable.windmill,"Windmill","Static",desc,"PUSHILIN_wind_turbine.sfb"));
        list.add(new Repository(R.drawable.rack,"testtube rack","Static",desc,"rack.sfb"));
        list.add(new Repository(R.drawable.skeleton,"skeleton","Static",desc,"skeleton.sfb"));
        list.add(new Repository(R.drawable.microscope,"microscope","Static",desc,"model.sfb"));

        adapter=new RepositoryAdapter(root.getContext(),list);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),3));
        recyclerView.setAdapter(adapter);

        return root;
    }
}