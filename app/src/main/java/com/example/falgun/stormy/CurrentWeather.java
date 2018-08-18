package com.example.falgun.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by falgun on 17/8/18.
 */

public class CurrentWeather {

    private String locationLabel;
    private String icon;
    private long time;
    private double temp;
    private double humidity;
    private double precipitationChance;
    private String summary;
    private String timeZone;

    public CurrentWeather(){

    }

    public CurrentWeather(String locationLabel, String icon, long time, double temp, double humidity, double precipitationChance, String summary, String timeZone) {
        this.locationLabel = locationLabel;
        this.icon = icon;
        this.time = time;
        this.temp = temp;
        this.humidity = humidity;
        this.precipitationChance = precipitationChance;
        this.summary = summary;
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIconId(){
        int iconId = R.drawable.clear_day;

        switch (icon) {
            case "clear-day" : iconId = R.drawable.clear_day;
            break;
            case "clear-night": iconId = R.drawable.clear_night;
            break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }
        return  iconId;
    }

    public long getTime() {
        return time;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");

        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipitaionChance() {
        return precipitationChance;
    }

    public void setPrecipitaionChance(double precipitationChance) {
        this.precipitationChance = precipitationChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
