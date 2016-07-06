package boombotix.com.thundercloud.model.search.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tracks {
    @SerializedName("href")
    private String queryUrl;

    @SerializedName("next")
    private String urlToNextPageOfResults;

    @SerializedName("previous")
    private String urlToPreviousPageOfResults;

    @SerializedName("items")
    private List<Track> trackList;

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getUrlToNextPageOfResults() {
        return urlToNextPageOfResults;
    }

    public void setUrlToNextPageOfResults(String urlToNextPageOfResults) {
        this.urlToNextPageOfResults = urlToNextPageOfResults;
    }

    public String getUrlToPreviousPageOfResults() {
        return urlToPreviousPageOfResults;
    }

    public void setUrlToPreviousPageOfResults(String urlToPreviousPageOfResults) {
        this.urlToPreviousPageOfResults = urlToPreviousPageOfResults;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }
}
