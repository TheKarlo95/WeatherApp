package hr.karlovrbic.weatherapp.model;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Forecast {

    private City city;
    private Weather weather;
    private Temperature temperature;
    private Wind wind;
    private Integer pressure;
    private Integer humidity;

    public Forecast(City city, Weather weather, Temperature temperature, Wind wind, Integer pressure, Integer humidity) {
        this.city = city;
        this.weather = weather;
        this.temperature = temperature;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
