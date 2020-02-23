package com.example.timesup_final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity{

    private Button registerButton;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        registerButton = findViewById(R.id.regButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backLogin = new Intent();
                backLogin.putExtra("USERNAME", username.getText().toString());
                backLogin.putExtra("PASSWORD", password.getText().toString());
                setResult(Activity.RESULT_OK, backLogin);
                finish();
            }
        });
    }
}
