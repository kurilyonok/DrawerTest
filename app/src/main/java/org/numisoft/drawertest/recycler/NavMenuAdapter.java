package org.numisoft.drawertest.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.model.NavMenuItem;
import org.numisoft.drawertest.recycler.holders.AuthorItemHolder;
import org.numisoft.drawertest.recycler.holders.BaseItemHolder;
import org.numisoft.drawertest.recycler.holders.HeaderItemHolder;
import org.numisoft.drawertest.recycler.holders.TitleItemHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mac-200 on 11/28/17.
 */

public class NavMenuAdapter extends RecyclerView.Adapter<BaseItemHolder> {

    private List<NavMenuItem> items;
    private ViewBinderHelper viewBinderHelper;
    private WeakReference<OnNavMenuClickListener> listener;

    public NavMenuAdapter(List<NavMenuItem> items, OnNavMenuClickListener listener) {
        this.items = items;
        this.listener = new WeakReference<>(listener);
        viewBinderHelper = new ViewBinderHelper();
        viewBinderHelper.setOpenOnlyOne(false);
    }

    @Override
    public BaseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case NavMenuItem.TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
                return new HeaderItemHolder(view);
            case NavMenuItem.TYPE_TITLE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
                return new TitleItemHolder(view);
            case NavMenuItem.TYPE_AUTHOR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author, parent, false);
                return new AuthorItemHolder(view, listener.get());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseItemHolder holder, int position) {
        holder.bind(items.get(position), position);
        if (holder instanceof AuthorItemHolder)
            viewBinderHelper.bind(((AuthorItemHolder) holder).getSwipeRevealLayout(), Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getItemType();
    }

}
