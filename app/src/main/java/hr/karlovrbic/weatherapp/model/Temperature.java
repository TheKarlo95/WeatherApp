package hr.karlovrbic.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */

public class Temperature implements Parcelable {

    public static final Parcelable.Creator<Temperature> CREATOR = new Parcelable.Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel source) {
            return new Temperature(source);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };

    private Double current;
    private Double min;
    private Double max;

    public Temperature(Double current, Double min, Double max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    protected Temperature(Parcel in) {
        this.current = (Double) in.readValue(Double.class.getClassLoader());
        this.min = (Double) in.readValue(Double.class.getClassLoader());
        this.max = (Double) in.readValue(Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.current);
        dest.writeValue(this.min);
        dest.writeValue(this.max);
    }

    public Temperature(Double min, Double max) {
        this(min, max, null);
    }

    public Double getCurrent() {
        return current;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }
}
