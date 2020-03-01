package com.example.timesup_final_project;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditNoteDialogFragment extends AppCompatDialogFragment {

    EditText editTitle, editDesc;
    TextView cancel, editNote;

    public interface OnEditNote{
        void editNote(String newTitle, String newDesc);
    }
    public OnEditNote onEditNote;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        editTitle = view.findViewById(R.id.titleEditText);
        editDesc = view.findViewById(R.id.descEditText);
        cancel = view.findViewById(R.id.cancel);
        editNote = view.findViewById(R.id.okay);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();
                if(!title.isEmpty() || !desc.isEmpty()){
                    Toast.makeText(getContext(), "Edited " + title, Toast.LENGTH_SHORT).show();
                    onEditNote.editNote(title, desc);
                }
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onEditNote = (OnEditNote)getTargetFragment();
        }catch (ClassCastException ex){}
    }
}
