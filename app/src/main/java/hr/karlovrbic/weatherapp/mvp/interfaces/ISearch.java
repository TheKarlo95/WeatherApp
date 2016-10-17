package hr.karlovrbic.weatherapp.mvp.interfaces;

import android.os.Bundle;

import hr.karlovrbic.weatherapp.model.City;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public interface ISearch {

    interface View extends IBase.View {
        void onSearchClick();

        void displayCityInputError();

        void setCityInputText(String city);

        void setCountryInputText(String country);

        void startForecastActivity(City city);
    }

    interface Presenter extends IBase.Presenter {
        void init(Bundle savedInstanceState);

        void getForecast(String cityName, String countryName);
    }
}
