package boombotix.com.thundercloud.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import boombotix.com.thundercloud.R;
import boombotix.com.thundercloud.ui.viewholder.SearchResultsHeaderViewHolder;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {
    private final Drawable itemDivider;
    private final Drawable headerDivider;
    private final int offsetHeight;
    private static final int SIDE_MARGIN = 48;

    public DividerItemDecorator(Context context, int offsetHeight) {
        // the methods that aren't depricated are 21+
        this.offsetHeight = offsetHeight;
        this.itemDivider = context.getResources().getDrawable(R.drawable.search_item_divider);
        this.headerDivider = context.getResources().getDrawable(R.drawable.search_header_divider);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + SIDE_MARGIN;
        int right = parent.getWidth() - (parent.getPaddingRight() + SIDE_MARGIN);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            Drawable divider = itemDivider;

            if(SearchResultsHeaderViewHolder.class.isInstance(child)){
                divider = headerDivider;
            }

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin + (offsetHeight/2);
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }
}
