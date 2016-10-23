package hr.karlovrbic.weatherapp.mvp.presenters;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.mvp.interfaces.ISearch;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class SearchPresenter implements ISearch.Presenter {

    private ISearch.View view;

    private Map<String, String> countries;

    @Inject
    public SearchPresenter(ISearch.View view) {
        this.view = view;
    }

    @Override
    public void showForecast(String cityName, String countryName) {
        if (validateCityInput(cityName)) {
            Country country = null;
            if (countryName != null && !countryName.isEmpty()) {
                country = new Country(getISO2Code(countryName), countryName);
            }
            City city = new City(cityName, country);

            view.startForecastActivity(city);
        }
    }

    @Override
    public void cancel() {
    }

    private boolean validateCityInput(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            view.displayCityInputError();
            return false;
        } else {
            return true;
        }
    }

    private String getISO2Code(String countryName) {
        if (countries == null) {
            initCountriesMap();
        }
        return countries.get(countryName);
    }

    private void initCountriesMap() {
        countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
    }
}
