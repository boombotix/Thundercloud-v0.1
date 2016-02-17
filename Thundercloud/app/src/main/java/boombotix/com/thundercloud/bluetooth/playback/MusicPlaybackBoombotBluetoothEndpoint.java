package boombotix.com.thundercloud.bluetooth.playback;

import javax.inject.Inject;

import boombotix.com.thundercloud.bluetooth.BluetoothCommandReceiver;
import boombotix.com.thundercloud.bluetooth.BluetoothCommandSender;
import boombotix.com.thundercloud.model.playback.MusicPlaybackAction;
import boombotix.com.thundercloud.model.playback.MusicPlaybackState;
import rx.Observable;

/**
 * Implementation of {@link MusicPlaybackBluetoothEndpoint} that sends and receives playback info
 * over bluetooth
 */
public class MusicPlaybackBoombotBluetoothEndpoint implements MusicPlaybackBluetoothEndpoint {

    private BluetoothCommandSender sender;

    private BluetoothCommandReceiver receiver;

    @Inject
    public MusicPlaybackBoombotBluetoothEndpoint(
            BluetoothCommandSender sender,
            BluetoothCommandReceiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public Observable<Boolean> controlPlayback(MusicPlaybackAction action) {
        return null;
    }

    @Override
    public Observable<MusicPlaybackState> getPlaybackState() {
        return null;
    }

    @Override
    public Observable<Boolean> setVolume(int volume) {
        return null;
    }

    @Override
    public Observable<Boolean> updateQueue() {
        return null;
    }
}
