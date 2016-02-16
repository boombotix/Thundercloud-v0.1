package boombotix.com.thundercloud.ui.viewholder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    @Bind(R.id.item_subtitle2)
    TextView itemSubtitle2;
    @Bind(R.id.item_art)
    ImageView imageview;
    Activity activity;

    public YourMusicViewHolder(View view, Activity activity) {
        super(view);
        ButterKnife.bind(this, view);
        this.activity = activity;
    }

    public void bindTitle(String title){
        this.itemTitle.setText(title);
    }
    public void bindSubtitle(String subtitle){
        if(subtitle == null){
            this.itemSubtitle.setVisibility(View.GONE);
        }
        else {
            this.itemSubtitle.setVisibility(View.VISIBLE);
            this.itemSubtitle.setText(subtitle);
        }
    }
    public void bindSubtitle2(String subtitle)
    {
        if(subtitle == null){
            this.itemSubtitle2.setVisibility(View.GONE);
        }
        else {
            this.itemSubtitle2.setVisibility(View.VISIBLE);
            this.itemSubtitle2.setText(subtitle);
        }
    }

    public void bindImage(String url){
        Glide
                .with(activity)
                .load(url)
                .fitCenter()
                .into(imageview);
    }

}
