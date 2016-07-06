package boombotix.com.thundercloud.api;

import boombotix.com.thundercloud.model.search.spotify.SearchResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Endpoint to handle Spotify search results. Can return a mix of result types.
 */
public interface SpotifySearchEndpoint {
    @GET("/search")
    Observable<SearchResponse> getAllResults(@Query("q") String query, @Query("type") String type);
}
