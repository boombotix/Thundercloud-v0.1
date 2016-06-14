package boombotix.com.thundercloud.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Applies the most common set of schedulers that we use with rx. Unchecked cast is to handle
 * all types, casting to object and then back to a generic observable SHOULD be fine
 *
 * Created by kriedema on 6/11/16.
 */
public class RxTransformers {
    static final Observable.Transformer schedulersTransformer =
            observable -> ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}