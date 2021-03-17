package com.example.e_02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Users users = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_actity);
        EditText username = findViewById(R.id.RegisterUsername);
        EditText password = findViewById(R.id.editTextTextPassword);
        EditText confirmPassword = findViewById(R.id.ConfirmPassword);
        Button login = findViewById(R.id.RegisterLogin);
        String usernameR = username.getText().toString();
        String passwordR = password.getText().toString();
        String passwordRR = confirmPassword.getText().toString();
        login.setOnClickListener(v ->
        {
            System.out.println("Clicked");
            Toast.makeText(RegisterActivity.this,"Register done",Toast.LENGTH_SHORT).show();
            users.addUser(new User(usernameR,passwordR));

        });
    }
}