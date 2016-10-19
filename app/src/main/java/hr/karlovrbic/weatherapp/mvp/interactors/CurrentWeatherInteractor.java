package hr.karlovrbic.weatherapp.mvp.interactors;

import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.response.CurrentWeatherResponse;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.network.ApiManager;
import hr.karlovrbic.weatherapp.network.ResponseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TheKarlo95 on 18.10.2016..
 */
public class CurrentWeatherInteractor implements IForecast.CurrentWeatherInteractor {

    private Subscription subscription;

    @Override
    public void getCurrentWeather(String city, String country, final ResponseListener<Forecast> listener) {
        String cityAndCountry = city + ", ";
        if (country != null) {
            cityAndCountry += country;
        }

        final Observable<CurrentWeatherResponse> weatherResponse =
                ApiManager.getService().getCurrentWeather(cityAndCountry, Keys.OPEN_WEATHER_KEY);

        subscription = weatherResponse
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CurrentWeatherResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e.getMessage());
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
