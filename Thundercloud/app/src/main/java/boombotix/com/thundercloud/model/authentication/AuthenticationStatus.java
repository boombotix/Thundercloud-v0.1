package boombotix.com.thundercloud.model.authentication;

/**
 * The authentication status of a single music streaming service
 *
 * @author Theo Kanning
 */
public class AuthenticationStatus {
    private MusicService service;
    private int status; //todo enum for this?
    private String error;

    public AuthenticationStatus(MusicService service, int status, String error) {
        this.service = service;
        this.status = status;
        this.error = error;
    }

    public MusicService getService() {
        return service;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
