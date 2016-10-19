package hr.karlovrbic.weatherapp.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.Temperature;
import hr.karlovrbic.weatherapp.model.Weather;

/**
 * Created by TheKarlo95 on 19.10.2016..
 */
public class CurrentWeatherResponse {

    @SerializedName("weather")
    @Expose
    public List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    public Main main;
    @SerializedName("wind")
    @Expose
    public Wind wind;
    @SerializedName("dt")
    @Expose
    public Date dt;
    @SerializedName("sys")
    @Expose
    public Sys sys;
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;

    public Forecast getForecast() {
        return new Forecast(new City(id, name, new Country(sys.country)),
                dt,
                weather.get(0),
                main.getTemperature(),
                new hr.karlovrbic.weatherapp.model.Wind(wind.speed, wind.deg),
                main.pressure,
                main.humidity);
    }

    public class Wind {

        @SerializedName("speed")
        @Expose
        public double speed;
        @SerializedName("deg")
        @Expose
        public double deg;
    }

    public class Main {

        @SerializedName("temp")
        @Expose
        public double tempCurrent;
        @SerializedName("pressure")
        @Expose
        public double pressure;
        @SerializedName("humidity")
        @Expose
        public int humidity;

        public Temperature getTemperature() {
            return new Temperature(tempCurrent, null, null);
        }
    }

    public class Sys {

        @SerializedName("country")
        @Expose
        public String country;
    }
}
