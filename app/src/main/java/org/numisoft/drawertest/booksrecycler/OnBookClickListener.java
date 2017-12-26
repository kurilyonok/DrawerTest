package org.numisoft.drawertest.booksrecycler;

import org.numisoft.drawertest.room.entities.Book;

/**
 * Created by mac-200 on 12/1/17.
 */

public interface OnBookClickListener {

    void onFavoriteClick(Book book);

    void onBookClick(Book book);

}
