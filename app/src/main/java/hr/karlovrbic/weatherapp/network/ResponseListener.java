package hr.karlovrbic.weatherapp.network;

public interface ResponseListener<T> {

    void onSuccess(T result);

    void onError(String message);
}