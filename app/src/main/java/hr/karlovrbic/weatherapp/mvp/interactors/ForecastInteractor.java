package hr.karlovrbic.weatherapp.mvp.interactors;

import java.util.List;

import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.WeatherApp;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.response.ForecastsResponse;
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
public class ForecastInteractor implements IForecast.ForecastInteractor {

    private Subscription subscription;

    @Override
    public void getForecast(String city, String country, final ResponseListener<List<Forecast>> listener) {
        String cityAndCountry = city + ", ";
        if (country != null) {
            cityAndCountry += country;
        }

        final Observable<ForecastsResponse> weatherResponse =
                ApiManager.getService().getForecasts(cityAndCountry, "4", Keys.OPEN_WEATHER_KEY);


        subscription = weatherResponse
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForecastsResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(WeatherApp.get().getString(R.string.forecast_interactor_error));
                    }

                    @Override
                    public void onNext(ForecastsResponse weatherResponse) {
                        listener.onSuccess(weatherResponse.getForecasts());
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
