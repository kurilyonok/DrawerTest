package org.numisoft.drawertest.tasks;

import android.os.AsyncTask;

import org.numisoft.drawertest.room.AppDatabase;
import org.numisoft.drawertest.room.entities.Book;

/**
 * Created by mac-200 on 12/5/17.
 */

public class DeleteBookTask extends AsyncTask<Book, String, String> {

    private AppDatabase database;

    public DeleteBookTask(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected String doInBackground(Book... books) {
        database.bookDao().delete(books[0]);
        return null;
    }

}
