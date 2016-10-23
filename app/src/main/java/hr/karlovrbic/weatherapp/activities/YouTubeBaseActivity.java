package hr.karlovrbic.weatherapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.MenuItem;

import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.WeatherApp;
import hr.karlovrbic.weatherapp.dagger.components.AppComponent;
import hr.karlovrbic.weatherapp.mvp.interfaces.IBase;
import hr.karlovrbic.weatherapp.utils.MessageUtils;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
public abstract class YouTubeBaseActivity extends com.google.android.youtube.player.YouTubeBaseActivity
        implements IBase.View {
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(WeatherApp.get().getAppComponent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(@IdRes int message) {
        MessageUtils.showMessage(this, message);
    }

    @Override
    public void showMessage(String message) {
        MessageUtils.showMessage(this, message);
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            initProgressDialog();
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.hide();
            progressDialog = null;
        }
    }

    protected abstract void injectDependencies(AppComponent appComponent);

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.forecast_loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}
