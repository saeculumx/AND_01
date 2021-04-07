package com.example.and_handin.OnlineWeatherApi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class WeatherService {
    Location location = new Location();
    double lat = location.getLat();
    double lon = location.getLon();
    String address = location.getAddress();

    public String getAddress() {
        return address;
    }
}
