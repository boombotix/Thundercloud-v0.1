package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.WifiNetwork;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for a list of wifi networks. Each row contains the name, signal strength, and info
 * button. Pressing the name triggers {@link WifiListClickListener#onWifiNetworkSelected(WifiNetwork)},
 * and pressing the info button triggers {@link WifiListClickListener#showWifiNetworkInfo(WifiNetwork)}
 *
 * @author Theo Kanning
 */
public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.WifiViewHolder> {

    private List<WifiNetwork> networks;

    private WifiListClickListener listener;

    public WifiListAdapter(List<WifiNetwork> networks, WifiListClickListener listener) {
        this.networks = networks;
        this.listener = listener;
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
        holder.network = network;
        holder.listener = listener;
        holder.ssid.setText(network.getSsid());
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

    public static class WifiViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.wifi_network_ssid)
        public TextView ssid;

        @Bind(R.id.wifi_network_strength)
        public ImageView strength;

        @Bind(R.id.wifi_network_info)
        public ImageView info;

        private WifiNetwork network;

        private WifiListClickListener listener;

        public WifiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> listener.onWifiNetworkSelected(this.network));
            info.setOnClickListener(v -> listener.showWifiNetworkInfo(this.network));
        }

        public void setStrength(Integer strength) {
            //todo show different icon for strength ranges
        }
    }
}
