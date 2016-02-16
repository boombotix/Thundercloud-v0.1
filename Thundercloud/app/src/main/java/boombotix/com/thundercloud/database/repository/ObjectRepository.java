package boombotix.com.thundercloud.database.repository;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;


/**
 * ObjectRepository - All Repositories should extend this.
 *
 * Contains a pretty hacky way of attempting to better handle the silly SQLExceptions that
 * ORMLite throws.
 *
 * Also contains generified versions of all common database operations, in an attempt to avoid
 * duplicating all the logic for each repository.
 */
public class ObjectRepository<T, I> {

    protected Dao<T, I> dao;

    public T find(I id) {
        T verb = null;

        try {
            verb = this.dao.queryForId(id);
        } catch (SQLException ex) {
            onSqlException(ex);
        }

        return verb;
    }

    public List<T> findAll() {
        List<T> allObjs = new ArrayList<T>();

        try {
            allObjs = this.dao.queryForAll();
        } catch (SQLException e) {
            onSqlException(e);
        }

        return allObjs;
    }

    public Observable<List<T>> findAllAsync() {
        Future<List<T>> future = new Future<List<T>>() {
            boolean isDone;

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return isDone;
            }

            @Override
            public List<T> get() throws InterruptedException, ExecutionException {
                List<T> result = findAll();
                isDone = true;
                return result;
            }

            @Override
            public List<T> get(long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return get();
            }
        };

        return Observable.from(future);
    }


    public void removeAll(List<T> dataToRemove) {
        try {
            this.dao.delete(dataToRemove);
        } catch (SQLException e) {
            onSqlException(e);
        }
    }

    public void removeAll() {
        try {
            TableUtils.clearTable(this.dao.getConnectionSource(), this.dao.getDataClass());
        } catch (SQLException e) {
            onSqlException(e);
        }
    }

    public void saveAll(List<T> allData) {
        for (T item : allData) {
            saveOrUpdate(item);
        }
    }

    public void saveOrUpdate(T data) {
        try {
            this.dao.createOrUpdate(data);
        } catch (SQLException ex) {
            onSqlException(ex);
        }
    }

    public void delete(T data) {
        try {
            this.dao.delete(data);
        } catch (SQLException e) {
            onSqlException(e);
        }
    }

    public void onSqlException(SQLException e) {
        throw new RuntimeException(e);
    }
}
