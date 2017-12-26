package org.numisoft.drawertest.model;

/**
 * Created by mac-200 on 11/28/17.
 */

public interface NavMenuItem {

    int TYPE_HEADER = 0;
    int TYPE_TITLE = 1;
    int TYPE_AUTHOR = 2;

    int getItemType();

}
