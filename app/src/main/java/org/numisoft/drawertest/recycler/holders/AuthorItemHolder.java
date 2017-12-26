package org.numisoft.drawertest.recycler.holders;

import android.view.View;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.model.AuthorItem;
import org.numisoft.drawertest.model.NavMenuItem;
import org.numisoft.drawertest.recycler.OnNavMenuClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac-200 on 11/28/17.
 */

public class AuthorItemHolder extends BaseItemHolder {

    @BindView(R.id.item_text)
    TextView tvTitle;

    @BindView(R.id.swipe_layout)
    SwipeRevealLayout swipeLayout;

    @BindView(R.id.container)
    View container;

    public AuthorItemHolder(View view, OnNavMenuClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
        container.setOnClickListener(v ->
                listener.onAuthorClick(getAdapterPosition()));
    }

    @Override
    public void bind(NavMenuItem item, int position) {
        tvTitle.setText(((AuthorItem) item).getLastName());
    }

    public SwipeRevealLayout getSwipeRevealLayout() {
        return swipeLayout;
    }
}
