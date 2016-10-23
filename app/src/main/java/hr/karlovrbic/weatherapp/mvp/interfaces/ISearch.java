package hr.karlovrbic.weatherapp.mvp.interfaces;

import hr.karlovrbic.weatherapp.model.City;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public interface ISearch {

    interface View extends IBase.View {
        void onSearchClick();

        void startForecastActivity(City city);

        void displayCityInputError();
    }

    interface Presenter extends IBase.Presenter {
        void showForecast(String cityName, String countryName);
    }
}
