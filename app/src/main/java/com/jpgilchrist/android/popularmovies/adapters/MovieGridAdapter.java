package com.jpgilchrist.android.popularmovies.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jpgilchrist.android.popularmovies.R;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jpegz on 3/20/17.
 */

/**
 * RecyclerView Adapter for the GridLayout in the Main Activity
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private static final String TAG = MovieGridAdapter.class.getSimpleName();

    /**
     * ViewHolder for the RecyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        /**
         * Constructor for the ViewHolder - sets any references to items in the view for later use
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.movie_grid_item_title);
        }
    }

    private List<TMDBPage.TMDBResult> data = new LinkedList<>();

    public MovieGridAdapter() {}

    /**
     * onCreateViewHolder is called once per view that's created (not bound)
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * onBindViewHOlder is called to bind data to an existing ViewHolder created by onCreateViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(this.data.get(position).getTitle());
    }

    /**
     * getItemCount returns the size of the data set
     * @return
     */
    @Override
    public int getItemCount() {
        if (this.data == null) {
            return 0;
        }
        return this.data.size();
    }

    public void appendPage(TMDBPage page) {
        this.data.addAll(page.getResults());
        notifyDataSetChanged();
    }

    public void reset() {
        this.data.clear();
        notifyDataSetChanged();
    }
}
