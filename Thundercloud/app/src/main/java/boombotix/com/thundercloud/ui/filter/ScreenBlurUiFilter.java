package boombotix.com.thundercloud.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

/**
 * UI Filter class which can create a blurred version of
 * provided views
 *
 * Created by kwatson on 2/9/16.
 */
public class ScreenBlurUiFilter {

    private static final int BLUR_IN_SAMPLE_SIZE = 8;

    private final BitmapFactory.Options blurOptions;
    private Context context;

    @Inject
    public ScreenBlurUiFilter(Activity activity) {
        this.context = activity;
        this.blurOptions = new BitmapFactory.Options();
        this.blurOptions.inSampleSize = BLUR_IN_SAMPLE_SIZE;
    }

    public Bitmap blurView(View viewToBlur) {
        Bitmap bitmapOfView;
        //Depending on the view type, a given capture method may not work,
        //so we try one first and then fall back to the other.
        try {
            bitmapOfView = captureBitmapViaCanvas(viewToBlur);
        } catch (IllegalArgumentException ex) {
            Log.d("ScreenBlur", "Couldn't capture bitmap via canvas. " +
                    "Attempting to capture via drawing cache.");
            bitmapOfView = captureBitmapFromDrawingCache(viewToBlur);
        }

        //Apply blur
        RenderScript renderScript = RenderScript.create(this.context);
        final Allocation input = Allocation.createFromBitmap(renderScript, bitmapOfView);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        final ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));

        blurScript.setRadius(24f);
        blurScript.setInput(input);
        blurScript.forEach(output);
        output.copyTo(bitmapOfView);

        return bitmapOfView;
    }

    /**
     * Creates a Bitmap from a View by drawing it onto a Canvas. Seems to work best
     * on singular views like an ImageView
     *
     * @param view
     *      View to Capture Bitmap of
     * @return
     *      Bitmap image of View
     */
    private Bitmap captureBitmapViaCanvas(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Bitmap bitmapOfView = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Canvas canvas = new Canvas(bitmapOfView);
        view.draw(canvas);

        return bitmapOfView;
    }

    /**
     * Capture a Bitmap from a View by creating and copying the view's drawing
     * cache.
     *
     * @param view
     *          View to capture Bitmap from
     * @return
     *          Bitmap image of view
     */
    private Bitmap captureBitmapFromDrawingCache(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache().copy(Bitmap.Config.ARGB_8888, false);
        view.destroyDrawingCache();
        return bitmap;
    }

}
