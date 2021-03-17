package com.example.e_02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_02.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ACW extends AppCompatActivity {
    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "a68f35363748b9e38dd6bf670d528ea0";
    public static String lat = "35";
    public static String lon = "139";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = findViewById(R.id.buttonFW);
        TextView TW = findViewById(R.id.textViewWEA);
        button.setOnClickListener(v->
        {
            System.out.println("___________________________________");
            Toast.makeText(ACW.this,"Done",Toast.LENGTH_SHORT).show();
            TW.setText("ASASAS");
        });
    }
}