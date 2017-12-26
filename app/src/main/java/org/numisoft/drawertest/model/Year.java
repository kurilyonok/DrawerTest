package org.numisoft.drawertest.model;

/**
 * Created by mac-200 on 12/11/17.
 */

public class Year {

    public static final String[] YEARS = {
            "ALL YEARS",
            "2017",
            "2016",
            "2015",
            "2014",
            "2013",
            "2012",
            "2011",
            "2010",
            "2009",
            "2008",
            "2007",
            "2006",
            "2005",
            "2004",
            "2003",
            "2002",
            "2001",
            "2000",
            "1999"
    };

    public static final String HEADER = "ALL YEARS";

    public static final int TYPE_STUB = 0;
    public static final int TYPE_SINGLE = 1;
    public static final int TYPE_DOUBLE = 2;
    public static final int TYPE_HEADER = 3;

    private String title;
    private int booksCount;
    private int favoriteCount;
    private int type;

    public Year(String title, int favoriteCount, int booksCount, int type) {
        this.title = title;
        this.favoriteCount = favoriteCount;
        this.booksCount = booksCount;
        this.type = type;
    }

    public Year(String title, int type) {
        this.title = title;
        this.type = type;
        this.booksCount = 0;
        this.favoriteCount = 0;
    }

    public Year(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
