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
    public YourMusicViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
//        itemTitle = (TextView) view.findViewById(R.id.item_title);
    }

    public void bindData(String item)
    {
        this.itemTitle.setText(item);
    }

}