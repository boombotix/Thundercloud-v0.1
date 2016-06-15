package boombotix.com.thundercloud.ui.base;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import boombotix.com.thundercloud.ui.activity.TopLevelActivity;
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
     * Gets the global {@link TabLayout} that lives on the activity
     *
     * @return
     *          {@link TabLayout} in activity Toolbar
     */
    protected TabLayout getTabs() {
        return ((TopLevelActivity) getSupportActivity()).getTabs();
    }

    /**
     * Turns the global tabs visible
     */
    protected void showTabs() {
        ((TopLevelActivity) getSupportActivity()).showTabs();
    }

    /**
     * Hides the global tabs
     */
    protected void hideTabs() {
        ((TopLevelActivity) getSupportActivity()).hideTabs();
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
