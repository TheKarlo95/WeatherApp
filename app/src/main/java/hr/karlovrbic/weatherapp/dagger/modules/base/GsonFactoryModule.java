package hr.karlovrbic.weatherapp.dagger.modules.base;

import com.google.api.client.json.gson.GsonFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class GsonFactoryModule {

    @Provides
    @Singleton
    public GsonFactory provideYoutube() {
        return new GsonFactory();
    }
}
