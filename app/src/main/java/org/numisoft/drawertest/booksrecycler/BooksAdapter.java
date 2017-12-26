package org.numisoft.drawertest.booksrecycler;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.room.entities.BookWithAuthor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac-200 on 12/1/17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BookViewHolder> implements Filterable {

    private List<BookWithAuthor> books = new ArrayList<>();
    private List<BookWithAuthor> booksFiltered = new ArrayList<>();
    private WeakReference<OnBookClickListener> listener;
    private String filterString;

    public BooksAdapter(OnBookClickListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view, listener.get());
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        if (booksFiltered.isEmpty()) return;
        holder.bind(booksFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return booksFiltered.size();
    }

    public void updateItems(List<BookWithAuthor> newBooks) {
        books.clear();
        books.addAll(newBooks);

        List<BookWithAuthor> booksFilteredOld = new ArrayList<>();
        booksFilteredOld.addAll(booksFiltered);
        booksFiltered.clear();

        if (filterString == null || filterString.isEmpty()) {
            booksFiltered.addAll(newBooks);
        } else {
            booksFiltered.addAll(filterNewBooks());
        }

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new BookDiffUtilCallback(booksFilteredOld, booksFiltered));
        result.dispatchUpdatesTo(this);
    }

    private List<BookWithAuthor> filterNewBooks() {
        return new ArrayList<BookWithAuthor>() {{
            for (BookWithAuthor b : books) {
                if (b.getBook().getTitle().toLowerCase().contains(filterString.toLowerCase())) {
                    add(b);
                }
            }
        }};
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filterString = charSequence.toString();
                booksFiltered.clear();

                if (filterString.isEmpty()) {
                    booksFiltered.addAll(books);
                } else {
                    List<BookWithAuthor> filteredList = new ArrayList<>();
                    for (BookWithAuthor b : books) {
                        if (b.getBook().getTitle().toLowerCase().contains(filterString.toLowerCase())) {
                            filteredList.add(b);
                        }
                    }
                    booksFiltered.addAll(filteredList);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = booksFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                booksFiltered = (ArrayList<BookWithAuthor>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
