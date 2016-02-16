package boombotix.com.thundercloud.bluetooth.playback;

import boombotix.com.thundercloud.model.playback.MusicPlaybackAction;
import boombotix.com.thundercloud.model.playback.MusicPlaybackState;
import rx.Observable;

/**
 * Endpoint for sending and receiving all music-playback-related communication with the speaker
 *
 * @author Theo Kanning
 */
public interface MusicPlaybackBluetoothEndpoint {

    Observable<Boolean> controlPlayback(MusicPlaybackAction action);

    Observable<MusicPlaybackState> getPlaybackState();

    Observable<Boolean> setVolume(int volume); //todo constrain volume
    //todo return current volume setting?

    Observable<Boolean> updateQueue();//todo add queue update object
}
