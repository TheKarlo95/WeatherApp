package hr.karlovrbic.weatherapp.mvp.interactors;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.mvp.interfaces.IYoutubeVideo;
import hr.karlovrbic.weatherapp.network.ApiManager;
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

    private Subscription subscription;

    @Override
    public void getVideo(String keywords, final ResponseListener<String> listener) {
        YouTube youtube = new YouTube.Builder(ApiManager.HTTP_TRANSPORT,
                ApiManager.JSON_FACTORY,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("weather-app").build();

        YouTube.Search.List search = null;
        try {
            search = youtube.search().list("id,snippet");
        } catch (IOException ioe) {
            listener.onError(ioe.getMessage());
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
                        listener.onError(e.getMessage());
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

        public SearchCall(YouTube.Search.List search) {
            this.search = search;
        }

        @Override
        public SearchListResponse call() throws Exception {
            return search.execute();
        }
    }
}
