package boombotix.com.thundercloud.ui.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import boombotix.com.thundercloud.R;

/**
 * Custom button class that changes between 'connect' and 'connected' states
 *
 * @author Theo Kanning
 */
public class ConnectButton extends AppCompatButton {

    public enum Style {
        CONNECT(android.R.color.white, R.color.colorAccent, R.string.connect),
        CONNECTED(R.color.colorPrimary, R.color.primaryBg, R.string.connected);

        private int textColorResourceId;

        private int backgroundColorResourceId;

        private int textResourceId;

        Style(int textColor, int background, int textString) {
            this.textColorResourceId = textColor;
            this.backgroundColorResourceId = background;
            this.textResourceId = textString;
        }
    }

    private static final float CORNER_RADIUS_DP = 2.5f;

    private Context context;

    public ConnectButton(Context context) {
        super(context);
        this.context = context;
    }

    public ConnectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ConnectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setStyle(Style style) {
        setBackgroundDrawable(style);
        setTextColor(style);
        setText(style);
    }

    private void setBackgroundDrawable(Style style){
        int color = ContextCompat.getColor(context, style.backgroundColorResourceId);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(dpToPx(CORNER_RADIUS_DP));
        drawable.setColor(color);
        setBackground(drawable);
    }

    private void setText(Style style){
        String text = context.getString(style.textResourceId);
        setText(text);
    }

    private void setTextColor(Style style){
        int color = ContextCompat.getColor(context, style.textColorResourceId);
        setTextColor(color);
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

}
