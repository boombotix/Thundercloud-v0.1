package boombotix.com.thundercloud.dependencyinjection.component;

import boombotix.com.thundercloud.dependencyinjection.module.ActivityModule;
import boombotix.com.thundercloud.houndify.response.HoundifySubscriber;
import boombotix.com.thundercloud.ui.activity.LoginActivity;
import boombotix.com.thundercloud.ui.activity.MusicServiceSetupActivity;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseActivity;
import boombotix.com.thundercloud.ui.fragment.ArtistDetailsFragment;
import boombotix.com.thundercloud.ui.fragment.MusicListFragment;
import boombotix.com.thundercloud.ui.fragment.MusicPagerFragment;
import boombotix.com.thundercloud.ui.fragment.NowPlayingFragment;
import boombotix.com.thundercloud.ui.fragment.PlayerFragment;
import boombotix.com.thundercloud.ui.fragment.VoiceSearchResultFragment;
import dagger.Component;

/**
 * Activity level injection class (Component). The lifetime of this component
 * is the lifetime of a single activity, and any non-global modules should
 * be defined as part of this module.
 *
 * For Modules/injections that need to live globally see {@link ApplicationComponent}
 *
 * Created by kenton on 1/24/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    final class Initializer {
        public static ActivityComponent init(BaseActivity activity) {
            return DaggerActivityComponent.builder()
                    .applicationComponent(activity.getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .build();
        }

        private Initializer() {
            // Block instantiation
        }
    }

    void inject(TopLevelActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(MusicListFragment musicListFragment);
    void inject(MusicPagerFragment musicPagerFragment);
    void inject(PlayerFragment playerFragment);
    void inject(ArtistDetailsFragment artistDetailsFragment);
    void inject(VoiceSearchResultFragment voiceSearchResultFragment);
    void inject(HoundifySubscriber houndifySubscriber);
    void inject(NowPlayingFragment nowPlayingFragment);
    void inject(MusicServiceSetupActivity musicServiceSetupActivity);
}
