package hr.karlovrbic.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by TheKarlo95 on 15.10.2016..
 */
public class City implements Parcelable {

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    private Integer id;
    private String name;
    private Country country;

    protected City(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.country = in.readParcelable(Country.class.getClassLoader());
    }

    public City(Integer id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public City(String name, Country country) {
        this(null, name, country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.country, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return new EqualsBuilder()
                .append(getId(), city.getId())
                .append(getName(), city.getName())
                .append(getCountry(), city.getCountry())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                .append(getCountry())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }
}
