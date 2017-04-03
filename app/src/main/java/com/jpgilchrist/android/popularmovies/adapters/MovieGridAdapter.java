package com.jpgilchrist.android.popularmovies.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jpgilchrist.android.popularmovies.R;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBPage;
import com.jpgilchrist.android.popularmovies.tmdb.TMDBUtils;
import com.squareup.picasso.Picasso;

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

    private List<TMDBPage.TMDBResult> data = new LinkedList<>();

    final private OnClickHandler onClickHandler;

    public interface OnClickHandler {
        void onClick(TMDBPage.TMDBResult result);
    }

    public MovieGridAdapter(OnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    /**
     * ViewHolder for the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView posterImageView;

        /**
         * Constructor for the ViewHolder - sets any references to items in the view for later use
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.movie_grid_item_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            TMDBPage.TMDBResult tmdbResult = data.get(adapterPosition);
            onClickHandler.onClick(tmdbResult);
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
        Uri posterPathUri = TMDBUtils.buildPosterPathUri(data.get(position).getPoster_path(), TMDBUtils.ImageSize.W342);
        Picasso.with(holder.itemView.getContext())
                .load(posterPathUri)
                .into(holder.posterImageView);
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

    public void setData(List<TMDBPage.TMDBResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void reset() {
        this.data.clear();
        notifyDataSetChanged();
    }
}
