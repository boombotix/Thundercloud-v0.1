package boombotix.com.thundercloud.api;


import boombotix.com.thundercloud.model.authentication.AuthRefreshResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by kenton on 1/24/16.
 */
public interface SpotifyAuthenticationEndpoint {

    @POST("/token")
    @FormUrlEncoded
    Observable<AuthRefreshResponse> getToken(@Header("Authorization") String authToken,
                                             @Field("grant_type") String grantType,
                                             @Field("refresh_token") String refreshToken );
}
