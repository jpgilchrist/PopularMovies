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

/**
 * Created by jpegz on 3/20/17.
 */

/**
 * RecyclerView Adapter for the GridLayout in the Main Activity
 */
public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.ViewHolder> {

    private static final String TAG = MovieGridAdapter.class.getSimpleName();

    public static final String FETCH_NEXT_PAGE_BROADCAST = "com.jpgilchrist.android.FETCH_NEXT_PAGE_BROADCAST";
    public static final String FETCH_PREVIOUS_PAGE_BROADCAST = "com.jpgilchrist.android.FETCH_PREVIOUS_PAGE_BROADCAST";

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

    private TMDBPage pageHolder;

    /**
     * initializes the adapter and accepts the "data set" as the argument
     */
    public MovieGridAdapter(TMDBPage initialPage) {
        if (initialPage == null) {
            this.pageHolder = new TMDBPage();
        } else {
            this.pageHolder = initialPage;
        }
    }

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
        Log.d(TAG, "onBindViewHolder position[" + position + "] itemCount[" + getItemCount() + "] lastPageLoaded[" + pageHolder.getPage() + "]");

        holder.titleTextView.setText(this.pageHolder.getResults().get(position).getTitle());

        if (position == getItemCount() - 1) { // we are binding the last item
            Log.d(TAG, "Sending Broadcast: " + FETCH_NEXT_PAGE_BROADCAST);

            Intent intent = new Intent();
            intent.setAction(FETCH_NEXT_PAGE_BROADCAST);
            holder.itemView.getContext().sendBroadcast(intent);
        } else if (position == 0) {

            int lastPageLoaded = pageHolder.getPage();
            if (lastPageLoaded * TMDBPage.ITEMS_PER_PAGE > pageHolder.getResults().size()) {
                Intent intent = new Intent();
                intent.setAction(FETCH_PREVIOUS_PAGE_BROADCAST);
                holder.itemView.getContext().sendBroadcast(intent);
            }
        }
    }

    /**
     * getItemCount returns the size of the data set
     * @return
     */
    @Override
    public int getItemCount() {
        if (this.pageHolder == null) {
            return 0;
        }

        return this.pageHolder.getResults().size();
    }

    public void setPage(TMDBPage initialPage) {
        if (initialPage == null) {
            this.pageHolder = new TMDBPage();
        } else {
            this.pageHolder = initialPage;
        }
        notifyDataSetChanged();
    }

    public void appendPage(TMDBPage page) {
        this.pageHolder.setPage(page.getPage());
        this.pageHolder.getResults().addAll(page.getResults());
        notifyDataSetChanged();
    }

    public void prependPage(TMDBPage page) {
        this.pageHolder.getResults().addAll(0, page.getResults());
        notifyDataSetChanged();
    }

}
