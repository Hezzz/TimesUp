package com.example.timesup_final_project;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;


public class AddNoteDialogFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private EditText addTitle, addDesc;
    private TextView cancel, addNew;
    private Button dateButton, timeButton;
    public static int alarmMonth, alarmDay, alarmYear, alarmHour, alarmMinute;
    private int req_code = 0;

    public interface OnNoteAdd{
        void addNotes(String title, String desc, String date, String time);
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
        timeButton = view.findViewById(R.id.timeButton);

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
                    req_code = input.length() + desc.length();
                    onNoteAdd.addNotes(input, desc, dateButton.getText().toString(), timeButton.getText().toString());
                    setAlarm(input, desc);
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

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
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

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                R.style.TimePicker,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity())
        );
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minutes){
        alarmHour = hour;
        alarmMinute = minutes;
        String time;
        if(minutes < 10 ) time = hour + ":0" + minutes;
        else time = hour + ":" + minutes;
        timeButton.setText(time);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day){
        alarmYear = year;
        alarmMonth = month;
        alarmDay = day;
        String date = month + 1 + "/" + day + "/" + year;
        dateButton.setText(date);
    }

    private void setAlarm(String title, String desc){
        Calendar current = Calendar.getInstance();
        Calendar setCal = (Calendar)current.clone();
        setCal.set(Calendar.HOUR_OF_DAY, alarmHour);
        setCal.set(Calendar.MINUTE, alarmMinute);
        setCal.set(Calendar.DAY_OF_MONTH, alarmDay);
        setCal.set(Calendar.MONTH, alarmMonth);
        setCal.set(Calendar.YEAR, alarmYear);
        alarmSetter(setCal, title, desc);
    }

    private void alarmSetter(Calendar alarm, String title, String desc){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getContext(), DeadlineAlarmReceiver.class);
        alarmIntent.putExtra("TITLE", title);
        alarmIntent.putExtra("DESC", desc);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), req_code, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onNoteAdd = (OnNoteAdd)getTargetFragment();
        }catch(ClassCastException e){}
    }
}
