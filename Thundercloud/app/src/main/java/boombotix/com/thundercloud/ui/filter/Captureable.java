package boombotix.com.thundercloud.ui.filter;

import android.view.View;

/**
 * Interface to facilitate returning a view for the purposes of capture
 * and manipulation (i.e. screenshotting, view filtering, etc)
 *
 * Designed to be implemented by fragments. Fragments wishing to implement it
 * should override {@link #captureView} to return the element(s) of their
 * view that they it wants captured or manipulated. In most cases this will
 * be the base view, although it can be anything.
 *
 * Created by kwatson on 2/16/16.
 */
public interface Captureable {

    View captureView();

}
