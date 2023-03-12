package com.codepath.bestsellerlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;
import com.codepath.bestsellerlistapp.models.BestSellerBook;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BestSellerBook} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class BestSellerBooksRecyclerViewAdapter extends RecyclerView.Adapter<BestSellerBooksRecyclerViewAdapter.BookViewHolder>{

    private final List<BestSellerBook> books;
    private final OnListFragmentInteractionListener mListener;

    public BestSellerBooksRecyclerViewAdapter(List<BestSellerBook> items, OnListFragmentInteractionListener listener) {
        books = items;
        mListener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_best_seller_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        holder.mItem = books.get(position);
        holder.mBookTitle.setText(books.get(position).title);
        holder.mBookAuthor.setText(books.get(position).author);
        Glide.with(holder.mView)
                .load(books.get(position).bookImageUrl)
                .into(holder.bookImage);
        holder.bookDescription.setText(books.get(position).description);
        // TODO Rank not working
        //holder.ranking.setText(books.get(position).rank);

        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Button clicked",
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView ranking;
        public final TextView mBookTitle;
        public final TextView mBookAuthor;
        public final TextView bookDescription;
        public BestSellerBook mItem;
        public final ImageView bookImage;
        public final Button buyButton;

        public BookViewHolder(View view) {
            super(view);
            mView = view;
            ranking = view.findViewById(R.id.ranking);
            mBookTitle = view.findViewById(R.id.book_title);
            mBookAuthor = view.findViewById(R.id.book_author);
            bookDescription = view.findViewById(R.id.book_description);
            bookImage = view.findViewById(R.id.book_image);
            buyButton = view.findViewById(R.id.buy_button);
        }

        @Override
        public String toString() {
            return mBookTitle.toString() + " '" + mBookAuthor.getText() + "'";
        }
    }
}