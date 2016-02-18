package boombotix.com.thundercloud.bluetooth;

/**
 * Adds/removes protocol bytes on a bytestream
 *
 * @author Theo Kanning
 */
public class BluetoothMessageWrapper {

    private static final byte[] START_BYTES = new byte[]{0x00,0x00};
    private static final byte[] STOP_BYTES = new byte[]{0x00,0x00};

    public byte[] addWrapperBytes(byte[] bytes){
        //todo add start and stop bytes
        return bytes;
    }

    public byte[] removeWrapperBytes(byte[] bytes){
        //todo remove start and stop bytes
        return bytes;
    }
}
