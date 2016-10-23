package hr.karlovrbic.weatherapp.dagger.components;

import dagger.Subcomponent;
import hr.karlovrbic.weatherapp.activities.YoutubeVideoActivity;
import hr.karlovrbic.weatherapp.dagger.modules.YouTubeVideoModule;

/**
 * Created by TheKarlo95 on 23.10.2016..
 */
@Subcomponent(modules = {YouTubeVideoModule.class})
public interface YoutubeVideoComponent {
    void inject(YoutubeVideoActivity activity);
}
