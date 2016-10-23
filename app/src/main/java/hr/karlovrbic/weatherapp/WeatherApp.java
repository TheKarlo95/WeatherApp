package hr.karlovrbic.weatherapp;

import android.app.Application;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
public class WeatherApp extends Application {

    private static WeatherApp instance;

    public static WeatherApp get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
