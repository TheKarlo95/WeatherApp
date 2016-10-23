package hr.karlovrbic.weatherapp.dagger.modules.base;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.weatherapp.BuildConfig;
import okhttp3.HttpUrl;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class HostModule {

    private static final int NETWORK_TIMEOUT_SECONDS = 60;

    @Provides
    @Singleton
    public HttpUrl provideEndpoint() {
        return HttpUrl.parse(BuildConfig.API_URL);
    }

    @Provides
    @Singleton
    public Integer provideNetworkTimeout() {
        return NETWORK_TIMEOUT_SECONDS;
    }
}
