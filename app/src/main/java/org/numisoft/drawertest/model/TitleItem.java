package org.numisoft.drawertest.model;

/**
 * Created by mac-200 on 11/28/17.
 */

public class TitleItem implements NavMenuItem {

    private String title;

    public TitleItem(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return NavMenuItem.TYPE_TITLE;
    }

    public String getTitle() {
        return title;
    }
}
