package boombotix.com.thundercloud.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.model.MusicService;
import boombotix.com.thundercloud.ui.viewholder.MusicServiceViewHolder;

/**
 * RecyclerView adapter for a list of music services, uses
 *
 * @author Theo Kanning
 */
public class MusicServiceListAdapter extends RecyclerView.Adapter<MusicServiceViewHolder>{

    public interface MusicServiceListListener{
        void onMusicServiceSelected(MusicService service);
    }

    private MusicService[] services = MusicService.values();

    private MusicServiceListListener listener;

    public MusicServiceListAdapter(MusicServiceListListener listener){
        this.listener = listener;
    }

    @Override
    public MusicServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_music_service, parent, false);
        return new MusicServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicServiceViewHolder holder, int position) {
        MusicService service = services[position];
        holder.setLogo(service.getLogoId());
        holder.setName(service.getName());
        holder.setClickListener(v -> listener.onMusicServiceSelected(service));
    }

    @Override
    public int getItemCount() {
        return services.length;
    }
}
