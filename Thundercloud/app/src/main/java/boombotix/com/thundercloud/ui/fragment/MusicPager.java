package boombotix.com.thundercloud.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment that shows a tabbed view with the user's playlists, songs, albums, and artists
 *
 * @author Jayd Saucedo
 */
public class MusicPager extends BaseFragment {
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Bind(R.id.container)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    public MusicPager() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_pager, container, false);
        ButterKnife.bind(this, view);

        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MusicListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case MusicListFragment.PLAYLIST_SECTION:
                    return getString(R.string.playlist);
                case MusicListFragment.SONGS_SECTION:
                    return getString(R.string.songs);
                case MusicListFragment.ALBUMS_SECTION:
                    return getString(R.string.albums);
                case MusicListFragment.ARTISTS_SECTION:
                    return getString(R.string.artists);
            }
            return null;
        }
    }
}
