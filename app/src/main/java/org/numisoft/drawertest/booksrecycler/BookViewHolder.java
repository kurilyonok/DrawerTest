package org.numisoft.drawertest.booksrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.numisoft.drawertest.R;
import org.numisoft.drawertest.room.entities.Book;
import org.numisoft.drawertest.room.entities.BookWithAuthor;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac-200 on 12/1/17.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_author)
    TextView tvAuthor;

    @BindView(R.id.tv_book_title)
    TextView tvBookTitle;

    @BindView(R.id.iv_favorite)
    ImageView ivFavorite;

    public BookViewHolder(View view, OnBookClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);

        ivFavorite.setOnClickListener(favorite -> {
            Book book1 = (Book) itemView.getTag();
            listener.onFavoriteClick(book1);
        });

        itemView.setOnClickListener(book -> {
            Book book2 = (Book) itemView.getTag();
            listener.onBookClick(book2);
        });

    }

    public void bind(BookWithAuthor book) {
        String firstName = book.getAuthors().get(0).getFirstName();
        String lastName = book.getAuthors().get(0).getLastName();
        tvAuthor.setText(firstName.concat(" ")
                .concat(lastName)
                .concat(", ")
                .concat(book.getBook().getYear()));

        tvBookTitle.setText(book.getBook().getTitle());
        ivFavorite.setImageResource(
                book.getBook().isFavorite() ?
                        R.drawable.ic_star_black_24dp :
                        R.drawable.ic_star_border_black_24dp);

        itemView.setTag(book.getBook());
    }

}
