package boombotix.com.thundercloud.houndify.response;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.hound.android.sdk.TextSearch;

import javax.inject.Inject;

import boombotix.com.thundercloud.houndify.model.MusicResponse;
import boombotix.com.thundercloud.houndify.model.Track;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by kriedema on 6/10/16.
 */
@RxLogSubscriber
public class HoundifySubscriber extends Subscriber<TextSearch.Result> {

    HoundifyResponseParser houndifyResponseParser;

    @Inject
    public HoundifySubscriber(HoundifyResponseParser houndifyResponseParser){
        this.houndifyResponseParser = houndifyResponseParser;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e.getMessage());
    }

    @Override
    public void onNext(TextSearch.Result result) {
        MusicResponse musicResponse = houndifyResponseParser.parseMusicResponse(result.getResponse());

        if(musicResponse == null) {
            Timber.d("Music response was null");

            return;
        }

        for(Track track : musicResponse.getTracks()){
            Timber.d("Artist: " + track.getArtistName() + " Album: " + track.getAlbumName() + " Track: " + track.getTrackName());
        }
    }
}
