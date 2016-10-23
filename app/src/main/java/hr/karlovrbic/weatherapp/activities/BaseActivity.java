package hr.karlovrbic.weatherapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.mvp.interfaces.IBase;
import hr.karlovrbic.weatherapp.utils.MessageUtils;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */

public class BaseActivity extends AppCompatActivity implements IBase.View {
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    protected void setToolbar(Toolbar toolbar, boolean showHomeAsUp) {
        setSupportActionBar(toolbar);
        if (showHomeAsUp) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.forecast_loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}
