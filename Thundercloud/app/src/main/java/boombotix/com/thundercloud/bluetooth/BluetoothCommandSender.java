package boombotix.com.thundercloud.bluetooth;

import javax.inject.Inject;

import boombotix.com.thundercloud.model.authentication.MusicService;
import boombotix.com.thundercloud.model.wifi.WifiCredentials;
import boombotix.com.thundercloud.model.authentication.OAuthCredentials;
import boombotix.com.thundercloud.model.playback.MusicPlaybackAction;

/**
 * Offers all bluetooth api commands, and encodes each message to bytes before adding a command
 * bytes and start/stop bytes
 *
 * @author Theo Kanning
 */
public class BluetoothCommandSender implements SpeakerBluetoothApi{

    private BluetoothMessageWrapper bluetoothMessageWrapper;

    @Inject
    public BluetoothCommandSender(
            BluetoothMessageWrapper bluetoothMessageWrapper) {
        this.bluetoothMessageWrapper = bluetoothMessageWrapper;
    }

    //todo add remaining commands
    @Override
    public void getSpeakerInfo() {
        sendCommand(CommandFlag.GET_SPEAKER_INFO);
    }

    @Override
    public void getAvailableNetworks() {

    }

    @Override
    public void connectToWifiNetwork(WifiCredentials credentials) {

    }

    @Override
    public void getWifiStatus() {

    }

    @Override
    public void updateCredentials(OAuthCredentials credentials, MusicService service) {

    }

    @Override
    public void refreshService(MusicService service) {

    }

    @Override
    public void getMusicServiceStatus(MusicService service) {

    }

    @Override
    public void controlPlayback(MusicPlaybackAction action) {

    }

    @Override
    public void getPlaybackState() {

    }

    @Override
    public void setVolume(int volume) {

    }

    @Override
    public void updateQueue() {

    }

    //encode object using protocol buffers
    private byte[] encode(){
        return new byte[]{};
    }

    private void sendCommand(CommandFlag flag){
        sendCommand(flag, null);
    }


    private void sendCommand(CommandFlag flag, Object data){
        //todo wrap with start and end bytes
        //todo send
    }
}
