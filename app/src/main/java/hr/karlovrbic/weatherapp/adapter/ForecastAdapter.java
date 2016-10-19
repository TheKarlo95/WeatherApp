package hr.karlovrbic.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.utils.Objects;

/**
 * Created by TheKarlo95 on 18.10.2016..
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Forecast> forecasts;
    private LayoutInflater inflater;

    public ForecastAdapter(Context context, Collection<Forecast> forecasts) {
        this.context = Objects.requireNonNull(context, "Parameter context cannot be null");
        this.inflater = LayoutInflater.from(context);
        if (forecasts == null || forecasts.isEmpty()) {
            this.forecasts = null;
        } else {
            this.forecasts = new ArrayList<>(forecasts);
        }
    }

    public ForecastAdapter(Context context) {
        this(context, null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.weather_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        holder.setForecast(context, forecast);
    }

    @Override
    public int getItemCount() {
        if (forecasts == null) {
            return 0;
        } else {
            return forecasts.size();
        }
    }

    public ArrayList<Forecast> getForecasts() {
        return forecasts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance();

        @BindView(R.id.weather_box_tv_date)
        TextView tvDate;
        @BindView(R.id.weather_box_iv_icon)
        ImageView ivIcon;
        @BindView(R.id.weather_box_tv_weather_main)
        TextView tvWeatherMain;
        @BindView(R.id.weather_box_tv_weather_description)
        TextView tvWeatherDescription;

        @BindView(R.id.weather_box_tv_temperature_max_value)
        TextView tvTempMax;
        @BindView(R.id.weather_box_tv_temperature_min_value)
        TextView tvTempMin;
        @BindView(R.id.weather_box_tv_temperature_current_value)
        TextView tvTempCurrent;

        @BindView(R.id.weather_box_tv_wind_speed_value)
        TextView tvWindSpeed;
        @BindView(R.id.weather_box_tv_wind_degrees_value)
        TextView tvWindDegrees;

        @BindView(R.id.weather_box_tv_pressure_value)
        TextView tvPressure;
        @BindView(R.id.weather_box_tv_humidity_value)
        TextView tvHumidity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setForecast(Context context, Forecast forecast) {
            if (forecast != null) {
                Glide.with(context)
                        .load(forecast.getWeather().getIconLink())
                        .dontAnimate()
                        .into(ivIcon);
                tvDate.setText(DATE_FORMAT.format(forecast.getDate()));
                tvWeatherMain.setText(forecast.getWeather().getMain());
                tvWeatherDescription.setText(forecast.getWeather().getDescription());
                tvTempMax.setText(String.valueOf(forecast.getTemperature().getMax()));
                tvTempMin.setText(String.valueOf(forecast.getTemperature().getMin()));
                tvTempCurrent.setText(String.valueOf(forecast.getTemperature().getCurrent()));
                tvWindSpeed.setText(String.valueOf(forecast.getWind().getSpeed()));
                tvWindDegrees.setText(String.valueOf(forecast.getWind().getDegrees()));
                tvPressure.setText(String.valueOf(forecast.getPressure()));
                tvHumidity.setText(String.valueOf(forecast.getHumidity()));
            }
        }

    }
}
