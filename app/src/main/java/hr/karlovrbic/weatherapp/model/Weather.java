package hr.karlovrbic.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public class Weather {

    private static final String ICON_LINK_ENDPOINT = "http://openweathermap.org/img/w/";

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
        if (iconLink != null) {
            this.iconLink = ICON_LINK_ENDPOINT + iconLink;
        }
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
        return iconLink;
    }
}
