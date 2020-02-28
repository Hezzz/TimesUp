package com.example.timesup_final_project;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment{

    public HomeFragment() {}
    private FloatingActionButton addButton;

    TextView nice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        nice = view.findViewById(R.id.textView2);
        addButton = view.findViewById(R.id.addFloatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newDialog = new DialogFragment();
//                newDialog.setTargetFragment(HomeFragment.this, 1);
                //fragment manager
                nice.setText("LOOOOL");
            }
        });

        return view;
    }
}
