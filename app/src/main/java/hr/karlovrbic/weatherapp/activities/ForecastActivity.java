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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.adapter.ForecastAdapter;
import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.model.Weather;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;
import hr.karlovrbic.weatherapp.mvp.presenters.ForecastPresenter;
import hr.karlovrbic.weatherapp.utils.MessageUtils;

public class ForecastActivity extends AppCompatActivity implements IForecast.View {

    private static final String KEYWORDS_EXTRAS_KEY = "keywords";
    private static final String FORECASTS_KEY = "forecasts";

    @BindView(R.id.forecast_toolbar)
    Toolbar toolbar;
    @BindView(R.id.forecast_rv_forecasts)
    RecyclerView rvForecasts;

    private ProgressDialog progressDialog;
    private IForecast.Presenter presenter;

    private City city;
    private Weather weather;
    private ForecastAdapter adapter;

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
        setForecasts(forecasts);
    }

    @Override
    @OnClick(R.id.forecast_fab)
    public void onShowVideoClick() {
        if (city != null && weather != null) {
            presenter.showVideo(city.getName(), weather.getDescription());
        }
    }

    @Override
    public void setForecasts(List<Forecast> forecasts) {
        city = forecasts.get(0).getCity();
        if(rvForecasts != null) {
            adapter = new ForecastAdapter(this, forecasts);
            rvForecasts.setLayoutManager(new LinearLayoutManager(this));
            rvForecasts.setAdapter(adapter);
        }
    }

    @Override
    public void startVideoActivity(String keywords) {
        Bundle extras = new Bundle();
        extras.putString(KEYWORDS_EXTRAS_KEY, keywords);

        Intent intent = ForecastActivity.buildIntent(this);
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
        } else {

        }
    }
}
