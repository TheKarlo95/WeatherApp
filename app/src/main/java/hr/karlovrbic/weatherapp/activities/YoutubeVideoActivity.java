package hr.karlovrbic.weatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.network.ApiManager;

public class YoutubeVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private static final String KEYWORDS_KEY = "keywords";

    @BindView(R.id.youtube_video_ytpv_player)
    YouTubePlayerView youTubeView;

    private String keywords;

    public static Intent buildIntent(Context context) {
        return new Intent(context, YoutubeVideoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    keywords = extras.getParcelable(ForecastActivity.KEYWORDS_EXTRAS_KEY);
                }
            }
        } else {
            keywords = savedInstanceState.getString(KEYWORDS_KEY);
        }

        youTubeView.initialize(Keys.YOUTUBE_API, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(KEYWORDS_KEY, keywords);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            try {
                searchVideo(keywords, player);
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = getString(R.string.youtube_video_player_error, errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            youTubeView.initialize(Keys.YOUTUBE_API, this);
        }
    }

    private void searchVideo(String keywords, YouTubePlayer player) throws IOException {
        YouTube youtube = new YouTube.Builder(ApiManager.HTTP_TRANSPORT,
                ApiManager.JSON_FACTORY,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("weather-app").build();
        YouTube.Search.List search = youtube.search().list("id,snippet");

        search.setKey(Keys.YOUTUBE_API);
        search.setQ(keywords);
        search.setType("video");

        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        search.setMaxResults(1L);

        // Call the API and print results.
        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();

        if (!searchResultList.isEmpty()) {
            SearchResult searchResult = searchResultList.get(0);
            player.cueVideo(searchResult.getId().getVideoId());
        }
    }
}
