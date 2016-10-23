package hr.karlovrbic.weatherapp.mvp.presenters;

import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.WeatherApp;
import hr.karlovrbic.weatherapp.mvp.interactors.YoutubeVideoInteractor;
import hr.karlovrbic.weatherapp.mvp.interfaces.IYoutubeVideo;
import hr.karlovrbic.weatherapp.network.ResponseListener;
import hr.karlovrbic.weatherapp.utils.Objects;

/**
 * Created by TheKarlo95 on 19.10.2016..
 */
public class YoutubeVideoPresenter implements IYoutubeVideo.Presenter {

    private IYoutubeVideo.View view;
    private IYoutubeVideo.Interactor interactor;


    public YoutubeVideoPresenter(IYoutubeVideo.View view) {
        this.view = Objects.requireNonNull(view, "Parameter view cannnot be null");
        this.interactor = new YoutubeVideoInteractor();
    }

    @Override
    public void loadVideo(String keywords) {
        view.showProgress();
        interactor.getVideo(keywords, new ResponseListener<String>() {
            @Override
            public void onSuccess(String videoId) {
                if(videoId != null) {
                    view.cueVideo(videoId);
                } else {
                    view.showMessage(WeatherApp.get().getString(R.string.youtube_video_presenter_no_videos_error));
                    view.onBackPressed();
                }
                view.hideProgress();
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
                view.hideProgress();
                view.onBackPressed();
            }
        });
    }

    @Override
    public void cancel() {
        interactor.cancel();
    }
}
