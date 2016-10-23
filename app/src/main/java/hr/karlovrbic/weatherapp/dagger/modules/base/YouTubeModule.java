package hr.karlovrbic.weatherapp.dagger.modules.base;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class YouTubeModule {

    @Provides
    @Singleton
    public com.google.api.services.youtube.YouTube provideYoutube(HttpTransport httpTransport, GsonFactory factory) {
        return new YouTube.Builder(httpTransport,
                factory,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("weather-app").build();
    }
}
