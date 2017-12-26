package org.numisoft.drawertest.calendarrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.model.Year;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mac-200 on 12/11/17.
 */

public class YearsAdapter extends RecyclerView.Adapter<BaseYearViewHolder> {


    private List<Year> years;
    private WeakReference<OnYearClickListener> listener;

    public YearsAdapter(List<Year> years, WeakReference<OnYearClickListener> listener) {
        this.years = years;
        this.listener = listener;
    }

    @Override
    public BaseYearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Year.TYPE_SINGLE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year2, parent, false);
                return new YearViewHolder(view, listener.get());
            case Year.TYPE_HEADER:
//            case Year.TYPE_DOUBLE:
                View doubleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year_double, parent, false);
                return new YearViewHolder(doubleView, listener.get());
            case Year.TYPE_STUB:
                View stub = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year_stub, parent, false);
                return new StubViewHolder(stub);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseYearViewHolder holder, int position) {
        holder.bind(years.get(position));
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    @Override
    public int getItemViewType(int position) {
        return years.get(position).getType();
    }
}
