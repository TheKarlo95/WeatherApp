package hr.karlovrbic.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.mvp.presenters.ForecastPresenter;
import hr.karlovrbic.weatherapp.recyclerview.adapter.ForecastAdapter;
import hr.karlovrbic.weatherapp.utils.MessageUtils;

public class ForecastActivity extends BaseActivity implements IForecast.View {

    public static final String KEYWORDS_EXTRAS_KEY = "keywords";
    private static final String FORECASTS_KEY = "forecasts";

    @BindView(R.id.forecast_toolbar)
    Toolbar toolbar;
    @BindView(R.id.forecast_rv_forecasts)
    RecyclerView rvForecasts;
    @BindView(R.id.forecast_tv_city)
    TextView tvCity;

    private IForecast.Presenter presenter;

    private ForecastAdapter adapter;

    public static Intent buildIntent(Context context) {
        return new Intent(context, ForecastActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ButterKnife.bind(this);

        setToolbar(toolbar, true);
        presenter = new ForecastPresenter(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    City city = extras.getParcelable(SearchActivity.CITY_EXTRAS_KEY);
                    presenter.getForecast(city);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FORECASTS_KEY, adapter.getForecasts());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Forecast> forecasts = savedInstanceState.getParcelableArrayList(FORECASTS_KEY);
        if (forecasts != null) {
            setForecasts(forecasts);
            setCity(forecasts.get(0).getCity());
        }
    }

    @Override
    public void setForecasts(List<Forecast> forecasts) {
        if (rvForecasts != null) {
            adapter = new ForecastAdapter(this, forecasts);
            rvForecasts.setLayoutManager(new LinearLayoutManager(this));
            rvForecasts.setAdapter(adapter);
        }
    }

    @Override
    public void setCity(City city) {
        if (city != null) {
            tvCity.setText(city.toString());
        }
    }

    @Override
    @OnClick(R.id.forecast_fab)
    public void onShowVideoClick() {
        List<Forecast> forecasts = adapter.getForecasts();
        if (forecasts != null && !forecasts.isEmpty()) {
            Forecast currentWeather = forecasts.get(0);
            presenter.showVideo(currentWeather.getCity().getName(), currentWeather.getWeather().getDescription());
        }
    }

    @Override
    public void startVideoActivity(String keywords) {
        Bundle extras = new Bundle();
        extras.putString(KEYWORDS_EXTRAS_KEY, keywords);

        Intent intent = YoutubeVideoActivity.buildIntent(this);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void showMessage(@IdRes int resId) {
        MessageUtils.showMessage(this, resId);
    }

    @Override
    public void showMessage(String message) {
        MessageUtils.showMessage(this, message);
    }
}
