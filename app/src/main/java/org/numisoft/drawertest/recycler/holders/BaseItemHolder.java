package org.numisoft.drawertest.recycler.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.numisoft.drawertest.model.NavMenuItem;

/**
 * Created by mac-200 on 11/28/17.
 */

public abstract class BaseItemHolder extends RecyclerView.ViewHolder {

    public BaseItemHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(NavMenuItem item, int position);

}
