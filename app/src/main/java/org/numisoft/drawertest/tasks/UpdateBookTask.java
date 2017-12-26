package org.numisoft.drawertest.tasks;

import android.os.AsyncTask;

import org.numisoft.drawertest.room.AppDatabase;
import org.numisoft.drawertest.room.entities.Book;

/**
 * Created by mac-200 on 12/6/17.
 */

public class UpdateBookTask extends AsyncTask<Book, String, String> {

    private AppDatabase database;

    public UpdateBookTask(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected String doInBackground(Book... books) {
        database.bookDao().update(books[0]);
        return null;
    }
}
