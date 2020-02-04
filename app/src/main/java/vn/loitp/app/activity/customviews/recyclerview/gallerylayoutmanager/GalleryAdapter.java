package vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MovieViewHolder> {
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface Callback {
        void onClick(Movie movie, int position);

        void onLongClick(Movie movie, int position);

        void onLoadMore();
    }

    private Context context;
    private Callback callback;
    private List<Movie> moviesList;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rootView;
        public ImageView imageView;

        MovieViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            rootView = view.findViewById(R.id.root_view);
        }
    }

    GalleryAdapter(Context context, List<Movie> moviesList, Callback callback) {
        this.context = context;
        this.moviesList = moviesList;
        this.callback = callback;
    }

    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        LImageUtil.INSTANCE.load(context, movie.getCover(), holder.imageView);

        holder.rootView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(movie, position);
            }
        });
        holder.rootView.setOnLongClickListener(v -> {
            if (callback != null) {
                callback.onLongClick(movie, position);
            }
            return true;
        });
        if (position == moviesList.size() - 1) {
            if (callback != null) {
                callback.onLoadMore();
            }
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}