package hr.karlovrbic.weatherapp.dagger.modules.base;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.weatherapp.network.WeatherService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public WeatherService provideApiService(OkHttpClient client,
                                            CallAdapter.Factory callFactory,
                                            @Named("gson") Converter.Factory gson,
                                            @Named("scalar") Converter.Factory scalar,
                                            HttpUrl endpoint) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(client)
                .addCallAdapterFactory(callFactory)
                .addConverterFactory(scalar)
                .addConverterFactory(gson)
                .build();

        return retrofit.create(WeatherService.class);
    }
}