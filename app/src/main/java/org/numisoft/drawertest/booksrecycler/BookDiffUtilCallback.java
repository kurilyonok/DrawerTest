package org.numisoft.drawertest.booksrecycler;

import android.support.v7.util.DiffUtil;

import org.numisoft.drawertest.room.entities.Book;
import org.numisoft.drawertest.room.entities.BookWithAuthor;

import java.util.List;

/**
 * Created by mac-200 on 12/1/17.
 */

public class BookDiffUtilCallback extends DiffUtil.Callback {

    private List<BookWithAuthor> oldBooks;
    private List<BookWithAuthor> newBooks;

    public BookDiffUtilCallback(List<BookWithAuthor> oldBooks, List<BookWithAuthor> newBooks) {
        this.oldBooks = oldBooks;
        this.newBooks = newBooks;
    }

    @Override
    public int getOldListSize() {
        return oldBooks.size();
    }

    @Override
    public int getNewListSize() {
        return newBooks.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        int oldBookId = oldBooks.get(oldItemPosition).getBook().getId();
        int newBookId = newBooks.get(newItemPosition).getBook().getId();
        return oldBookId == newBookId;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Book oldBook = oldBooks.get(oldItemPosition).getBook();
        Book newBook = newBooks.get(newItemPosition).getBook();


        String oldBookTitle = oldBook.getTitle();
        String newBookTitle = newBook.getTitle();

        boolean oldBookIsFavorite = oldBook.isFavorite();
        boolean newBookIsFavorite = newBook.isFavorite();

        return oldBookTitle.equalsIgnoreCase(newBookTitle) && oldBookIsFavorite == newBookIsFavorite;
    }

}
