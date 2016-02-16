package boombotix.com.thundercloud.ui.base;

import android.support.v4.app.Fragment;

import butterknife.ButterKnife;

/**
 * Base Fragment which should be implemented by any Fragment.
 *
 * Takes care of any housekeeping or globally necessary methods for fragments.
 *
 * Created by jsaucedo on 2/1/16.
 */
public class BaseFragment extends Fragment {

    public BaseActivity getSupportActivity(){
        return (BaseActivity) getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
