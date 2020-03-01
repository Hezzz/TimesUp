package com.example.timesup_final_project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.Calendar;

public class EditNoteDialogFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText editTitle, editDesc;
    private TextView cancel, editNote;
    private Button dateButton;

    public interface OnEditNote{
        void editNote(String newTitle, String newDesc, String newDate);
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
        dateButton = view.findViewById(R.id.datePickerButton);

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
                    onEditNote.editNote(title, desc, dateButton.getText().toString());
                }
                getDialog().dismiss();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        return view;
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                R.style.DatePicker,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day){
        String date = month + 1 + "/" + day + "/" + year;
        dateButton.setText(date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onEditNote = (OnEditNote)getTargetFragment();
        }catch(ClassCastException ex){}
    }
}
