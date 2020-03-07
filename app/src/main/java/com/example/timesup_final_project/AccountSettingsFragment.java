package com.example.timesup_final_project;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AccountSettingsFragment extends Fragment {

    public AccountSettingsFragment() {}

    private EditText newFirstName, newLastName, newEmail, newContact;
    private Button editButton;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Account Settings");
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);

        newFirstName = view.findViewById(R.id.edit_firstName);
        newLastName = view.findViewById(R.id.edit_lastName);
        newEmail = view.findViewById(R.id.edit_email);
        newContact = view.findViewById(R.id.edit_contactNo);
        editButton = view.findViewById(R.id.edit_button);

        databaseHelper = new DatabaseHelper(getActivity());

        newFirstName.setText(CurrentUser.getFirstname());
        newLastName.setText(CurrentUser.getLastname());
        newEmail.setText(CurrentUser.getEmail());
        newContact.setText(CurrentUser.getContactNo());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme)
                        .setIcon(R.drawable.edit_icon)
                        .setTitle(R.string.edit_account)
                        .setMessage(R.string.edit_account_message)
                        .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                CurrentUser.setUser(newFirstName.getText().toString(),
                                                    newLastName.getText().toString(),
                                                    newEmail.getText().toString(),
                                                    newContact.getText().toString());
                                TextView username = MainActivity.headerView.findViewById(R.id.userNameTitle);
                                TextView emailAdd = MainActivity.headerView.findViewById(R.id.emailAddTitle);
                                TextView contactno = MainActivity.headerView.findViewById(R.id.contactNoTitle);
                                username.setText(CurrentUser.getFirstname() + " " + CurrentUser.getLastname());
                                emailAdd.setText(CurrentUser.getEmail());
                                contactno.setText(CurrentUser.getContactNo());

                                databaseHelper.updateUser(CurrentUser.getUserName(), CurrentUser.getFirstname(), CurrentUser.getLastname(),
                                                    CurrentUser.getEmail(), CurrentUser.getContactNo());
                                Toast.makeText(getActivity(), "Your account has been updated! " + CurrentUser.getFirstname(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog editDialog = builder.create();
                editDialog.show();
            }

        });
        return view;
    }

}
