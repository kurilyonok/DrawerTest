package org.numisoft.drawertest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import org.numisoft.drawertest.calendarrecycler.OnYearClickListener;
import org.numisoft.drawertest.calendarrecycler.YearsAdapter;
import org.numisoft.drawertest.model.Year;
import org.numisoft.drawertest.room.entities.BookWithAuthor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac-200 on 12/11/17.
 */

public class CalendarWindow extends PopupWindow {

    private View overlay;
    private View anchor;
    private ViewGroup root;
    private Context context;
    private WeakReference<OnYearClickListener> listener;
    private int columnCount;
    private int doubleSpan;
    private List<BookWithAuthor> books;

    public CalendarWindow(View anchor, OnYearClickListener listener, List<BookWithAuthor> books) {
        super(((LayoutInflater) anchor.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_window, null));
        this.anchor = anchor;
        this.listener = new WeakReference<>(listener);
        this.books = books;
//        setAnimationStyle();
        init();
    }

    private void init() {
        context = anchor.getContext();

        createOverlay();

        int width = (int) context.getResources().getDimension(R.dimen.pop_width);
        int height = (int) context.getResources().getDimension(R.dimen.pop_height);

        setWidth(width);
        setHeight(height);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setElevation(context.getResources().getDimension(R.dimen.pop_elevation));
        setOnDismissListener(() -> root.removeView(overlay));

        setupRecyclerView();

        show();
    }

    private void setupRecyclerView() {
        RecyclerView rvYears = getContentView().findViewById(R.id.rv_years);
        columnCount = context.getResources().getInteger(R.integer.year_column_count);
        doubleSpan = context.getResources().getInteger(R.integer.double_span);
        YearsAdapter adapter = new YearsAdapter(getYears(), listener);
        GridLayoutManager layoutManager = new GridLayoutManager(context, columnCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case Year.TYPE_HEADER:
                        return doubleSpan;
                    default:
                        return 1;
                }
            }
        });

        rvYears.setLayoutManager(layoutManager);
        rvYears.setAdapter(adapter);
    }

    private List<Year> getYears() {
        int favoriteCount = 0;
        Map<String, Year> yearsMap = new HashMap<>();
        for (String title : Year.YEARS) {
            int yearType = title.equalsIgnoreCase(Year.HEADER) ? Year.TYPE_HEADER : Year.TYPE_SINGLE;
            yearsMap.put(title, new Year(title, yearType));
        }

        for (BookWithAuthor book : books) {
            String yearTitle = book.getBook().getYear();
            if (yearsMap.containsKey(yearTitle)) {

                Year year = yearsMap.get(yearTitle);
                year.setBooksCount(year.getBooksCount() + 1);

                if (book.getBook().isFavorite()) {
                    year.setFavoriteCount(year.getFavoriteCount() + 1);
                    favoriteCount++;
                }

                yearsMap.put(yearTitle, year);
            }
        }

        yearsMap.put(Year.HEADER, new Year(Year.HEADER, favoriteCount, books.size(), Year.TYPE_HEADER));

        List<Year> years = new ArrayList<>();
        years.addAll(yearsMap.values());
        Collections.sort(years, (year1, year2) -> year2.getTitle().compareTo(year1.getTitle()));

        int doubleCount = 1;
        int delta = (columnCount - (years.size() + (doubleSpan - 1) * doubleCount) % columnCount) % columnCount;
        while (delta > 0) {
            years.add(new Year(Year.TYPE_STUB));
            delta--;
        }

        return years;
    }

    private void show() {
        int margin = (int) context.getResources().getDimension(R.dimen.pop_margin);
        showAsDropDown(anchor, -margin, -margin, Gravity.RIGHT);
    }

    private void createOverlay() {
        root = findRootView(anchor);
        overlay = new View(context);
        overlay.setOnClickListener(view -> dismiss());
        overlay.setLayoutParams(new ViewGroup.LayoutParams(root.getWidth(), root.getHeight()));
        root.addView(overlay);
    }

    private ViewGroup findRootView(View anchorView) {
        ViewGroup decorView = (ViewGroup) anchorView.getRootView();
        return (ViewGroup) ((ViewGroup) decorView.getChildAt(0)).getChildAt(1);
    }

}
