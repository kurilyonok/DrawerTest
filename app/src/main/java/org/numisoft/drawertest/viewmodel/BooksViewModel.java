package org.numisoft.drawertest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.numisoft.drawertest.room.AppDatabase;
import org.numisoft.drawertest.room.entities.Book;
import org.numisoft.drawertest.room.entities.BookWithAuthor;
import org.numisoft.drawertest.tasks.DeleteBookTask;
import org.numisoft.drawertest.tasks.InsertBookTask;
import org.numisoft.drawertest.tasks.UpdateBookByIdTask;
import org.numisoft.drawertest.tasks.UpdateBookTask;

import java.util.List;

/**
 * Created by mac-200 on 12/1/17.
 */

public class BooksViewModel extends AndroidViewModel {

    private AppDatabase database;

    public BooksViewModel(Application application) {
        super(application);
        database = AppDatabase.getInstance(application);
    }

    public LiveData<List<BookWithAuthor>> getBooks() {
        return database.bookDao().getAllBooks();
    }

    public LiveData<List<BookWithAuthor>> getBooksByYear(String year) {
        return database.bookDao().getBooksByYear(year);
    }

    public LiveData<List<BookWithAuthor>> getBooksByAuthor(int position) {
        return database.bookDao().getBooksByAuthor(position);
    }

    public void toggleFavorite(Book book) {
        Book copy = new Book(book);
        copy.setFavorite(!copy.isFavorite());
        new UpdateBookTask(database).execute(copy);
    }

    public void toggleFavorite(int id) {
        new UpdateBookByIdTask(database).execute(id);
    }

    public void deleteBook(Book book) {
        new DeleteBookTask(database).execute(book);
    }

    public void insertBook(Book book) {
        new InsertBookTask(database).execute(book);
    }
}
