package com.example.and_handin.OnlineWeatherApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.and_handin.R;

import java.util.List;
import java.util.Locale;

public class Location extends AppCompatActivity {
    double lat;
    double lon;
    String address;
    String cityInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    private void GetLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        android.location.Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
        } else {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, mLocationLisener);
        }
    }
    private void showLocation (android.location.Location location)
    {
        lat = location.getLatitude();
        lon = location.getLongitude();
        address = "lat: "+location.getLatitude()+" lon: "+location.getLongitude();
        System.out.println(address);
    }
    LocationListener mLocationLisener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
        @Override
        public void onProviderEnabled(String provider)
        {

        }
        @Override
        public void onProviderDisabled(String provider)
        {

        }
        @Override
        public void onLocationChanged(@NonNull android.location.Location location) {
            showLocation(location);
        }
    };
    private List<Address> getAddresses(Location location)
    {
        List<Address> result = null;
        try{
            if (location!=null)
            {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                result = geocoder.getFromLocation(location.getLat(),location.getLon(),1);
                cityInfo = result.get(0).getLocality();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    };
    public double getLat()
    {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getAddress() {
        return address;
    }
}