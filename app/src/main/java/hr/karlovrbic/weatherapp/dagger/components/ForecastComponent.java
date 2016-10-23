package hr.karlovrbic.weatherapp.dagger.components;

import dagger.Subcomponent;
import hr.karlovrbic.weatherapp.activities.ForecastActivity;
import hr.karlovrbic.weatherapp.dagger.modules.ForecastModule;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Subcomponent(modules = {ForecastModule.class})
public interface ForecastComponent {
    void inject(ForecastActivity activity);
}
