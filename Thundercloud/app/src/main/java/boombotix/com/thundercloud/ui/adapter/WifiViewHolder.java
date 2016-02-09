package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.WifiNetwork;
import boombotix.com.thundercloud.ui.adapter.WifiListAdapter.WifiListClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ViewHolder for a single wifi network
 *
 * @author Theo Kanning
 */
public class WifiViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.wifi_network_ssid)
    TextView ssid;

    @Bind(R.id.wifi_network_strength)
    ImageView strength;

    @Bind(R.id.wifi_network_info)
    ImageView info;

    private WifiNetwork network;

    private WifiListClickListener listener;

    public WifiViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> listener.onWifiNetworkSelected(this.network));
        info.setOnClickListener(v -> listener.showWifiNetworkInfo(this.network));
    }

    public void setNetwork(WifiNetwork network) {
        this.network = network;
    }

    public void setListener(
            WifiListClickListener listener) {
        this.listener = listener;
    }

    public void setSsid(String ssid) {
        this.ssid.setText(ssid);
    }

    public void setStrength(Integer strength) {
        //todo show different icon for strength ranges
    }
}
