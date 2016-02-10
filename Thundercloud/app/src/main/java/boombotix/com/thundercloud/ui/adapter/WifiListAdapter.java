package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.WifiNetwork;
import boombotix.com.thundercloud.ui.viewholder.WifiViewHolder;

/**
 * Adapter for a list of wifi networks. Each row contains the name, signal strength, and info
 * button. Pressing the name triggers {@link WifiListClickListener#onWifiNetworkSelected(WifiNetwork)},
 * and pressing the info button triggers {@link WifiListClickListener#showWifiNetworkInfo(WifiNetwork)}
 *
 * @author Theo Kanning
 */
public class WifiListAdapter extends RecyclerView.Adapter<WifiViewHolder> {

    private List<WifiNetwork> networks;

    private WifiListClickListener listener;

    public WifiListAdapter(List<WifiNetwork> networks, WifiListClickListener listener) {
        this.networks = networks;
        this.listener = listener;
    }

    public void addNetwork(WifiNetwork network){
        networks.add(network);
        notifyItemInserted(networks.size() - 1);
    }

    @Override
    public WifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_wifi_network, parent, false);
        return new WifiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WifiViewHolder holder, int position) {
        WifiNetwork network = networks.get(position);
        holder.setNetwork(network);
        holder.setListener(listener);
        holder.setSsid(network.getSsid());
        holder.setStrength(network.getStrength());
    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    public interface WifiListClickListener {

        void onWifiNetworkSelected(WifiNetwork network);

        void showWifiNetworkInfo(WifiNetwork network);
    }
}
