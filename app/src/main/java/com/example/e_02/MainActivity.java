package com.example.e_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final Users users = new Users();
    static final String EXTRA_USER_NAME = "com.data.user.username";
    static final String EXTRA_PASSWORD = "com.data.user.password";
    static final String EXTRA_ARRAY = "com.data.user.users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.Loginbutton);
        Button registerButton = findViewById(R.id.Registerbutton);
        EditText username = findViewById(R.id.editTextTextEmailAddress);
        EditText password = findViewById(R.id.editTextTextPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login(username.getText().toString(),password.getText().toString()))
                {
                    Intent intentUser = new Intent(MainActivity.this,Exercise_03.class);
                    intentUser.putExtra(EXTRA_USER_NAME,username.getText().toString());
                    intentUser.putExtra(EXTRA_PASSWORD,password.getText().toString());
                    startActivity(intentUser);
                    System.out.println(username.toString()+"/USERNAME//PASSWORD:"+password.toString());
                    setContentView(R.layout.activity_exercise_03);
                    Toast.makeText(MainActivity.this,"You are logged in now",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Not match Username/Password, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerButton.setOnClickListener(v->
        {
            //Intent intentRegister = new Intent(MainActivity.this,RegisterActivity.class);
            //intentRegister.putExtra(EXTRA_ARRAY,users);
            setContentView(R.layout.activity_register_actity);
        });
    }
    public boolean login(String username,String password)
    {
        users.loadData();
        System.out.println("Loaded");
        return users.checkUser(username,password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.aboutId)
        {
            Toast.makeText(MainActivity.this,"This is a test example",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (itemId==R.id.settingId)
        {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            return true;
        }
        if (itemId==R.id.shareId)
        {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return true;
    }
}