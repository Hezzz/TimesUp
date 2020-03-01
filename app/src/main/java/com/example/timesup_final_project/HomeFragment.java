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

import java.util.List;

public class HomeFragment extends Fragment implements AddNoteDialogFragment.OnNoteAdd,
    EditNoteDialogFragment.OnEditNote{

    public HomeFragment() {}
    private FloatingActionButton addButton;
    private TextView clickedTitle, clickedDesc, clickedDate, clickedTime;
    private LinearLayout linearLayout;
    private DatabaseHelper deadlineDatabaseHelper;
    private List<Deadline> deadlineList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        deadlineDatabaseHelper = new DatabaseHelper(getActivity());
        linearLayout = view.findViewById(R.id.deadlineList);
        deadlineList = deadlineDatabaseHelper.getDeadlines(CurrentUser.getUserName());

        for(int i=0; i<deadlineList.size(); i++){
            View deadline = getLayoutInflater().inflate(R.layout.view_custom_row_deadline, linearLayout, false);

            TextView title = deadline.findViewById(R.id.deadlineTitle);
            TextView deadline_date = deadline.findViewById(R.id.deadlineDate);
            TextView desc = deadline.findViewById(R.id.deadlineDesc);
            TextView time = deadline.findViewById(R.id.deadlineTime);

            title.setTag("TITLE");
            deadline_date.setTag("DATE");
            desc.setTag("DESC");
            time.setTag("TIME");

            title.setText(deadlineList.get(i).getTastTitle());
            desc.setText(deadlineList.get(i).getTaskDesc());
            deadline_date.setText(deadlineList.get(i).getTaskDate());
            time.setText(deadlineList.get(i).getTask_time());

            deadline.setOnClickListener(noteListener);
            deadline.setOnLongClickListener(noteDeleteListener);
            linearLayout.addView(deadline, 0);
        }

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

    public void addNewReminder(String text, String description, String date, String time){
        View deadline = getLayoutInflater().inflate(R.layout.view_custom_row_deadline, linearLayout, false);

        TextView title = deadline.findViewById(R.id.deadlineTitle);
        TextView deadline_date = deadline.findViewById(R.id.deadlineDate);
        TextView desc = deadline.findViewById(R.id.deadlineDesc);
        TextView deadline_time = deadline.findViewById(R.id.deadlineTime);

        title.setTag("TITLE");
        deadline_date.setTag("DATE");
        desc.setTag("DESC");
        deadline_time.setTag("TIME");

        title.setText(text);
        deadline_date.setText(date);
        desc.setText(description);
        deadline_time.setText(time);

        deadline.setOnClickListener(noteListener);
        deadline.setOnLongClickListener(noteDeleteListener);
        linearLayout.addView(deadline, 0);
    }

    @Override
    public void addNotes(String title, String desc, String date, String time){
        if (deadlineDatabaseHelper.addNewDeadline(CurrentUser.getUserName(),
                title, desc, date, time)){
            Toast.makeText(getActivity(), "Added " + title, Toast.LENGTH_SHORT).show();
            addNewReminder(title, desc, date, time);
        }
    }

    private View.OnClickListener noteListener = new View.OnClickListener(){
        @Override
        public void onClick(final View view){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme)
                    .setIcon(R.drawable.edit_icon)
                    .setTitle(getString(R.string.edit_deadline))
                    .setMessage(getString(R.string.edit_question))
                    .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                        }
                    })
                    .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            clickedTitle = view.findViewWithTag("TITLE");
                            clickedDate = view.findViewWithTag("DATE");
                            clickedDesc  = view.findViewWithTag("DESC");
                            clickedTime = view.findViewWithTag("TIME");
                            Bundle oldData = new Bundle();
                            oldData.putString("TITLE", clickedTitle.getText().toString());
                            oldData.putString("DESC", clickedDesc.getText().toString());
                            oldData.putString("DATE", clickedDate.getText().toString());
                            oldData.putString("TIME", clickedTime.getText().toString());
                            EditNoteDialogFragment editDialog = new EditNoteDialogFragment();
                            editDialog.setArguments(oldData);
                            editDialog.setTargetFragment(HomeFragment.this, 1);
                            editDialog.show(getParentFragmentManager(), null);
                        }
                    });
            AlertDialog editDialog = builder.create();
            editDialog.show();
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
                                    ((TextView)view.findViewWithTag("DATE")).getText().toString(),
                                    ((TextView)view.findViewWithTag("TIME")).getText().toString());
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
    public void editNote(String newTitle, String newDesc, String newDate, String newTime){
        deadlineDatabaseHelper.editDeadline(CurrentUser.getUserName(), clickedTitle.getText().toString(),
                clickedDesc.getText().toString(), clickedDate.getText().toString(), clickedTime.getText().toString(),
                newTitle, newDesc, newDate, newTime);
        clickedTitle.setText(newTitle);
        clickedDesc.setText(newDesc);
        clickedDate.setText(newDate);
        clickedTime.setText(newTime);
    }
}
