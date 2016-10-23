package hr.karlovrbic.weatherapp.dagger.modules;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.weatherapp.mvp.interactors.CurrentWeatherInteractor;
import hr.karlovrbic.weatherapp.mvp.interactors.ForecastInteractor;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.mvp.presenters.ForecastPresenter;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class ForecastModule {

    private IForecast.View view;

    public ForecastModule(IForecast.View view) {
        this.view = view;
    }

    @Provides
    public IForecast.View provideView() {
        return view;
    }

    @Provides
    public IForecast.Presenter providePresenter(ForecastPresenter presenter) {
        return presenter;
    }

    @Provides
    public IForecast.ForecastInteractor provideForecastInteractor(ForecastInteractor interactor) {
        return interactor;
    }

    @Provides
    public IForecast.CurrentWeatherInteractor provideCurrentWeatherInteractor(CurrentWeatherInteractor interactor) {
        return interactor;
    }
}
