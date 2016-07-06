package boombotix.com.thundercloud.ui.base;

import android.support.v4.app.Fragment;

import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Base Fragment which should be implemented by any Fragment.
 *
 * Takes care of any housekeeping or globally necessary methods for fragments.
 *
 * Created by jsaucedo on 2/1/16.
 */
public class BaseFragment extends Fragment {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public BaseActivity getSupportActivity(){
        return (BaseActivity) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(compositeSubscription.isUnsubscribed()){
            compositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        if(!compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
        }
    }

    /**
     * Notifies any subscribed parties that the main content fragment/view
     * is loaded.
     */
    protected void alertActivityMainViewCreated() {
        ((TopLevelActivity) getSupportActivity()).alertMainViewCreated();
    }

    protected void setToolbarTitle(String title) {
        ((TopLevelActivity) getSupportActivity()).setToolbarTitle(title);
    }

    /**
     * Shows the search bar in the toolbar
     */
    protected void showSearch() {
        ((TopLevelActivity) getSupportActivity()).showSearch();
    }

    /**
     * Hides the search bar in the toolbar.
     */
    protected void hideSearch() {
        ((TopLevelActivity) getSupportActivity()).hideSearch();
    }

}
