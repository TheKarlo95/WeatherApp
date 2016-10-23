package hr.karlovrbic.weatherapp.mvp.presenters;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.mvp.interfaces.ISearch;
import hr.karlovrbic.weatherapp.utils.Objects;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class SearchPresenter implements ISearch.Presenter {

    private static final String CITY_INPUT = "city_input";
    private static final String COUNTRY_INPUT = "country_input";

    private ISearch.View view;

    private Map<String, String> countries;

    public SearchPresenter(ISearch.View view) {
        this.view = Objects.requireNonNull(view, "Parameter view cannnot be null");

        countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String city = savedInstanceState.getString(CITY_INPUT);
            String country = savedInstanceState.getString(COUNTRY_INPUT);
            view.setCityInputText(city);
            view.setCountryInputText(country);
        }
    }

    @Override
    public void showForecast(String cityName, String countryName) {
        if (validateCityInput(cityName)) {
            Country country = null;
            if(countryName != null && !countryName.isEmpty()) {
                country = new Country(getISO2Code(countryName), countryName);
            }
            City city = new City(cityName, country);

            view.startForecastActivity(city);
        }
    }

    @Override
    public void cancel() {
    }

    public boolean validateCityInput(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            view.displayCityInputError();
            return false;
        } else {
            return true;
        }
    }

    private String getISO2Code(String countryName) {
        return countries.get(countryName);
    }
}
