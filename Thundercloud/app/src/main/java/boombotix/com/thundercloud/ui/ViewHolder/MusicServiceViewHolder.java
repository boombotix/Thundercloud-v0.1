package boombotix.com.thundercloud.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.view.ConnectButton;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ViewHolder for a single music service in a RecyclerView
 *
 * @author Theo Kanning
 */
public class MusicServiceViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.logo)
    ImageView logo;

    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.connect)
    ConnectButton connectButton;

    public MusicServiceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setLogo(int logoId){
        logo.setImageResource(logoId);
    }

    public void setConnectedStatus(boolean connectedStatus){
        this.connectButton.setText(connectedStatus ? R.string.connected : R.string.connect);
    }

    public void setClickListener(View.OnClickListener listener){
        connectButton.setOnClickListener(listener);
    }
}
