package hr.karlovrbic.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Wind implements Parcelable {

    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel source) {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

    private Double speed;
    private Double degrees;

    public Wind(Double speed, Double degrees) {
        this.speed = speed;
        this.degrees = degrees;
    }

    protected Wind(Parcel in) {
        this.speed = (Double) in.readValue(Double.class.getClassLoader());
        this.degrees = (Double) in.readValue(Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.speed);
        dest.writeValue(this.degrees);
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getDegrees() {
        return degrees;
    }
}
