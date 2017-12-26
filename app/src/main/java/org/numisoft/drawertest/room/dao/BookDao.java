package org.numisoft.drawertest.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import org.numisoft.drawertest.room.entities.Book;
import org.numisoft.drawertest.room.entities.BookWithAuthor;

import java.util.List;

/**
 * Created by mac-200 on 11/29/17.
 */

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    List<Book> getAll();

    @Query("SELECT * FROM books WHERE id = :id")
    Book getBookById(int id);

    @Transaction
    @Query("SELECT * FROM books ORDER BY year DESC")
    LiveData<List<BookWithAuthor>> getAllBooks();

    @Transaction
    @Query("SELECT * FROM books WHERE year = :year")
    LiveData<List<BookWithAuthor>> getBooksByYear(String year);

    @Transaction
    @Query("SELECT * FROM books WHERE author_id = :position")
    LiveData<List<BookWithAuthor>> getBooksByAuthor(int position);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);
}
