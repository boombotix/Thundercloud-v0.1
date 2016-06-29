package boombotix.com.thundercloud.model.constants;

import java.util.UUID;

/**
 * Common UUID, Strings, and status codes we use for bluetooth connection
 */
public class BluetoothConstants {
    // defined in the bluetooth spec as the UUID used for legacy SPP connections
    // we can assume the speaker will broadcast this when we attempt to write wifi credentials to the speaker
    public static final UUID SPP_STANDARD_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static final String BOOMBOT_BASE_NAME = "Boombot";
    public static final String TEST_HARDWARE_BASE_NAME = "beaglebone";
    public static final String BOOMBOT_SHAREDPREF_KEY = "PairedDevice";

    // not final, right now this is all we expect to get back. not implimented on the speaker yet
    public static final byte SUCCESS = 0x1;
    public static final byte ERROR = 0x2;
    public static final byte TIMEOUT = 0x4;
}
