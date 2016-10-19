package hr.karlovrbic.weatherapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
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

public class ForecastActivity extends AppCompatActivity implements IForecast.View {

    public static final String KEYWORDS_EXTRAS_KEY = "keywords";
    private static final String FORECASTS_KEY = "forecasts";

    @BindView(R.id.forecast_toolbar)
    Toolbar toolbar;
    @BindView(R.id.forecast_rv_forecasts)
    RecyclerView rvForecasts;
    @BindView(R.id.forecast_tv_city)
    TextView tvCity;

    private ProgressDialog progressDialog;
    private IForecast.Presenter presenter;

    private City city;
    private ForecastAdapter adapter;

    private Forecast currentWeather;// for today
    private List<Forecast> forecasts;// for day 2 and 3

    public static Intent buildIntent(Context context) {
        return new Intent(context, ForecastActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ButterKnife.bind(this);

        initUI();
        loadCityAndForecast(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FORECASTS_KEY, adapter.getForecasts());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Forecast> forecasts = savedInstanceState.getParcelableArrayList(FORECASTS_KEY);
        if (forecasts != null) {
            city = forecasts.get(0).getCity();
            tvCity.setText(city.toString());
            loadRecyclerView(forecasts);
        }
    }

    @Override
    @OnClick(R.id.forecast_fab)
    public void onShowVideoClick() {
        if (city != null && currentWeather != null) {
            presenter.showVideo(city.getName(), currentWeather.getWeather().getDescription());
        }
    }

    @Override
    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
        setForecasts();
    }

    @Override
    public void setCurrentWeather(Forecast currentWeather) {
        city = currentWeather.getCity();
        tvCity.setText(city.toString());

        this.currentWeather = currentWeather;
        setForecasts();
    }

    private void setForecasts() {
        if (currentWeather != null && forecasts != null && rvForecasts != null) {
            currentWeather.getTemperature().setMax(forecasts.get(0).getTemperature().getMax());
            currentWeather.getTemperature().setMin(forecasts.get(0).getTemperature().getMin());
            List<Forecast> allForecasts = new ArrayList<>();
            allForecasts.add(currentWeather);
            allForecasts.addAll(forecasts.subList(1, forecasts.size()));

            loadRecyclerView(forecasts);
        }
    }

    private void loadRecyclerView(List<Forecast> forecasts) {
        if (rvForecasts != null) {
            adapter = new ForecastAdapter(this, forecasts);
            rvForecasts.setLayoutManager(new LinearLayoutManager(this));
            rvForecasts.setAdapter(adapter);
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
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(@IdRes int resId) {
        MessageUtils.showMessage(this, resId);
    }

    @Override
    public void showMessage(String message) {
        MessageUtils.showMessage(this, message);
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.forecast_loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    private void loadCityAndForecast(Bundle savedInstanceState) {
        presenter = new ForecastPresenter(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    city = extras.getParcelable(SearchActivity.CITY_EXTRAS_KEY);
                }
            }

            presenter.getForecast(city);
        }
    }
}
