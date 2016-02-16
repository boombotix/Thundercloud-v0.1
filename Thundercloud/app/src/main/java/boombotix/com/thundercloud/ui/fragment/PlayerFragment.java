package boombotix.com.thundercloud.ui.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import butterknife.Bind;
import butterknife.ButterKnife;


public class PlayerFragment extends BaseFragment {

    @Bind(R.id.player_blurred_background)
    FrameLayout blurredBackround;

    @Inject
    ScreenBlurUiFilter screenBlurUiFilter;

    public PlayerFragment() {
        // Required empty public constructor
    }

    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        ButterKnife.bind(this, view);
        getSupportActivity().getActivityComponent().inject(this);

        this.blurredBackround.setBackground(new BitmapDrawable(getResources(),
                this.screenBlurUiFilter.blurView(((TopLevelActivity) getSupportActivity())
                        .getCaptureableView())));

        return view;
    }

}
