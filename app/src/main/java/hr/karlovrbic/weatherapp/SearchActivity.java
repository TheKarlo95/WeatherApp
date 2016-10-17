package hr.karlovrbic.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.mvp.interfaces.ISearch;
import hr.karlovrbic.weatherapp.mvp.presenters.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements ISearch.View {

    private static final String CITY_EXTRAS_KEY = "city";

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_fab)
    FloatingActionButton fab;
    @BindView(R.id.search_til_city)
    TextInputLayout tilCity;
    @BindView(R.id.search_til_country)
    TextInputLayout tilCountry;
    @BindView(R.id.search_et_city)
    EditText etCity;
    @BindView(R.id.search_et_country)
    EditText etCountry;

    private ISearch.Presenter presenter;

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        presenter = new SearchPresenter(this);

        presenter.init(savedInstanceState);

        etCity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onSearchClick();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    @OnClick(R.id.search_fab)
    public void onSearchClick() {
        String cityName = etCity.getText().toString();
        String countryName = etCountry.getText().toString();
        presenter.getForecast(cityName, countryName);
    }

    @Override
    public void displayCityInputError() {
        tilCity.setError(getString(R.string.search_error_message_city));
        requestFocus(etCity);
    }

    @Override
    public void setCityInputText(String city) {
        etCity.setText(city);
    }

    @Override
    public void setCountryInputText(String country) {
        etCountry.setText(country);
    }

    @Override
    public void startForecastActivity(City city) {
        Bundle extras = new Bundle();
        extras.putParcelable(CITY_EXTRAS_KEY, city);

        Intent intent = ForecastActivity.buildIntent(this);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
