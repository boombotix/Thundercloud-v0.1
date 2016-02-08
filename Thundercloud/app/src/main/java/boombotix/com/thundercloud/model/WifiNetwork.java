package boombotix.com.thundercloud.model;

/**
 * Class containing wif network data retrieved from speaker.
 *
 * @author Theo Kanning
 */
public class WifiNetwork {

    private String ssid;
    private Integer strength;
    private String securityType; //todo replace with security type enum

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Integer getStrength() {
        return strength;
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
}
