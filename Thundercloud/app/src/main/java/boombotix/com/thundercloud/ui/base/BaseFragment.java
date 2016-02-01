package boombotix.com.thundercloud.ui.base;

import android.support.v4.app.Fragment;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class BaseFragment extends Fragment {
    public BaseActivity getSupportActivity(){
        return (BaseActivity) getActivity();
    }
}
