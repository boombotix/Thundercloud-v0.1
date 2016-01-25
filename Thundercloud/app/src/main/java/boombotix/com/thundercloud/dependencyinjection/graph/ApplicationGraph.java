package boombotix.com.thundercloud.dependencyinjection.graph;

import boombotix.com.thundercloud.api.SpotifyEndpoint;

/**
 * Public interface defining the objects in our Dagger 2 application level object graph.
 *
 * Technically unnecessary, but keeps our component slightly cleaner,
 * and gives us the possibility to later easily introduce different modules
 * implementations for build variants (i.e. a release API client and a debug one)
 *
 * Created by kenton on 1/24/16.
 */
public interface ApplicationGraph {

    SpotifyEndpoint spotifyEndpoint();

}
