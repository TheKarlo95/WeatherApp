package hr.karlovrbic.weatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Weather implements Parcelable {

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel source) {
            return new Weather(source);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    private static final String ICON_LINK = "http://openweathermap.org/img/w/%s.png";

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String iconLink;

    public Weather(int id, String main, String description, String iconLink) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.iconLink = iconLink;
    }

    protected Weather(Parcel in) {
        this.id = in.readInt();
        this.main = in.readString();
        this.description = in.readString();
        this.iconLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.main);
        dest.writeString(this.description);
        dest.writeString(this.iconLink);
    }

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIconLink() {
        if (iconLink == null) {
            return null;
        } else {
            return String.format(ICON_LINK, iconLink);
        }
    }
}
