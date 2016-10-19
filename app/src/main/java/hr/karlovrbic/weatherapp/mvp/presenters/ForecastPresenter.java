package hr.karlovrbic.weatherapp.mvp.presenters;

import java.util.List;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.mvp.interactors.ForecastInteractor;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.network.ResponseListener;
import hr.karlovrbic.weatherapp.utils.Objects;

/**
 * Created by TheKarlo95 on 18.10.2016..
 */

public class ForecastPresenter implements IForecast.Presenter {

    private IForecast.View view;
    private IForecast.Interactor interactor;

    public ForecastPresenter(IForecast.View view) {
        this.view = Objects.requireNonNull(view, "Parameter view cannnot be null");
        this.interactor = new ForecastInteractor();
    }

    @Override
    public void showVideo(String cityName, String weatherDescription) {

    }

    @Override
    public void getForecast(City city) {
        String cityName = null;
        String countryName = null;
        if(city != null) {
            cityName = city.getName();
            Country country = city.getCountry();
            if(country != null) {
                countryName= country.getName();
            }
        }

        interactor.getForecast(cityName, countryName, new ResponseListener<List<Forecast>>() {
                    @Override
                    public void onSuccess(List<Forecast> result) {
                        view.setForecasts(result);
                    }

                    @Override
                    public void onError(String message) {
                        view.showMessage(message);
                    }
                }
        );
    }

    @Override
    public void cancel() {
        interactor.cancel();
    }
}
