package com.example.arscience.ui.repository;

import android.os.Bundle;
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

public class RepositoryFragment extends Fragment {

    private RepositoryViewModel repositoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        repositoryViewModel =
                ViewModelProviders.of(this).get(RepositoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_repository, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        repositoryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}