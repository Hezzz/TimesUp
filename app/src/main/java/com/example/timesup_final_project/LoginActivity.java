package com.example.timesup_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity{

    private TextView regLink;
    private Button loginButton;
    private EditText username, password;
    private final int REG_ACTIVITY_REQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regLink = findViewById(R.id.regLink);
        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent register = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(register, REG_ACTIVITY_REQ);
            }
        });

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(400);
                    Toast.makeText(getApplicationContext(), "Please enter your username/password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent regIntent){
        if(requestCode == REG_ACTIVITY_REQ){
            if (resultCode == AppCompatActivity.RESULT_OK){
                username.setText(regIntent.getStringExtra("USERNAME"));
                password.setText(regIntent.getStringExtra("PASSWORD"));
            }
        }
    }

}
