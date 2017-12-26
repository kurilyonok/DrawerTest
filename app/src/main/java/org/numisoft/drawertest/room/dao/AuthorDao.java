package org.numisoft.drawertest.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import org.numisoft.drawertest.room.entities.Author;
import org.numisoft.drawertest.room.entities.AuthorWithBooks;

import java.util.List;

/**
 * Created by mac-200 on 11/29/17.
 */

@Dao
public interface AuthorDao {

    @Query("SELECT * FROM authors")
    List<Author> getAuthors();

    @Transaction
    @Query("SELECT * FROM authors")
    LiveData<List<AuthorWithBooks>> getAuthorsWithBooks();

    @Insert
    void insert(Author author);

    @Delete
    void delete(Author author);

}
