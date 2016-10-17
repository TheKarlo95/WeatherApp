package hr.karlovrbic.weatherapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.Temperature;
import hr.karlovrbic.weatherapp.model.Weather;
import hr.karlovrbic.weatherapp.model.Wind;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class WeatherResponse {

    @SerializedName("weather")
    @Expose
    private List<Weather> weathers = new ArrayList<>();
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private int cityId;
    @SerializedName("name")
    @Expose
    private String cityName;

    public Forecast getForecastModel() {
        City city = new City(cityId, cityName, sys.getCountry());
        Weather weather = null;
        if (weathers != null) {
            weather = weathers.get(0);
        }
        Temperature temperature = new Temperature(main.tempMin, main.tempMax, main.temp);
        return new Forecast(city, weather, temperature, wind, main.pressure, main.humidity);
    }

    class Main {

        @SerializedName("temp")
        @Expose
        private Double temp;
        @SerializedName("pressure")
        @Expose
        private Integer pressure;
        @SerializedName("humidity")
        @Expose
        private Integer humidity;
        @SerializedName("temp_min")
        @Expose
        private Double tempMin;
        @SerializedName("temp_max")
        @Expose
        private Double tempMax;
    }

    class Sys {

        @SerializedName("country")
        @Expose
        private String countryCode;

        Country getCountry() {
            return new Country(countryCode);
        }
    }
}
