package org.numisoft.drawertest.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.numisoft.drawertest.room.dao.AuthorDao;
import org.numisoft.drawertest.room.dao.BookDao;
import org.numisoft.drawertest.room.entities.Author;
import org.numisoft.drawertest.room.entities.Book;

/**
 * Created by mac-200 on 11/29/17.
 */

@Database(entities = {Book.class, Author.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    public abstract AuthorDao authorDao();

    private static AppDatabase INSTANCE;


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "db-name")
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.delete("books", "id = 2", null);
                        }
                    })
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.wtf("ROOOOOM", "onCreate");
                        }
                    })
                    .build();
        }

        return INSTANCE;
    }

}
