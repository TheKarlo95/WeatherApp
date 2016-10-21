package hr.karlovrbic.weatherapp.mvp.presenters;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.mvp.interactors.CurrentWeatherInteractor;
import hr.karlovrbic.weatherapp.mvp.interactors.ForecastInteractor;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.network.ResponseListener;
import hr.karlovrbic.weatherapp.utils.DateUtils;
import hr.karlovrbic.weatherapp.utils.Objects;

/**
 * Created by TheKarlo95 on 18.10.2016..
 */

public class ForecastPresenter implements IForecast.Presenter {

    @NonNull
    private IForecast.View view;
    @NonNull
    private IForecast.ForecastInteractor forecastInteractor;
    @NonNull
    private IForecast.CurrentWeatherInteractor currentWeatherInteractor;

    private int callCounter;
    private List<Forecast> forecasts;
    private Forecast currentWeather;

    public ForecastPresenter(IForecast.View view) {
        this.view = Objects.requireNonNull(view, "Parameter view cannnot be null");
        this.forecastInteractor = new ForecastInteractor();
        this.currentWeatherInteractor = new CurrentWeatherInteractor();
    }

    @Override
    public void showVideo(String cityName, String weatherDescription) {
        String keywords = cityName + " " + weatherDescription;
        view.startVideoActivity(keywords);
    }

    @Override
    public void getForecast(City city) {
        view.showProgress();
        callCounter = 2;

        String cityName = null;
        String countryName = null;
        if (city != null) {
            cityName = city.getName();
            Country country = city.getCountry();
            if (country != null) {
                countryName = country.getName();
            }
        }

        forecastInteractor.getForecast(cityName, countryName, new ResponseListener<List<Forecast>>() {
            @Override
            public void onSuccess(List<Forecast> result) {
                setForecasts(result);
                hideProgress();
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
                view.hideProgress();
            }
        });
        currentWeatherInteractor.getCurrentWeather(cityName, countryName, new ResponseListener<Forecast>() {
            @Override
            public void onSuccess(Forecast result) {
                setCurrentWeather(result);
                hideProgress();
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
                view.hideProgress();
            }
        });
    }

    @Override
    public void cancel() {
        forecastInteractor.cancel();
        view.hideProgress();
    }

    private void hideProgress() {
        callCounter--;
        if (callCounter <= 0) {
            view.hideProgress();
        }
    }

    private void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
        setForecasts();
    }

    private void setCurrentWeather(Forecast currentWeather) {
        this.currentWeather = currentWeather;
        setForecasts();
    }

    private void setForecasts() {
        if (currentWeather != null && forecasts != null) {
            if (!DateUtils.isToday(forecasts.get(0).getDate())) {
                forecasts = forecasts.subList(1, forecasts.size());
            }

            currentWeather.getTemperature().setMax(forecasts.get(0).getTemperature().getMax());
            currentWeather.getTemperature().setMin(forecasts.get(0).getTemperature().getMin());

            List<Forecast> allForecasts = new ArrayList<>(3);
            allForecasts.add(currentWeather);
            allForecasts.addAll(forecasts.subList(1, forecasts.size()));

            view.setForecasts(allForecasts);
            view.setCity(currentWeather.getCity());
        }
    }
}
