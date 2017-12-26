package org.numisoft.drawertest.calendarrecycler;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.model.Year;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac-200 on 12/11/17.
 */

public class YearViewHolder extends BaseYearViewHolder {

    @BindView(R.id.button_year)
    Button bYear;

    @BindView(R.id.tv_count)
    TextView tvCount;

    public YearViewHolder(View view, OnYearClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        bYear.setOnClickListener(view1 -> listener.onYearClick((Year) itemView.getTag()));
    }

    public void bind(Year year) {
        bYear.setText(year.getTitle());

        String count = Integer.toString(year.getFavoriteCount())
                .concat(" / ")
                .concat(Integer.toString(year.getBooksCount()));

        tvCount.setText(count);
        itemView.setTag(year);
    }

}
