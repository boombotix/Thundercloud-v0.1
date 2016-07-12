package boombotix.com.thundercloud.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalOffset;

    public VerticalSpaceItemDecoration(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalOffset;
    }
}
