package hr.karlovrbic.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.mvp.interfaces.IForecast;

public class CurrentWeatherActivity extends AppCompatActivity {

    @BindView(R.id.forecast_toolbar)
    Toolbar toolbar;
    @BindView(R.id.current_weather_frag_youtube_player)
    YouTubePlayerSupportFragment youTubePlayerFragment;

    private IForecast.Presenter presenter;

    public static Intent buildIntent(Context context) {
        return new Intent(context, CurrentWeatherActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
