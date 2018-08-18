package com.example.falgun.stormy;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.falgun.stormy.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.PublicKey;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather currentWeather;
    double lat = 37.8267;
    double lng = -122.4233;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast(lat, lng);
//        Call call = client.newCall(request);
    }

    private void getForecast(double lat, double lng) {
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        TextView darkSky = findViewById(R.id.darkSkyAttribution);
        final ImageView imageIcon = findViewById(R.id.iconImageView);

        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        String apiKey = "dea5b28bf82f9bc79b84010510f7d56d";


        String forecastURL = "https://api.darksky.net/forecast/"+apiKey+"/"+lat+","+lng;
        if(isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .get()
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Postman-Token", "27ed231b-d23d-59e1-2abe-22409720baac")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
//            Response response = client.newCall(request).execute();
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            currentWeather = getCurrentDetails(jsonData);

                            CurrentWeather displayWeather = new CurrentWeather(
                                    currentWeather.getLocationLabel(),
                                    currentWeather.getIcon(),
                                    currentWeather.getTime(),
                                    currentWeather.getTemp(),
                                    currentWeather.getHumidity(),
                                    currentWeather.getPrecipitaionChance(),
                                    currentWeather.getSummary(),
                                    currentWeather.getTimeZone()
                            );

                            binding.setWeather(displayWeather);

                            Drawable image = ContextCompat.getDrawable(MainActivity.this, displayWeather.getIconId());

                            imageIcon.setImageDrawable(image);

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception", e);
                    }
                }
            });

        }
        else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setLocationLabel("Alcatraz Island, CA");
        currentWeather.setPrecipitaionChance(currently.getDouble("precipProbability"));
        currentWeather.setTemp(currently.getDouble("temperature"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTimeZone(forecast.getString("timezone"));

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {

        AlertDialogFragment dialogFragment = new AlertDialogFragment();

        dialogFragment.show(getFragmentManager(), "error_dialog");

    }

    public void refreshOnLCick(View view){
        Toast.makeText(this, R.string.refresh_msg, Toast.LENGTH_LONG).show();
        getForecast(lat, lng);
    }
}
