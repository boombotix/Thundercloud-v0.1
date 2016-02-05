package boombotix.com.thundercloud.ui.fragment.wifi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that presents the user with a list of available wifi networks. If doing a first-time
 * setup, the user can choose to skip wifi connection entirely.
 *
 * @author Theo Kanning
 */
public class WifiListFragment extends Fragment {

    private static final String ARG_SPEAKER_NAME = "speakerName";

    private static final String ARG_FIRST_TIME = "firstTime";

    @Bind(R.id.no_results_container)
    View noResultsContainer;

    @Bind(R.id.search_container)
    View searchContainer;

    @Bind(R.id.progress_container)
    View progressContainer;

    @Bind(R.id.skip_container)
    View skipContainer;

    @Bind(R.id.network_list)
    RecyclerView networkList;

    @Bind(R.id.instructions)
    TextView instructions;

    private String speakerName;

    private boolean firstTime;

    private WifiListFragmentCallbacks listener;

    public WifiListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param speakerName the name of the connected speaker, used for display purposes
     * @param firstTime   whether or not this is part of first-time setup
     * @return A new instance of fragment WifiListFragment.
     */
    public static WifiListFragment newInstance(String speakerName, boolean firstTime) {
        WifiListFragment fragment = new WifiListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPEAKER_NAME, speakerName);
        args.putBoolean(ARG_FIRST_TIME, firstTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            speakerName = getArguments().getString(ARG_SPEAKER_NAME);
            firstTime = getArguments().getBoolean(ARG_FIRST_TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi_list, container, false);
        ButterKnife.bind(this,view);
        getActivity().setResult(R.string.wifi_list_title);

        initNetworkList();
        startSearch();
        return view;
    }

    /**
     * Initializes network recyclerview and data
     */
    private void initNetworkList(){
        //todo adapter
        //todo handle clicks and prompt for password
    }

    /**
     * Searches for available wifi networks
     */
    private void startSearch(){
        showSearchView();
        //todo start search
    }

    /**
     * Shows a progress bar and message, hides list
     */
    private void showSearchView(){
        searchContainer.setVisibility(View.VISIBLE);
        noResultsContainer.setVisibility(View.GONE);
        networkList.setVisibility(View.GONE);
        progressContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the list of network results
     */
    private void showResultsView(){
        searchContainer.setVisibility(View.VISIBLE);
        noResultsContainer.setVisibility(View.GONE);
        networkList.setVisibility(View.VISIBLE);
        progressContainer.setVisibility(View.GONE);
    }

    /**
     * Shows message saying that no networks were found
     */
    private void showNoResultsView(){
        searchContainer.setVisibility(View.GONE);
        noResultsContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Hide skip text and reminder unless this is first-time setup
     */
    private void initSkipping(){
        if(firstTime){
            skipContainer.setVisibility(View.VISIBLE);
        } else {
            skipContainer.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.skip)
    public void skipWifiSetup(){
        //todo stop search
        listener.onWifiSetupSkipped();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WifiListFragmentCallbacks) {
            listener = (WifiListFragmentCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement WifiListFragmentCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface WifiListFragmentCallbacks {

        //todo change once we know what data are required for joining a network, password, id etc
        void onWifiNetworkChosen(String networkName);

        void onWifiSetupSkipped();
    }
}
