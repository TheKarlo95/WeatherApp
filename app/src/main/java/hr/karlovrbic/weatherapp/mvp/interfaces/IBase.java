package hr.karlovrbic.weatherapp.mvp.interfaces;

import android.support.annotation.IdRes;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public interface IBase {

    interface View {
        void showMessage(@IdRes int resId);

        void showMessage(String message);

        void showProgress();

        void hideProgress();

        void onBackPressed();
    }

    interface Presenter {
        void cancel();
    }

    interface Interactor {
        void cancel();
    }
}
