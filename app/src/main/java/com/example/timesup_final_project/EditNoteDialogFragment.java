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

public class EditNoteDialogFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private EditText editTitle, editDesc;
    private TextView cancel, editNote;
    private Button dateButton, timeButton;
    public static int newAlarmMonth, newAlarmDay, newAlarmYear, newAlarmHour, newAlarmMinute;
    private int old_req_code = 0, new_req_code = 0;

    public interface OnEditNote{
        void editNote(String newTitle, String newDesc, String newDate, String time);
    }
    public OnEditNote onEditNote;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle oldData = getArguments();

        editTitle = view.findViewById(R.id.titleEditText);
        editDesc = view.findViewById(R.id.descEditText);
        cancel = view.findViewById(R.id.cancel);
        editNote = view.findViewById(R.id.okay);
        dateButton = view.findViewById(R.id.datePickerButton);
        timeButton = view.findViewById(R.id.timeButton);

        editTitle.setText(oldData.getString("TITLE"));
        editDesc.setText(oldData.getString("DESC"));
        dateButton.setText(oldData.getString("DATE"));
        timeButton.setText(oldData.getString("TIME"));
        old_req_code = oldData.getInt("OLD_REQ_CODE");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        editNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();
                if(!title.isEmpty()){
                    if(desc.isEmpty()) desc = "Add description...";
                    new_req_code = title.length() + desc.length();
                    Toast.makeText(getContext(), "Edited " + title, Toast.LENGTH_SHORT).show();
                    setAlarm(title, desc);
                    onEditNote.editNote(title, desc, dateButton.getText().toString(), timeButton.getText().toString());
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

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        return view;
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
        newAlarmHour = hour;
        newAlarmMinute = minutes;
        String time;
        if(minutes < 10 ) time = hour + ":0" + minutes;
        else time = hour + ":" + minutes;
        timeButton.setText(time);
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
        newAlarmYear = year;
        newAlarmMonth = month;
        newAlarmDay = day;
        String date = month + 1 + "/" + day + "/" + year;
        dateButton.setText(date);
    }

    private void setAlarm(String title, String desc){
        Calendar current = Calendar.getInstance();
        Calendar setCal = (Calendar)current.clone();
        setCal.set(Calendar.HOUR_OF_DAY, newAlarmHour);
        setCal.set(Calendar.MINUTE, newAlarmMinute);
        setCal.set(Calendar.DAY_OF_MONTH, newAlarmDay);
        setCal.set(Calendar.MONTH, newAlarmMonth);
        setCal.set(Calendar.YEAR, newAlarmYear);
        alarmSetter(setCal, title, desc);
    }

    private void alarmSetter(Calendar alarm, String title, String desc){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getContext(), DeadlineAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), old_req_code, alarmIntent, PendingIntent.FLAG_NO_CREATE);
        if(pendingIntent != null) {
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
        }

        alarmIntent = new Intent(getContext(), DeadlineAlarmReceiver.class);
        alarmIntent.putExtra("TITLE", title);
        alarmIntent.putExtra("DESC", desc);
        pendingIntent = PendingIntent.getBroadcast(getContext(), new_req_code, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onEditNote = (OnEditNote)getTargetFragment();
        }catch(ClassCastException ex){}
    }
}
