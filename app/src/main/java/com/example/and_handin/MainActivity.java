package com.example.and_handin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.and_handin.OnlineWeatherApi.HttpHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private static String url = "https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&exclude=hourly,daily&appid=70742a4ee46a7faef64cb6003f3b8f4f";
    ArrayList<HashMap<String, String>> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        new GetWeatherInfo().execute();
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
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject c = jsonObject.getJSONObject("current");
                    String temp = c.getString("temp");
                    String timezone = c.getString("feels_like");
                    String humidity = c.getString("humidity");
                    HashMap<String, String> weather = new HashMap<>();
                    weather.put("temp", temp);
                    weather.put("feel", timezone);
                    weather.put("hum", humidity);
                    weatherList.add(weather);
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
                    R.layout.list_item, new String[]{"temp", "feel",
                    "hum", "temp", "feel",
                    "hum"}, new int[]{R.id.temp,
                    R.id.feel, R.id.hum, R.id.tempt, R.id.feelt, R.id.humt});
            lv.setAdapter(adapter);
        }
    }
}