package org.numisoft.drawertest.recycler.holders;

import android.view.View;
import android.widget.TextView;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.model.NavMenuItem;
import org.numisoft.drawertest.model.TitleItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac-200 on 11/28/17.
 */

public class TitleItemHolder extends BaseItemHolder {

    @BindView(R.id.title_text)
    TextView tvTitle;

    public TitleItemHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(NavMenuItem item, int position) {
        tvTitle.setText(((TitleItem) item).getTitle());
    }

}
