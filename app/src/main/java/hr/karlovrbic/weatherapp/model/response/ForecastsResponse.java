package hr.karlovrbic.weatherapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.Temperature;
import hr.karlovrbic.weatherapp.model.Weather;
import hr.karlovrbic.weatherapp.model.Wind;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class ForecastsResponse {

    @SerializedName("city")
    @Expose
    public City city;
    @SerializedName("cnt")
    @Expose
    public int cnt;
    @SerializedName("list")
    @Expose
    public List<ForecastInfo> list = new ArrayList<>();

    public List<Forecast> getForecasts() {
        List<Forecast> forecasts = new ArrayList<>();
        for (ForecastInfo forecastInfo : list) {
            forecasts.add(forecastInfo.getForecast(city.getCity()));
        }
        if (forecasts.isEmpty()) {
            return null;
        } else {
            return forecasts;
        }
    }

    public class City {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("country")
        @Expose
        public String countryCode;

        private hr.karlovrbic.weatherapp.model.City city;

        public hr.karlovrbic.weatherapp.model.City getCity() {
            if(city == null) {
                city = new hr.karlovrbic.weatherapp.model.City(id, name, new Country(countryCode));
            }
            return city;
        }
    }

    public class ForecastInfo {

        @SerializedName("dt")
        @Expose
        public Date dt;
        @SerializedName("temp")
        @Expose
        public Temp temp;
        @SerializedName("pressure")
        @Expose
        public double pressure;
        @SerializedName("humidity")
        @Expose
        public int humidity;
        @SerializedName("speed")
        @Expose
        public double windSpeed;
        @SerializedName("deg")
        @Expose
        public double windDegrees;
        @SerializedName("weather")
        @Expose
        public java.util.List<Weather> weather = new ArrayList<Weather>();

        public Forecast getForecast(hr.karlovrbic.weatherapp.model.City city) {
            return new Forecast(city,
                    dt,
                    weather.get(0),
                    new Temperature(null, temp.min, temp.max),
                    new Wind(windSpeed, windDegrees),
                    pressure,
                    humidity);
        }
    }

    public class Temp {

        @SerializedName("min")
        @Expose
        public double min;
        @SerializedName("max")
        @Expose
        public double max;
    }
}
