package hr.karlovrbic.weatherapp.mvp.interactors;

import javax.inject.Inject;

import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.WeatherApp;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.response.CurrentWeatherResponse;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.network.ResponseListener;
import hr.karlovrbic.weatherapp.network.WeatherService;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TheKarlo95 on 18.10.2016..
 */
public class CurrentWeatherInteractor implements IForecast.CurrentWeatherInteractor {

    private WeatherService service;
    private Subscription subscription;

    @Inject
    public CurrentWeatherInteractor(WeatherService service) {
        this.service = service;
    }

    @Override
    public void getCurrentWeather(String city, String country, final ResponseListener<Forecast> listener) {
        String cityAndCountry = city;
        if (country != null) {
            cityAndCountry += "," + country;
        }

        final Observable<CurrentWeatherResponse> weatherResponse =
                service.getCurrentWeather(cityAndCountry, Keys.OPEN_WEATHER_KEY);

        subscription = weatherResponse
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CurrentWeatherResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(WeatherApp.get().getString(R.string.current_weather_interactor_error));
                    }

                    @Override
                    public void onNext(CurrentWeatherResponse weatherResponse) {
                        listener.onSuccess(weatherResponse.getForecast());
                    }
                });
    }

    @Override
    public void cancel() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
