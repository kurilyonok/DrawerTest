package org.numisoft.drawertest.model;

/**
 * Created by mac-200 on 11/28/17.
 */

public class HeaderItem implements NavMenuItem {

    @Override
    public int getItemType() {
        return NavMenuItem.TYPE_HEADER;
    }

}
