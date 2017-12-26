package org.numisoft.drawertest.calendarrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.numisoft.drawertest.model.Year;

/**
 * Created by mac-200 on 12/12/17.
 */

public abstract class BaseYearViewHolder extends RecyclerView.ViewHolder {

    public BaseYearViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Year year);

}
