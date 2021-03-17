package com.example.e_02;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.e_02.MainActivity.EXTRA_PASSWORD;
import static com.example.e_02.MainActivity.EXTRA_USER_NAME;

public class Exercise_03 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_03);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch imgSwitch = findViewById(R.id.switch1);
        TextView Username = findViewById(R.id.E3Username);
        TextView Password = findViewById(R.id.E3PASSWORD);
        Button logout = findViewById(R.id.button2);
        Button show = findViewById(R.id.button);
        Button GOTO = findViewById(R.id.GOTO);
        ImageView img = findViewById(R.id.imageView);
        img.setVisibility(View.INVISIBLE);
        show.setOnClickListener(v ->
        {
            Bundle bundleUser = getIntent().getExtras();
            if (bundleUser != null && bundleUser.containsKey(EXTRA_USER_NAME) && bundleUser.containsKey(EXTRA_PASSWORD)) {
                String userName = bundleUser.getString(EXTRA_USER_NAME);
                Username.setText(userName);
                String passWord = bundleUser.getString(EXTRA_PASSWORD);
                Password.setText(passWord);
                Toast.makeText(Exercise_03.this, "Info showed", Toast.LENGTH_SHORT).show();
                System.out.println("Import done");
            }
        });
        logout.setOnClickListener(v ->
        {
            setContentView(R.layout.activity_main);
            onDestroy();
        });
        imgSwitch.setOnClickListener(v->
        {
            if (imgSwitch.isChecked())
            {
                img.setVisibility(View.VISIBLE);
            }
            else {
                img.setVisibility(View.INVISIBLE);
            }
        });
        GOTO.setOnClickListener(v->{
            setContentView(R.layout.activity_a_c_w);
        });
    }
  //  @Override
  //  public boolean onCreateOptionsMenu(Menu menu) {
  //      getMenuInflater().inflate(R.menu.menu_main,menu);
  //      return true;
  //  }
}