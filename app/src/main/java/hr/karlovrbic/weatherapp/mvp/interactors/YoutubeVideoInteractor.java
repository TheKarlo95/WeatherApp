package hr.karlovrbic.weatherapp.mvp.interactors;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.WeatherApp;
import hr.karlovrbic.weatherapp.mvp.interfaces.IYoutubeVideo;
import hr.karlovrbic.weatherapp.network.ResponseListener;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TheKarlo95 on 19.10.2016..
 */
public class YoutubeVideoInteractor implements IYoutubeVideo.Interactor {

    private YouTube youtube;
    private Subscription subscription;

    @Inject
    public YoutubeVideoInteractor(YouTube youtube) {
        this.youtube = youtube;
    }

    @Override
    public void getVideo(String keywords, final ResponseListener<String> listener) {
        YouTube.Search.List search;
        try {
            search = youtube.search().list("id,snippet");
        } catch (IOException ioe) {
            listener.onError(WeatherApp.get().getString(R.string.youtube_video_interactor_error));
            return;
        }
        search.setKey(Keys.YOUTUBE_API);
        search.setQ(keywords);
        search.setType("video");
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        search.setMaxResults(1L);

        Observable<SearchListResponse> searchObservable = Observable.fromCallable(new SearchCall(search));

        subscription = searchObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchListResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(WeatherApp.get().getString(R.string.youtube_video_interactor_error));
                    }

                    @Override
                    public void onNext(SearchListResponse searchResponse) {
                        List<SearchResult> searchResultList = searchResponse.getItems();
                        if (searchResultList != null && !searchResultList.isEmpty()) {
                            listener.onSuccess(searchResultList.get(0).getId().getVideoId());
                        } else {
                            listener.onSuccess(null);
                        }
                    }
                });
    }

    @Override
    public void cancel() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    private static class SearchCall implements Callable<SearchListResponse> {

        private YouTube.Search.List search;

        SearchCall(YouTube.Search.List search) {
            this.search = search;
        }

        @Override
        public SearchListResponse call() throws Exception {
            return search.execute();
        }
    }
}
