package hr.karlovrbic.weatherapp.mvp.interfaces;

import java.util.List;

import hr.karlovrbic.weatherapp.model.City;
import hr.karlovrbic.weatherapp.model.Forecast;
import hr.karlovrbic.weatherapp.network.ResponseListener;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public interface IForecast {

    interface View extends IBase.View {
        void setForecasts(List<Forecast> forecasts);

        void setCity(City city);

        void onShowVideoClick();

        void startVideoActivity(String keywords);
    }

    interface Presenter extends IBase.Presenter {
        void showVideo(String cityName, String weatherDescription);

        void getForecast(City city);
    }

    interface ForecastInteractor extends IBase.Interactor {
        void getForecast(String city, String country, final ResponseListener<List<Forecast>> listener);
    }

    interface CurrentWeatherInteractor extends IBase.Interactor {
        void getCurrentWeather(String city, String country, final ResponseListener<Forecast> listener);
    }
}
