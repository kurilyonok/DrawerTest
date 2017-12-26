package org.numisoft.drawertest.room.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by mac-200 on 11/30/17.
 */

public class AuthorWithBooks {

    @Embedded
    private Author author;

    @Relation(parentColumn = "id", entityColumn = "author_id")
    private List<Book> books;

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Book> getBooks() {
        return books;
    }

}
