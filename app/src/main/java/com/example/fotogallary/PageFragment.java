package com.example.fotogallary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

public class PageFragment extends Fragment {
    private String path;
    private String TAG = Fragment.class.getSimpleName();


    public PageFragment() {}

    @SuppressLint("ValidFragment")
    public PageFragment(String path) {
        Bundle arguments = new Bundle();
        this.path = path;
        arguments.putString("path", path);
        setArguments(arguments);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagefragment, container, false);

        ImageView imageGraphic = view.findViewById(R.id.full_screen);



        path = getArguments().getString("path");


        Glide.with(view).load(path).into(imageGraphic);


        return view;
    }


}
