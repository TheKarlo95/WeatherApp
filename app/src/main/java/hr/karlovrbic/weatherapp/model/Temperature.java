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

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    private Double current;
    private Double min;
    private Double max;

    public Temperature(Double current, Double min, Double max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Temperature(Double min, Double max) {
        this(null, min, max);
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

    public void setCurrent(Double current) {
        this.current = current;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getCurrent(Unit unit) {
        return unit.getConverter().call(current);
    }

    public Double getCurrent() {
        return getCurrent(Unit.KELVIN);
    }

    public Double getMin(Unit unit) {
        return unit.getConverter().call(min);
    }

    public Double getMin() {
        return getMin(Unit.KELVIN);
    }

    public Double getMax(Unit unit) {
        return unit.getConverter().call(max);
    }

    public Double getMax() {
        return getMax(Unit.KELVIN);
    }

    public String getCurrentString(Unit unit) {
        if (current == null) {
            return null;
        } else {
            return NUMBER_FORMAT.format(getCurrent(unit)) + unit.getSign();
        }
    }

    public String getMaxString(Unit unit) {
        if (max == null) {
            return null;
        } else {
            return NUMBER_FORMAT.format(getMax(unit)) + unit.getSign();
        }
    }

    public String getMinString(Unit unit) {
        if (min == null) {
            return null;
        } else {
            return NUMBER_FORMAT.format(getMin(unit)) + unit.getSign();
        }
    }

    public enum Unit {
        KELVIN("K", new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input;
                }
            }
        }),
        CELSIUS("\u2103", new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input - 272.15;
                }
            }
        }),
        FAHRENHEIT("\u2109", new Func1<Double, Double>() {
            @Override
            public Double call(Double input) {
                if (input == null) {
                    return null;
                } else {
                    return input * 9.0 / 5.0 - 459.67;
                }
            }
        });

        public static Temperature.Unit getLocaleUnit(Context context) {
            Locale current = context.getResources().getConfiguration().locale;
            String countryCode = current.getCountry().toUpperCase();

            if (countryCode.equals("US") ||
                    countryCode.equals("BS") ||
                    countryCode.equals("BZ") ||
                    countryCode.equals("KY")) {
                return Temperature.Unit.FAHRENHEIT;
            } else {
                return Temperature.Unit.CELSIUS;
            }
        }

        private String sign;
        private Func1<Double, Double> converter;

        Unit(String sign, Func1 function) {
            this.sign = sign;
            this.converter = function;
        }

        public String getSign() {
            return sign;
        }

        public Func1<Double, Double> getConverter() {
            return converter;
        }
    }
}
