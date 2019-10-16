package com.example.arscience.ui.home;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.arscience.R;

import io.paperdb.Paper;

public class CourseworkFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_coursework, container, false);
        TextView textView = root.findViewById(R.id.text_home);
        textView.setText("Coursework Homepage");

        Paper.init(root.getContext());
        String code=Paper.book().read("Classcode");

//        Toast.makeText(root.getContext(), code, Toast.LENGTH_SHORT).show();
        return root;
    }
}