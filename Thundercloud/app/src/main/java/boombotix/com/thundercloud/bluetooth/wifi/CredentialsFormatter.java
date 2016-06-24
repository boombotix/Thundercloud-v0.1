package boombotix.com.thundercloud.bluetooth.wifi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import hugo.weaving.DebugLog;

/**
 * Created by kriedema on 6/24/16.
 */
public class CredentialsFormatter {
    static final String formattableString = "WIFI: WPA:%s%s%s\0";

    @DebugLog
    public byte[] prepareCredentialsForSpeaker(String ssid, String password){
        try {
            return String.format(formattableString, ssid, Character.toString((char)7), password)
                    .getBytes(Charset.forName("ASCII").displayName());
        } catch (UnsupportedEncodingException ex){
            throw new RuntimeException("Couldn't format wireless credentials", ex);
        }
    }
}
