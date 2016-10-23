package hr.karlovrbic.weatherapp.dagger.components;

import dagger.Subcomponent;
import hr.karlovrbic.weatherapp.activities.SearchActivity;
import hr.karlovrbic.weatherapp.dagger.modules.SearchModule;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {
    void inject(SearchActivity activity);
}
