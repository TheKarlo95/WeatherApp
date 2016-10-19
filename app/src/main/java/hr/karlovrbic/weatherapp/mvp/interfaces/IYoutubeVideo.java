package hr.karlovrbic.weatherapp.mvp.interfaces;

import android.support.annotation.IdRes;

import hr.karlovrbic.weatherapp.network.ResponseListener;

/**
 * Created by TheKarlo95 on 19.10.2016..
 */

public interface IYoutubeVideo {

    interface View extends IBase.View {
        void cueVideo(String videoId);

        void showMessage(@IdRes int resId);

        void showMessage(String message);

        void showProgress();

        void hideProgress();
    }

    interface Presenter extends IBase.Presenter {
        void loadVideo(String keywords);
    }

    interface Interactor extends IBase.Interactor {
        void getVideo(String keywords, final ResponseListener<String> listener);
    }
}
