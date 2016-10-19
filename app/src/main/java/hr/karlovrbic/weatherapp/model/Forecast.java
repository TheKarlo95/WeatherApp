package hr.karlovrbic.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Forecast implements Parcelable {

    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel source) {
            return new Forecast(source);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

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

    protected Forecast(Parcel in) {
        this.city = in.readParcelable(City.class.getClassLoader());
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.weather = in.readParcelable(Weather.class.getClassLoader());
        this.temperature = in.readParcelable(Temperature.class.getClassLoader());
        this.wind = in.readParcelable(Wind.class.getClassLoader());
        this.pressure = (Double) in.readValue(Double.class.getClassLoader());
        this.humidity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.city, flags);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeParcelable(this.weather, flags);
        dest.writeParcelable(this.temperature, flags);
        dest.writeParcelable(this.wind, flags);
        dest.writeValue(this.pressure);
        dest.writeValue(this.humidity);
    }

    public City getCity() {
        return city;
    }

    public Date getDate() {
        return date;
    }

    public Weather getWeather() {
        return weather;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public Double getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }
}
