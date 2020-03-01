package com.example.timesup_final_project;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements AddNoteDialogFragment.OnNoteAdd,
    EditNoteDialogFragment.OnEditNote{

    public HomeFragment() {}
    private FloatingActionButton addButton;
    private TextView clickedTitle, clickedDesc, clickedDate;
    private LinearLayout linearLayout;
    private DatabaseHelper deadlineDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        deadlineDatabaseHelper = new DatabaseHelper(getActivity());

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
        deadline.setOnLongClickListener(noteDeleteListener);
        linearLayout.addView(deadline, 0);
    }

    @Override
    public void addNotes(String title, String desc, String date){
        if (deadlineDatabaseHelper.addNewDeadline(CurrentUser.getUserName(),
                title, desc, date)){
            Toast.makeText(getActivity(), "Added " + title, Toast.LENGTH_SHORT).show();
            addNewReminder(title, desc, date);
        }
    }

    private View.OnClickListener noteListener = new View.OnClickListener(){
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

    private View.OnLongClickListener noteDeleteListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(final View view){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme)
                    .setIcon(R.drawable.delete_icon)
                    .setTitle(getString(R.string.delete_title))
                    .setMessage(getString(R.string.delete_message))
                    .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), getString(R.string.delete_cancelled), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            deadlineDatabaseHelper.deleteDeadline(CurrentUser.getUserName(),
                                    ((TextView)view.findViewWithTag("TITLE")).getText().toString(),
                                    ((TextView)view.findViewWithTag("DESC")).getText().toString(),
                                    ((TextView)view.findViewWithTag("DATE")).getText().toString());
                            ((ViewGroup)view.getParent()).removeView(view);
                            Toast.makeText(getContext(),getString(R.string.deadline_deleted), Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog deleteDialog = builder.create();
            deleteDialog.show();
            return true;
        }
    };

    @Override
    public void editNote(String newTitle, String newDesc, String newDate) {
        clickedTitle.setText(newTitle);
        clickedDesc.setText(newDesc);
        clickedDate.setText(newDate);
    }
}

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
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
