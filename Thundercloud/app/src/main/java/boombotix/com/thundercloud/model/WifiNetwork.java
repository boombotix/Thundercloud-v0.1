package boombotix.com.thundercloud.model;

/**
 * Class containing wif network data retrieved from speaker.
 *
 * @author Theo Kanning
 */
public class WifiNetwork {

    //todo move into common folder to be shared with embedded module
    private String ssid;

    private Integer strength;

    private String securityType; //todo replace with security type enum

    private WifiCredentials credentials;

    public WifiNetwork(String ssid, Integer strength, String securityType) {
        this.ssid = ssid;
        this.strength = strength;
        this.securityType = securityType;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getStrength() {
        return strength; //todo return strength enum
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public WifiCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(WifiCredentials credentials) {
        this.credentials = credentials;
    }
}