package com.yemreak.haberya.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yemreak.haberya.db.dao.NewsDao;
import com.yemreak.haberya.db.dao.NewsWithStateDao;
import com.yemreak.haberya.db.dao.StateDao;
import com.yemreak.haberya.db.entity.News;
import com.yemreak.haberya.db.entity.State;

/**
 * Details: https://android.yemreak.com/veriler/room-database#room-database
 */
@Database(entities = {News.class, State.class}, version = 9, exportSchema =
        false)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public static final String DB_NAME = "news_db";
    // Singleton to safe db conflicts
    private static NewsRoomDatabase INSTANCE;

    /**
     * Returns singleton DB Instance
     * Details: https://android.yemreak.com/veriler/room-database#synchronized-ile-dbyi-koruma
     *
     * @param context Activity context
     * @return Database
     */
    public static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // To keep safe the db for more than one changes
            synchronized (NewsRoomDatabase.class) {
                // Don't block thread if INSTANCE is null
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NewsRoomDatabase.class,
                            DB_NAME
                    )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract NewsDao newsDao();

    public abstract NewsWithStateDao newsWithStateDao();

    public abstract StateDao stateDao();
}
