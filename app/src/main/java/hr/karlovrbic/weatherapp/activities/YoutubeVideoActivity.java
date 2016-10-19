package hr.karlovrbic.weatherapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.karlovrbic.weatherapp.Keys;
import hr.karlovrbic.weatherapp.R;
import hr.karlovrbic.weatherapp.mvp.interfaces.IYoutubeVideo;
import hr.karlovrbic.weatherapp.mvp.presenters.YoutubeVideoPresenter;
import hr.karlovrbic.weatherapp.utils.MessageUtils;

public class YoutubeVideoActivity extends YouTubeBaseActivity
        implements IYoutubeVideo.View,
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private static final String KEYWORDS_KEY = "keywords";
    private static final String VIDEO_ID_KEY = "video_id";

    @BindView(R.id.youtube_video_ytpv_player)
    YouTubePlayerView youTubeView;

    private ProgressDialog progressDialog;
    private IYoutubeVideo.Presenter presenter;

    private String keywords;
    private String videoId;

    public static Intent buildIntent(Context context) {
        return new Intent(context, YoutubeVideoActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        ButterKnife.bind(this);

        initProgressDialog();

        presenter = new YoutubeVideoPresenter(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    keywords = extras.getString(ForecastActivity.KEYWORDS_EXTRAS_KEY);
                }
            }
            presenter.loadVideo(keywords);
        } else {
            keywords = savedInstanceState.getString(KEYWORDS_KEY);
            videoId = savedInstanceState.getString(VIDEO_ID_KEY);
            cueVideo(videoId);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(KEYWORDS_KEY, keywords);
        bundle.putString(VIDEO_ID_KEY, videoId);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            if (videoId != null) {
                player.cueVideo(videoId);
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

    @Override
    public void cueVideo(String videoId) {
        this.videoId = videoId;
        youTubeView.initialize(Keys.YOUTUBE_API, this);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(@IdRes int resId) {
        MessageUtils.showMessage(this, resId);
    }

    @Override
    public void showMessage(String message) {
        MessageUtils.showMessage(this, message);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.forecast_loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}
