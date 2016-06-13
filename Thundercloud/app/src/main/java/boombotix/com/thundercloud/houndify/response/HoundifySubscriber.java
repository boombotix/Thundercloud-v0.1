package boombotix.com.thundercloud.houndify.response;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.hound.android.sdk.TextSearch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import boombotix.com.thundercloud.houndify.model.Album;
import boombotix.com.thundercloud.houndify.model.Artist;
import boombotix.com.thundercloud.houndify.model.HoundifyTypes;
import boombotix.com.thundercloud.houndify.model.MusicSearchResultsNativeData;
import boombotix.com.thundercloud.houndify.model.MusicThirdPartyId;
import boombotix.com.thundercloud.houndify.model.Track;
import rx.Observable;
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
        Timber.d(result.getResponse().getResults().get(0).getJsonNode().toString());

        MusicSearchResultsNativeData musicResponse = houndifyResponseParser.parseMusicSearchResponse(result.getResponse());

        List<MusicThirdPartyId> spotifyUris = new ArrayList<>();

        if(musicResponse == null) {
            Timber.d("Music response was null");
            return;
        }
        for(Track track : musicResponse.getTracks()){
            Timber.d("Artist: " + track.getArtistName() + " Album: " + track.getAlbumName() + " Track: " + track.getTrackName());

            Observable.from(track.getMusicThirdPartyIds())
                    .filter(t -> t.getMusicThirdParty().getName().equals(HoundifyTypes.ThirdPartyMusicServices.Spotify.toString()))
                    .subscribe(spotifyUris::add);
        }
        for(Album album : musicResponse.getAlbums()){
            Timber.d("Artist: " + album.getArtistName() + " Album: " + album.getAlbumName());

            Observable.from(album.getMusicThirdPartyIds())
                    .filter(a -> a.getMusicThirdParty().getName().equals(HoundifyTypes.ThirdPartyMusicServices.Spotify.toString()))
                    .subscribe(spotifyUris::add);
        }
        for(Artist artist : musicResponse.getArtists()){
            Timber.d("Artist: " + artist.getArtistName() + " Type: " + artist.getArtistType());

            Observable.from(artist.getMusicThirdPartyIds())
                    .filter(a -> a.getMusicThirdParty().getName().equals(HoundifyTypes.ThirdPartyMusicServices.Spotify.toString()))
                    .subscribe(spotifyUris::add);

            for (Track track : artist.getTracks()){
                Timber.d("Artist: " + track.getArtistName() + " Album: " + track.getAlbumName() + " Track: " + track.getTrackName());

                Observable.from(track.getMusicThirdPartyIds())
                        .filter(t -> t.getMusicThirdParty().getName().equals(HoundifyTypes.ThirdPartyMusicServices.Spotify.toString()))
                        .subscribe(spotifyUris::add);
            }
        }
        for (MusicThirdPartyId id : spotifyUris){
            for(String link : id.getDeepLinks()){
                Timber.d(link);
            }
        }
    }
}
