package hr.karlovrbic.weatherapp.dagger.modules;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.weatherapp.mvp.interactors.YoutubeVideoInteractor;
import hr.karlovrbic.weatherapp.mvp.interfaces.IYoutubeVideo;
import hr.karlovrbic.weatherapp.mvp.presenters.YoutubeVideoPresenter;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class YouTubeVideoModule {

    private IYoutubeVideo.View view;

    public YouTubeVideoModule(IYoutubeVideo.View view) {
        this.view = view;
    }

    @Provides
    public IYoutubeVideo.View provideView() {
        return view;
    }

    @Provides
    public IYoutubeVideo.Presenter providePresenter(YoutubeVideoPresenter presenter) {
        return presenter;
    }

    @Provides
    public IYoutubeVideo.Interactor provideInteractor(YoutubeVideoInteractor interactor) {
        return interactor;
    }
}
