package com.iuce.news;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Details: https://android.yemreak.com/temel-kavramlar/room-database#room-database
 */
@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {

    abstract NewsDao newsDao();

    // Singleton to safe db conflicts
    private static NewsRoomDatabase INSTANCE;

    /**
     * Returns singleton DB Instance
     * Details: https://android.yemreak.com/temel-kavramlar/room-database#synchronized-ile-dbyi-koruma
     * @param context Activity context
     * @return Database
     */
    static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // To keep safe the db for more than one changes
            synchronized (NewsRoomDatabase.class) {
                // Don't block thread if INSTANCE is null
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NewsRoomDatabase.class,
                            "news_db"
                    )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
