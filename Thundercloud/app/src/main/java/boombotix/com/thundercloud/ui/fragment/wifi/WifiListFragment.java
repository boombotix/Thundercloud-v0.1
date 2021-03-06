package boombotix.com.thundercloud.ui.fragment.wifi;

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canelmas.let.AskPermission;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.wifi.WifiNetwork;
import boombotix.com.thundercloud.ui.adapter.WifiListAdapter;
import boombotix.com.thundercloud.ui.base.BaseFragment;
import boombotix.com.thundercloud.ui.dialog.WifiCredentialsDialog;
import boombotix.com.thundercloud.wifi.WifiScanResultsObservableContract;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Fragment that presents the user with a list of available wifi networks. If doing a first-time
 * setup, the user can choose to skip wifi connection entirely.
 *
 * @author Theo Kanning
 */
public class WifiListFragment extends BaseFragment implements WifiListAdapter.WifiListClickListener,
        WifiCredentialsDialog.WifiCredentialsDialogListener {

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

    @Bind(R.id.wifi_list)
    RecyclerView wifiRecyclerView;

    @Bind(R.id.instructions)
    TextView instructions;

    @Inject
    WifiScanResultsObservableContract wifiScanResultsObservable;

    private WifiListAdapter adapter;

    private String speakerName;

    private boolean firstTime;

    private WifiListFragmentCallbacks listener;

    public WifiListFragment() {

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
        ButterKnife.bind(this, view);
        getActivity().setResult(R.string.wifi_list_title);

        getSupportActivity().getActivityComponent().inject(this);

        showInstructions();
        showSkipOptionIfFirstTime();
        initNetworkList();
        startSearch();
        return view;
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

    /**
     * Shows a different instruction message based on the speaker name wand whether or not this is
     * the first-time setup.
     */
    @AskPermission({
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    })
    private void showInstructions() {
        String message;
        if (firstTime) {
            message = getString(R.string.wifi_list_instructions_first_time, speakerName);
        } else {
            message = getString(R.string.wifi_list_instructions, speakerName);
        }
        instructions.setText(message);
    }

    /**
     * Initializes network recyclerview and adapter
     */
    private void initNetworkList() {
        adapter = new WifiListAdapter(new ArrayList<>(), this);
        wifiRecyclerView.setAdapter(adapter);
        wifiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Searches for available wifi networks
     */
    private void startSearch() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);

        wifiManager.startScan();
        compositeSubscription.add(wifiScanResultsObservable.getScanResults().subscribe(this::onWifiScanResults));

        showSearchView();
    }

    @DebugLog
    private void onWifiScanResults(List<WifiNetwork> scanResults){

        if(scanResults.size() == 0) return;

        adapter.clearList();

        for(WifiNetwork network : scanResults){
            adapter.addNetwork(network);
        }

        showResultsView();
    }

    private void showCredentialsDialog(WifiNetwork network) {
        WifiCredentialsDialog dialog = new WifiCredentialsDialog(network, getContext());
        dialog.setListener(this);
        dialog.show();
    }

    /**
     * Shows a progress bar and message, hides list
     */
    private void showSearchView() {
        searchContainer.setVisibility(View.VISIBLE);
        noResultsContainer.setVisibility(View.GONE);
        wifiRecyclerView.setVisibility(View.GONE);
        progressContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Shows the list of network results
     */
    @DebugLog
    private void showResultsView() {
        searchContainer.setVisibility(View.VISIBLE);
        noResultsContainer.setVisibility(View.GONE);
        wifiRecyclerView.setVisibility(View.VISIBLE);
        progressContainer.setVisibility(View.GONE);
    }

    /**
     * Shows message saying that no networks were found
     */
    private void showNoResultsView() {
        searchContainer.setVisibility(View.GONE);
        noResultsContainer.setVisibility(View.VISIBLE);
    }

    private void showSkipOptionIfFirstTime() {
        if (firstTime) {
            skipContainer.setVisibility(View.VISIBLE);
        } else {
            skipContainer.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.skip)
    public void skipWifiSetup() {
        //todo stop search
        listener.onWifiSetupSkipped();
    }


    @Override
    public void onWifiNetworkSelected(WifiNetwork network) {
        showCredentialsDialog(network);
    }

    @Override
    public void showWifiNetworkInfo(WifiNetwork network) {
        //todo show network info
    }

    @Override
    public void onCredentialsConfirmed(WifiNetwork network) {
        listener.onWifiNetworkChosen(network);
    }

    public interface WifiListFragmentCallbacks {

        //todo change once we know what data are required for joining a network, password, id etc
        void onWifiNetworkChosen(WifiNetwork network);

        void onWifiSetupSkipped();
    }
}
