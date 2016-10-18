package hr.karlovrbic.weatherapp.model;

import java.util.Date;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Forecast {

    private City city;
    private Date date;
    private Weather weather;
    private Temperature temperature;
    private Wind wind;
    private Double pressure;
    private Integer humidity;

    public Forecast(City city,
                    Date date,
                    Weather weather,
                    Temperature temperature,
                    Wind wind,
                    Double pressure,
                    Integer humidity) {
        this.city = city;
        this.date = date;
        this.weather = weather;
        this.temperature = temperature;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
