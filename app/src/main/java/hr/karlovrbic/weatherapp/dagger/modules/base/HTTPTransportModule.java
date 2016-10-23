package hr.karlovrbic.weatherapp.dagger.modules.base;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class HTTPTransportModule {

    @Provides
    @Singleton
    public HttpTransport provideYoutube() {
        return new NetHttpTransport();
    }
}
