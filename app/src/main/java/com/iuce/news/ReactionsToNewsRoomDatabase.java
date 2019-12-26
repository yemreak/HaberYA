package com.iuce.news;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Details: https://android.yemreak.com/veriler/room-database#room-database
 */
@Database(entities = {ReactionToNews.class}, version = 1, exportSchema = false)
public abstract class ReactionsToNewsRoomDatabase extends RoomDatabase {

    public static final String DB_NAME = "news_db";

    abstract ReactionsToNewsDao reactionToNewsDao();

    // Singleton to safe db conflicts
    private static ReactionsToNewsRoomDatabase INSTANCE;

    /**
     * Returns singleton DB Instance
     * Details: https://android.yemreak.com/veriler/room-database#synchronized-ile-dbyi-koruma
     * @param context Activity context
     * @return Database
     */
    static ReactionsToNewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // To keep safe the db for more than one changes
            synchronized (ReactionsToNewsRoomDatabase.class) {
                // Don't block thread if INSTANCE is null
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ReactionsToNewsRoomDatabase.class,
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
