package boombotix.com.thundercloud.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import boombotix.com.thundercloud.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jsaucedo on 2/1/16.
 */
public class YourMusicViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.item_title)
    TextView itemTitle;
    @Bind(R.id.item_subtitle)
    TextView itemSubtitle;
    public YourMusicViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bindTitle(String title){
        this.itemTitle.setText(title);
    }
    public void bindSubtitle(String subtitle)
    {
        this.itemSubtitle.setText(subtitle);
    }

}
