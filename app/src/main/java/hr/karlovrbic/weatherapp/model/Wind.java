package hr.karlovrbic.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Wind {

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Integer degrees;

    public Wind(Double speed, Integer degrees) {
        this.speed = speed;
        this.degrees = degrees;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getDegrees() {
        return degrees;
    }
}
