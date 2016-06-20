package boombotix.com.thundercloud.api;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import kaaes.spotify.webapi.android.models.Track;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by kriedema on 6/17/16.
 */
public interface SpotifyTrackEndpoint {
    @RxLogObservable
    @GET("/tracks/{id}/")
    Observable<Track> getTrack(@Path("id") String id);
}
