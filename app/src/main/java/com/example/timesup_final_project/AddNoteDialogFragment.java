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


public class AddNoteDialogFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText addTitle, addDesc;
    private TextView cancel, addNew;
    private Button dateButton;

    public interface OnNoteAdd{
        void addNotes(String title, String desc, String date);
    }
    public OnNoteAdd onNoteAdd;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        addTitle = view.findViewById(R.id.titleEditText);
        addDesc = view.findViewById(R.id.descEditText);
        cancel = view.findViewById(R.id.cancel);
        addNew = view.findViewById(R.id.okay);
        dateButton = view.findViewById(R.id.datePickerButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = addTitle.getText().toString();
                String desc = addDesc.getText().toString();
                if(!input.isEmpty()){
                    Toast.makeText(getContext(), "Added " + input, Toast.LENGTH_SHORT).show();
                    if(desc.isEmpty()) desc = "Add description...";
                    onNoteAdd.addNotes(input, desc, dateButton.getText().toString());
                    getDialog().dismiss();
                }
                if(input.isEmpty()) Toast.makeText(getContext(), "Add title", Toast.LENGTH_SHORT).show();
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
            onNoteAdd = (OnNoteAdd)getTargetFragment();
        }catch(ClassCastException e){}
    }
}
