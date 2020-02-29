package com.example.timesup_final_project;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements MyDialogFragment.OnInputSelected{

    public static final String TAG = "HOMEFRAGMENT";
    public HomeFragment() {}
    private FloatingActionButton addButton;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        linearLayout = view.findViewById(R.id.deadlineList);
        addButton = view.findViewById(R.id.addFloatingButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                MyDialogFragment newDialog = new MyDialogFragment();
                newDialog.setTargetFragment(HomeFragment.this, 1);
                newDialog.show(getParentFragmentManager(), null);
            }
        });

        return view;
    }

    public void addNewReminder(String text, String desct){
        View deadline = getLayoutInflater().inflate(R.layout.view_custom_row_deadline, linearLayout);
        TextView title = deadline.findViewById(R.id.testTitle);
        TextView desc = deadline.findViewById(R.id.testDesc);
        title.setText(text);
        desc.setText(desct);
//        TextView sampleText = new TextView(getActivity());
//        sampleText.setText(text);
//        sampleText.setTextColor(getResources().getColor(R.color.colorAccent));
//        sampleText.setVisibility(View.VISIBLE);
//        linearLayout.addView(sampleText);
    }

    @Override
    public void sendInput(String title, String desc) {
        addNewReminder(title, desc);
    }
}
