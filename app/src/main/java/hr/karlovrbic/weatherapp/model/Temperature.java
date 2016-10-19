package hr.karlovrbic.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import rx.functions.Func1;

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

    public Double getCurrent(Unit unit) {
        return unit.getConverter().call(current);
    }

    public Double getCurrent() {
        return getCurrent(Unit.CELSIUS);
    }

    public Double getMin(Unit unit) {
        return unit.getConverter().call(min);
    }

    public Double getMin() {
        return getMin(Unit.CELSIUS);
    }

    public Double getMax(Unit unit) {
        return unit.getConverter().call(max);
    }

    public Double getMax() {
        return getMax(Unit.CELSIUS);
    }

    public enum Unit {
        KELVIN(new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                return input;
            }
        }),
        CELSIUS(new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input - 272.15;
                }
            }
        }),
        FAHRENHEIT(new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input * 9.0 / 5.0 - 459.67;
                }
            }
        });

        private Func1<Double, Double> converter;

        Unit(Func1 function) {
            this.converter = function;
        }

        public Func1<Double, Double> getConverter() {
            return converter;
        }
    }
}
