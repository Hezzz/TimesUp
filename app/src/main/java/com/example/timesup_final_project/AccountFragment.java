package com.example.timesup_final_project;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccountFragment extends Fragment {

    public AccountFragment() {}

    private TextView account_name, account_email, account_contact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setActionBarTitle("Account Information");
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        account_name = view.findViewById(R.id.account_name);
        account_email = view.findViewById(R.id.account_email);
        account_contact = view.findViewById(R.id.account_contact);

        account_name.setText(CurrentUser.getFirstname() + " " + CurrentUser.getLastname());
        account_email.setText(CurrentUser.getEmail());
        account_contact.setText(CurrentUser.getContactNo());

        return view;
    }

}
