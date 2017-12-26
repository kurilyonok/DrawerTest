package org.numisoft.drawertest.tasks;

import android.os.AsyncTask;

import org.numisoft.drawertest.room.AppDatabase;
import org.numisoft.drawertest.room.entities.Book;

/**
 * Created by mac-200 on 12/8/17.
 */

public class UpdateBookByIdTask extends AsyncTask<Integer, Void, Void> {

    private AppDatabase database;

    public UpdateBookByIdTask(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Book book = database.bookDao().getBookById(integers[0]);
        book.setFavorite(!book.isFavorite());
        database.bookDao().update(book);
        return null;
    }

}
