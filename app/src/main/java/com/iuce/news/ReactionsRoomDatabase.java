package com.iuce.news;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Details: https://android.yemreak.com/veriler/room-database#room-database
 */
@Database(entities = {Reaction.class}, version = 2, exportSchema = false)
public abstract class ReactionsRoomDatabase extends RoomDatabase {

    public static final String DB_NAME = "news_db";

    abstract ReactionsDao reactionsDao();

    // Singleton to safe db conflicts
    private static ReactionsRoomDatabase INSTANCE;

    /**
     * Returns singleton DB Instance
     * Details: https://android.yemreak.com/veriler/room-database#synchronized-ile-dbyi-koruma
     * @param context Activity context
     * @return Database
     */
    static ReactionsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // To keep safe the db for more than one changes
            synchronized (ReactionsRoomDatabase.class) {
                // Don't block thread if INSTANCE is null
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ReactionsRoomDatabase.class,
                            DB_NAME
                    )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
