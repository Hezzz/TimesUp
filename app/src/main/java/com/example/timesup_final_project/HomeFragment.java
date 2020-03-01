package com.example.timesup_final_project;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements AddNoteDialogFragment.OnNoteAdd,
    EditNoteDialogFragment.OnEditNote{

    public HomeFragment() {}
    private FloatingActionButton addButton;
    private TextView clickedTitle, clickedDesc, clickedDate;
    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
                AddNoteDialogFragment newDialog = new AddNoteDialogFragment();
                newDialog.setTargetFragment(HomeFragment.this, 1);
                newDialog.show(getParentFragmentManager(), null);
            }
        });

        return view;
    }

    public void addNewReminder(String text, String desct, String date){
        View deadline = getLayoutInflater().inflate(R.layout.view_custom_row_deadline, linearLayout, false);

        TextView title = deadline.findViewById(R.id.deadlineTitle);
        TextView deadline_date = deadline.findViewById(R.id.deadlineDate);
        TextView desc = deadline.findViewById(R.id.deadlineDesc);

        title.setTag("TITLE");
        deadline_date.setTag("DATE");
        desc.setTag("DESC");

        title.setText(text);
        deadline_date.setText(date);
        desc.setText(desct);

        deadline.setOnClickListener(noteListener);
        linearLayout.addView(deadline, 0);
    }

//    @Override
//    public void onSaveInstanceState(Bundle state){
//        super.onSaveInstanceState(state);
//        if(state!=null){
//            state.putString("website","You got back");
//            Toast.makeText(getActivity(), "Fragment Save State", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onViewStateRestored(Bundle savedInstanceState){
//        super.onViewStateRestored(savedInstanceState);
//        if(savedInstanceState!=null){
//            String website = savedInstanceState.getString("website");
//            Toast.makeText(getActivity(),"Fragment Restore State: ",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//    }

    @Override
    public void addNotes(String title, String desc, String date){
        addNewReminder(title, desc, date);
    }

    private View.OnClickListener noteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view){
            clickedTitle = view.findViewWithTag("TITLE");
            clickedDate = view.findViewWithTag("DATE");
            clickedDesc  = view.findViewWithTag("DESC");
            EditNoteDialogFragment editDialog = new EditNoteDialogFragment();
            editDialog.setTargetFragment(HomeFragment.this, 1);
            editDialog.show(getParentFragmentManager(), null);
        }
    };

    @Override
    public void editNote(String newTitle, String newDesc) {
        clickedTitle.setText(newTitle);
        clickedDesc.setText(newDesc);
    }
}
