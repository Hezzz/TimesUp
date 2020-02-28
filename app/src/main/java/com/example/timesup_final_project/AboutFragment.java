package com.example.timesup_final_project;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    public AboutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        ((MainActivity)getActivity()).setActionBarTitle("About");
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }

}
