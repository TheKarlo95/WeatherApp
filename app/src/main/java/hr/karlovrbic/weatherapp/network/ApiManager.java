package hr.karlovrbic.weatherapp.network;

import android.util.Log;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import hr.karlovrbic.weatherapp.BuildConfig;
import hr.karlovrbic.weatherapp.network.deserializers.DateDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiManager {

    public static final String API_ENDPOINT = "http://api.openweathermap.org/data/2.5/";

    private static final WeatherService WEATHER_SERVICE;

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static final JsonFactory JSON_FACTORY = new GsonFactory();

    static {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (BuildConfig.DEBUG) {
                    Log.d("api_tag", message);
                }
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(API_ENDPOINT)
                .client(client)
                .build();

        WEATHER_SERVICE = retrofit.create(WeatherService.class);
    }

    public static WeatherService getService() {
        return WEATHER_SERVICE;
    }
}
