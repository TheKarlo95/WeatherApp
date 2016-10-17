package hr.karlovrbic.weatherapp.mvp.interfaces;

import java.util.List;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Country;
import hr.karlovrbic.weatherapp.network.ResponseListener;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public interface IForecast {

    interface View extends IBase.View {
        void showMessage(String message);

        void showProgress();

        void hideProgress();

        void onSearchClick();
    }

    interface Presenter extends IBase.Presenter {
        void cancel();

        void getForecast(City city, Country country);
    }

    interface ForecastInteractor extends IBase.Interactor {
        void getForecast(ResponseListener<List<City>> listener);
    }
}
