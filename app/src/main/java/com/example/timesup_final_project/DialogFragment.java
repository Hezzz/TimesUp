package com.example.timesup_final_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DialogFragment extends androidx.fragment.app.DialogFragment{

    EditText addTitle, addDesc;
    TextView cancel, addNew;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);

        addTitle = view.findViewById(R.id.titleEditText);
        addDesc = view.findViewById(R.id.descEditText);
        cancel = view.findViewById(R.id.cancel);
        addNew = view.findViewById(R.id.okay);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Nice", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
