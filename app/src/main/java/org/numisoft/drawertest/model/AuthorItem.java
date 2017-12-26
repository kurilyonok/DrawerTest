package org.numisoft.drawertest.model;

/**
 * Created by mac-200 on 11/28/17.
 */

public class AuthorItem implements NavMenuItem {

    private String lastName;
    private int booksCount;
    private int favoriteCount;

    public AuthorItem(String title) {
        this.lastName = title;
    }

    @Override
    public int getItemType() {
        return NavMenuItem.TYPE_AUTHOR;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(int booksCount) {
        this.booksCount = booksCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
