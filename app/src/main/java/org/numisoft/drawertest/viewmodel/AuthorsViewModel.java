package org.numisoft.drawertest.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.numisoft.drawertest.room.AppDatabase;
import org.numisoft.drawertest.room.entities.AuthorWithBooks;

import java.util.List;

/**
 * Created by mac-200 on 12/1/17.
 */

public class AuthorsViewModel extends AndroidViewModel {

    private LiveData<List<AuthorWithBooks>> authors;

    public AuthorsViewModel(@NonNull Application application) {
        super(application);
        authors = AppDatabase.getInstance(application).authorDao().getAuthorsWithBooks();
    }

//    public AuthorsViewModel() {
//        authors = DrawerTestApp.getDb().authorDao().getAuthorsWithBooks();
//    }

    public LiveData<List<AuthorWithBooks>> getAuthors() {
        return authors;
    }

}
