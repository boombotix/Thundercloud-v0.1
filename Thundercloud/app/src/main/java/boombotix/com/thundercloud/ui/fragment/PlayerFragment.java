package boombotix.com.thundercloud.ui.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.filter.ScreenBlurUiFilter;
import boombotix.com.thundercloud.ui.view.CropImageView;
import butterknife.Bind;
import butterknife.ButterKnife;


public class PlayerFragment extends BaseFragment {

    @Bind(R.id.player_blurred_background)
    CropImageView blurredBackround;

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

        setupBlurredBackground();

        return view;
    }

    private void setupBlurredBackground() {
        View toBlur = ((TopLevelActivity) getSupportActivity())
                .getCaptureableView();
        if (toBlur != null) {

            this.blurredBackround.setImageDrawable(new BitmapDrawable(getResources(),
                    this.screenBlurUiFilter.blurView(toBlur)));
            this.blurredBackround.setColorFilter(ContextCompat.getColor(getActivity(), R.color.playerBarTransparent),
                    PorterDuff.Mode.DARKEN);
            this.blurredBackround.setOffset(0, 1);
        }
    }

}
