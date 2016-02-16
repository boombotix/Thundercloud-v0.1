package boombotix.com.thundercloud.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Database helper class for ORMLite. Handles creating and updating the database.
 *
 * @author Kenton Watson
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "macselect.db";

    /**
     * If this is incremented, ORMLite knows that an upgrade process needs to be run.
     * This must be adjusted if any change is made to the schema, particularly in
     * a production scenario (i.e. an upgrade posted to Play). In development,
     * it may make more sense to just uninstall the app, so that the database version
     * doesn't climb to the heavens.
     */
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
//        try {
//            //A table must be created for each entity object.
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
//        try {
//            //TODO: Consider an actual robost upgrade scenario.
//            //Each entity object must be separately handled here.
//
//            onCreate(db, connectionSource);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
    }
}