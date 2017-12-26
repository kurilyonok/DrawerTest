package org.numisoft.drawertest.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mac-200 on 11/29/17.
 */

@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @SerializedName("author_id")
    @ColumnInfo(name = "author_id")
    private int authorId;

    private String year;

    public Book(String title, int authorId, String year) {
        this.title = title;
        this.authorId = authorId;
        this.year = year;
    }

    public Book(Book copy) {
        this.setId(copy.getId());
        this.setTitle(copy.getTitle());
        this.setFavorite(copy.isFavorite());
        this.setAuthorId(copy.getAuthorId());
        this.setYear(copy.getYear());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
