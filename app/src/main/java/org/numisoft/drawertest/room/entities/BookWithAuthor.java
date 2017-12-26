package org.numisoft.drawertest.room.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by mac-200 on 12/1/17.
 */

public class BookWithAuthor {

    @Embedded
    private Book book;

    @Relation(parentColumn = "author_id", entityColumn = "id")
    private List<Author> authors;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
