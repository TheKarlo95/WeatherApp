package hr.karlovrbic.weatherapp.mvp.interfaces;

/**
 * Created by TheKarlo95 on 17.10.2016..
 */
public interface IBase {

    interface View {
    }

    interface Presenter {
    }

    interface Interactor {
        void cancel();

        void reset();
    }
}
