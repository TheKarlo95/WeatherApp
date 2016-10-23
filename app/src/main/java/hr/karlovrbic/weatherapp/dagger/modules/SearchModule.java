package hr.karlovrbic.weatherapp.dagger.modules;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.weatherapp.mvp.interfaces.ISearch;
import hr.karlovrbic.weatherapp.mvp.presenters.SearchPresenter;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Module
public class SearchModule {

    private ISearch.View view;

    public SearchModule(ISearch.View view) {
        this.view = view;
    }

    @Provides
    public ISearch.View provideView() {
        return view;
    }

    @Provides
    public ISearch.Presenter providePresenter(SearchPresenter presenter) {
        return presenter;
    }
}