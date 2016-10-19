package hr.karlovrbic.weatherapp.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.Temperature;
import hr.karlovrbic.weatherapp.model.Wind;
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateInstance();
        private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

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
        @BindView(R.id.weather_box_tv_temperature_current_label)
        TextView tvTempCurrentLabel;

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
                String iconLink = forecast.getWeather().getIconLink();
                Date date = forecast.getDate();
                String weatherMain = forecast.getWeather().getMain();
                String weatherDescription = forecast.getWeather().getDescription();
                Temperature.Unit tempUnit = Temperature.Unit.getLocaleUnit(context);
                String tempMax = forecast.getTemperature().getMaxString(tempUnit);
                String tempMin = forecast.getTemperature().getMinString(tempUnit);
                String tempCur = forecast.getTemperature().getCurrentString(tempUnit);
                Wind.Unit speedUnit = Wind.Unit.getLocaleUnit(context);
                String windSpeed = forecast.getWind().getSpeedString(speedUnit);
                String windDegrees = forecast.getWind().getDegreesString();
                String pressure = forecast.getPressureString();
                String humidity = forecast.getHumidityString();

                if (iconLink != null) {
                    Glide.with(context)
                            .load(iconLink)
                            .crossFade()
                            .error(R.drawable.weather_icon_error)
                            .placeholder(R.drawable.weather_icon_placeholder)
                            .into(ivIcon);
                } else {
                    Glide.clear(ivIcon);
                    ivIcon.setImageDrawable(null);
                }
                if (date != null) {
                    tvDate.setText(DATE_FORMAT.format(date));
                }
                if (weatherMain != null) {
                    tvWeatherMain.setText(weatherMain);
                }
                if (weatherDescription != null) {
                    tvWeatherDescription.setText(weatherDescription);
                }
                if (tempMax != null) {
                    tvTempMax.setText(tempMax);
                }
                if (tempMin != null) {
                    tvTempMin.setText(tempMin);
                }
                if (tempCur != null) {
                    tvTempCurrentLabel.setVisibility(View.VISIBLE);
                    tvTempCurrent.setVisibility(View.VISIBLE);
                    tvTempCurrent.setText(tempCur);
                } else {
                    tvTempCurrentLabel.setVisibility(View.GONE);
                    tvTempCurrent.setVisibility(View.GONE);
                }
                if (windSpeed != null) {
                    tvWindSpeed.setText(windSpeed);
                }
                if (windDegrees != null) {
                    tvWindDegrees.setText(windDegrees);
                }
                if (pressure != null) {
                    tvPressure.setText(pressure);
                }
                if (humidity != null) {
                    tvHumidity.setText(humidity);
                }
            }
        }
    }
}
