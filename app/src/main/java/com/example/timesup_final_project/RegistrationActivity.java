package com.example.timesup_final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity{

    private Button registerButton;
    private EditText username, password, firstname,
        lastname, email, contactNo, confirmPass;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelper = new DatabaseHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);
        confirmPass = findViewById(R.id.confirmPass);

        registerButton = findViewById(R.id.regButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty() ||
                    firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() ||
                    email.getText().toString().isEmpty() || contactNo.getText().toString().isEmpty() ||
                    confirmPass.getText().toString().isEmpty()){
                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(400);
                    Toast.makeText(getApplicationContext(), R.string.fill_all, Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.getText().toString().equals(confirmPass.getText().toString())){

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.DialogTheme)
                                .setIcon(R.drawable.account_circle)
                                .setTitle("Register Account?")
                                .setMessage("Are you sure about your details")
                                .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i){
                                    }
                                })
                                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        UserClass user = new UserClass(username.getText().toString(),
                                                password.getText().toString(), firstname.getText().toString(),
                                                lastname.getText().toString(), email.getText().toString(),
                                                contactNo.getText().toString());

                                        if (databaseHelper.insertUser(user)){
                                            Intent backLogin = new Intent();
                                            backLogin.putExtra("USERNAME", username.getText().toString());
                                            backLogin.putExtra("PASSWORD", password.getText().toString());
                                            Toast.makeText(getApplicationContext(), R.string.user_created, Toast.LENGTH_SHORT).show();
                                            setResult(Activity.RESULT_OK, backLogin);
                                            finish();
                                        }

                                        else{
                                            Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                                            vibrator.vibrate(400);
                                            Toast.makeText(getApplicationContext(), R.string.user_exists, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        AlertDialog registerUser = builder.create();
                        registerUser.show();
                    }
                    else{
                        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(400);
                        Toast.makeText(getApplicationContext(), R.string.password_mismatch, Toast.LENGTH_SHORT).show();
                        confirmPass.setText("");
                        password.setText("");
                    }
                }
            }
        });
    }
}
