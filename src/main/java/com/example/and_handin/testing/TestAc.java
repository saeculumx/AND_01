package com.example.and_handin.testing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.and_handin.OnlineWeatherApi.WeatherService;
import com.example.and_handin.R;

public class TestAc extends AppCompatActivity {
    WeatherService weatherService = new WeatherService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        TextView t1 = findViewById(R.id.textView);
        TextView t2 = findViewById(R.id.textView2);
        TextView t3 = findViewById(R.id.textView3);
        TextView t4 = findViewById(R.id.textView4);
        t2.setText(weatherService.getAddress());
    }


}