package com.example.and_handin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.and_handin.OnlineWeatherApi.HttpHandler;
import com.example.and_handin.OnlineWeatherApi.WeatherService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    double lat;
    double lon;
    private final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private ActionBar toolBar;
    private String cityInfo;
    ArrayList<HashMap<String, String>> weatherList;
    HashMap<String, String> weather = new HashMap<>();
    SwipeRefreshLayout swipeRefreshLayout;
    private boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        weatherList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        toolBar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolBar.setTitle("Weather");
        GetLocation(this);
        new GetWeatherInfo().execute();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isUpdate = true;
                GetLocation(MainActivity.this);
                new GetWeatherInfo().execute();
                weatherList.remove(weather);
                swipeRefreshLayout.setRefreshing(false);
                isUpdate = false;
            }
        });
    }
    private void updateHashMap(String cityname, String temp, String felt, String weathercon,String hum)
    {
        if (!weather.containsKey("cityname"))
        {
            weather.put("cityname",cityname);
        }
        else
        {
            weather.put("cityname",weather.get("cityname")+1);
        }
        if (!weather.containsKey("temp"))
        {
            weather.put("temp",temp);
        }
        else
        {
            weather.put("temp",weather.get("temp")+1);
        }
        if (!weather.containsKey("felt"))
        {
            weather.put("felt",felt);
        }
        else
        {
            weather.put("felt",weather.get("felt")+1);
        }
        if (!weather.containsKey("weathercon"))
        {
            weather.put("weathercon",weathercon);
        }
        else
        {
            weather.put("weathercon",weather.get("weathercon")+1);
        }
        if (!weather.containsKey("hum"))
        {
            weather.put("hum",hum);
        }
        else
        {
            weather.put("hum",weather.get("hum")+1);
        }
    }
    private class GetWeatherInfo extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please stand by");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=hourly,daily&appid=70742a4ee46a7faef64cb6003f3b8f4f";
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject c = jsonObject.getJSONObject("current");
                    JSONArray jsonArraya = c.getJSONArray("weather");
                    String weatherCon = jsonArraya.getJSONObject(0).getString("main");
                    String temp = c.getString("temp");
                    double tet = (Double.parseDouble(temp) - 272);
                    int a = (int) Math.round(tet);
                    String ft = "Temperature " + a + "°C";
                    String fetemp = c.getString("feels_like");
                    double fet = (Double.parseDouble(fetemp) - 272);
                    int b = (int) Math.round(fet);
                    String ff = "Feeling Temperature " + b + "°C";
                    String humidity = c.getString("humidity");
                    String fh = "Humidity " + humidity + "%";
                    if (isUpdate)
                    {
                        updateHashMap(cityInfo,ft,ff,weatherCon,fh);

                        //weatherList.add(weather);
                    }
                    else
                    {
                        weather.put("temp", ft);
                        weather.put("feel", ff);
                        weather.put("hum", fh);
                        weather.put("cityname", cityInfo);
                        weather.put("weathercon", weatherCon);
                        weatherList.add(weather);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            Result result = new Result() {
                @Override
                public void setSystemId(String systemId) {

                }

                @Override
                public String getSystemId() {
                    return null;
                }
            };
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, weatherList,
                    R.layout.list_item, new String[]{"cityname","temp", "feel","weathercon",
                    "hum"}, new int[]{R.id.cityname,R.id.temp,
                    R.id.feel,R.id.weathercon, R.id.hum});
            lv.setAdapter(adapter);


        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolBar.setTitle("Weather");
                    return true;
                case R.id.navigation_gifts:
                    toolBar.setTitle("My Gifts");
                    return true;
                case R.id.navigation_cart:
                    toolBar.setTitle("Cart");
                    return true;
                case R.id.navigation_profile:
                    toolBar.setTitle("Profile");
                    return true;
            }
            return false;
        }
    };

    private void GetLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider;
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "No gps available", Toast.LENGTH_SHORT).show();
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
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
            getAddresses(location);
        } else {
            locationManager.requestLocationUpdates(locationProvider, 0, 0, mLocationLisener);
        }
    }
    private void showLocation (Location location)
    {
        lat = location.getLatitude();
        lon = location.getLongitude();
        String address = "lat: "+location.getLatitude()+" lon: "+location.getLongitude();
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
        public void onLocationChanged(@NonNull Location location) {
            showLocation(location);
        }
    };
    private List<Address> getAddresses(Location location)
    {
        System.out.println("???1");
        List<Address> result = null;
        try{
            if (location!=null)
            {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                System.out.println(cityInfo+"???2");
                result = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                cityInfo = result.get(0).getLocality();
                System.out.println(cityInfo+"???3");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    };
}

