package boombotix.com.thundercloud.database.repository;

import com.j256.ormlite.dao.Dao;

import boombotix.com.thundercloud.database.model.RecentSearch;

/**
 * Created by kwatson on 1/27/16.
 */
public class RecentSearchRepository extends ObjectRepository<RecentSearch, Long> {

    public RecentSearchRepository(Dao<RecentSearch, Long> dao) {
        super.dao = dao;
    }
}
