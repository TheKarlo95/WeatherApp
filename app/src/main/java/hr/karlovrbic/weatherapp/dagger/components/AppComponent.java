package hr.karlovrbic.weatherapp.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import hr.karlovrbic.weatherapp.dagger.modules.ForecastModule;
import hr.karlovrbic.weatherapp.dagger.modules.SearchModule;
import hr.karlovrbic.weatherapp.dagger.modules.YouTubeVideoModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.ApiModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.ClientModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.ConverterModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.GsonFactoryModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.HTTPTransportModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.HostModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.LogModule;
import hr.karlovrbic.weatherapp.dagger.modules.base.YouTubeModule;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Component(modules = {
        ApiModule.class,
        ClientModule.class,
        ConverterModule.class,
        GsonFactoryModule.class,
        HostModule.class,
        HTTPTransportModule.class,
        LogModule.class,
        YouTubeModule.class
})
@Singleton
public interface AppComponent {

    SearchComponent plus(SearchModule module);

    ForecastComponent plus(ForecastModule module);

    YoutubeVideoComponent plus(YouTubeVideoModule module);
}
