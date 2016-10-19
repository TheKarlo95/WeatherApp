package hr.karlovrbic.weatherapp.network;

import hr.karlovrbic.weatherapp.model.response.CurrentWeatherResponse;
import hr.karlovrbic.weatherapp.model.response.ForecastsResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by TheKarlo95 on 18.10.2016..
 */
public interface WeatherService {

    @GET("forecast/daily?")
    Observable<ForecastsResponse> getForecasts(@Query("q") String cityAndCountry,
                                               @Query("cnt") String numOfDays,
                                               @Query("APPID") String apiKey);
    @GET("weather?")
    Observable<CurrentWeatherResponse> getCurrentWeather(@Query("q") String cityAndCountry,
                                                         @Query("APPID") String apiKey);
}
