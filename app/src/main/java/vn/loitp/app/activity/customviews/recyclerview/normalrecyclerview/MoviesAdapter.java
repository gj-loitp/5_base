package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import loitp.basemaster.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface Callback {
        public void onClick(Movie movie, int position);

        public void onLongClick(Movie movie, int position);

        public void onLoadMore();
    }

    private Context context;
    private Callback callback;
    private List<Movie> moviesList;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public LinearLayout rootView;

        public MovieViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            rootView = (LinearLayout) view.findViewById(R.id.root_view);
        }

        public void clearAnimation() {
            rootView.clearAnimation();
        }
    }


    public MoviesAdapter(Context context, List<Movie> moviesList, Callback callback) {
        this.context = context;
        this.moviesList = moviesList;
        this.callback = callback;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(movie, position);
                }
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(movie, position);
                }
                return true;
            }
        });
        if (position == moviesList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            //Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_enter);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MovieViewHolder holder) {
        //super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }
}