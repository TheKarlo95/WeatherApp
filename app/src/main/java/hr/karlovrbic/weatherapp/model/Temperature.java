package hr.karlovrbic.weatherapp.model;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */

public class Temperature {

    private Double currentTemperature;
    private Double minTemperature;
    private Double maxTemperature;

    public Temperature(Double minTemperature, Double maxTemperature, Double currentTemperature) {
        this.currentTemperature = currentTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public Temperature(Double minTemperature, Double maxTemperature) {
        this(minTemperature, maxTemperature, null);
    }
}
