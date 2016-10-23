package hr.karlovrbic.weatherapp.dagger.modules.base;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.weatherapp.BuildConfig;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class LogModule {

    @Provides
    @Singleton
    public HttpLoggingInterceptor.Level provideLogLevel() {
        if (BuildConfig.DEBUG) {
            return HttpLoggingInterceptor.Level.BODY;
        } else {
            return HttpLoggingInterceptor.Level.NONE;
        }
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideLog(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);
        return interceptor;
    }
}
