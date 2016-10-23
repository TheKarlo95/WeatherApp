package hr.karlovrbic.weatherapp;

import android.app.Application;

import hr.karlovrbic.weatherapp.dagger.components.AppComponent;
import hr.karlovrbic.weatherapp.dagger.components.DaggerAppComponent;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
public class WeatherApp extends Application {

    private static WeatherApp instance;
    private AppComponent appComponent;

    public static WeatherApp get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
