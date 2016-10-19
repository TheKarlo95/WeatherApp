package hr.karlovrbic.weatherapp.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;
import java.util.Locale;

import rx.functions.Func1;

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

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

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

    public Double getSpeed(Unit unit) {
        return unit.getConverter().call(speed);
    }

    public String getSpeedString(Unit unit) {
        if (speed == null) {
            return null;
        } else {
            return NUMBER_FORMAT.format(getSpeed(unit)) + unit.getSign();
        }
    }

    public Double getDegrees() {
        return degrees;
    }

    public enum Unit {
        METER_PER_SECOND("m/s", new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input;
                }
            }
        }),
        MILES_PER_HOUR("mph", new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input / 0.44704;
                }
            }
        });

        public static Unit getLocaleUnit(Context context) {
            Locale current = context.getResources().getConfiguration().locale;
            String countryCode = current.getCountry().toUpperCase();

            if (countryCode.equals("US") ||
                    countryCode.equals("UK") ||
                    countryCode.equals("LR") ||
                    countryCode.equals("MM")) {
                return Unit.MILES_PER_HOUR;
            } else {
                return Unit.METER_PER_SECOND;
            }
        }

        private String sign;
        private Func1<Double, Double> converter;

        Unit(String sign, Func1<Double, Double> converter) {

            this.sign = sign;
            this.converter = converter;
        }

        public String getSign() {
            return sign;
        }

        public Func1<Double, Double> getConverter() {
            return converter;
        }
    }
}
