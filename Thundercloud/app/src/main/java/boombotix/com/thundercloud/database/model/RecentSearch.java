package boombotix.com.thundercloud.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.joda.time.DateTime;

/**
 * ORMLite database object for storing instances of
 * a user's recent music searches (text, not voice)
 *
 * Created by kwatson on 1/26/16.
 */
@DatabaseTable
public class RecentSearch {

    @DatabaseField
    private long id;

    @DatabaseField
    private String searchTerm;

    @DatabaseField
    private DateTime dateLastSearched;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public DateTime getDateLastSearched() {
        return dateLastSearched;
    }

    public void setDateLastSearched(DateTime dateLastSearched) {
        this.dateLastSearched = dateLastSearched;
    }
}
