package vn.loitp.app.activity.customviews.recyclerview.bookview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LImageUtil;
import com.core.utilities.LScreenUtil;
import com.core.utilities.LUIUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MovieViewHolder> {
    private final String TAG = getClass().getSimpleName();
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface Callback {
        void onClick(Movie movie, int position);

        void onLongClick(Movie movie, int position);

        void onLoadMore();
    }

    void checkData() {
        int indexEachColumn = getItemCount() % column;
        int missItemCount = column - indexEachColumn;
        //LLog.d(TAG, "checkData missItemCount: " + missItemCount);
        if (missItemCount != column) {
            //LLog.d(TAG, ">>>>>>>>>>>need to add: " + missItemCount);
            for (int i = 0; i < missItemCount; i++) {
                Movie movie = new Movie();
                moviesList.add(movie);
                //LLog.d(TAG, "add " + i);
            }
            notifyDataSetChanged();
        }
    }

    private Context context;
    private Callback callback;
    private List<Movie> moviesList;
    private int column;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ImageView iv;
        public ImageView bkg;
        public RelativeLayout rootView;

        public MovieViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.tv);
            iv = view.findViewById(R.id.imageView);
            bkg = view.findViewById(R.id.bkg);
            rootView = view.findViewById(R.id.root_view);
        }
    }

    private int sizeW;
    private int sizeH;
    private int sizeMarginTopBottom;
    private int sizeMarginTopLeftRight;

    public BookAdapter(Context context, int column, List<Movie> moviesList, Callback callback) {
        this.context = context;
        this.column = column;
        this.moviesList = moviesList;
        this.callback = callback;
        this.sizeW = LScreenUtil.INSTANCE.getScreenWidth() / column;
        this.sizeH = sizeW * 15 / 9;
        this.sizeMarginTopBottom = sizeW / 5;
        this.sizeMarginTopLeftRight = sizeW / 10;
    }

    @NotNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_view, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.rootView.getLayoutParams().width = sizeW;
        holder.rootView.getLayoutParams().height = sizeH;
        holder.rootView.invalidate();

        if (movie == null) {
            return;
        }

        int indexEachColumn = position % column;
        holder.tv.setText(movie.getTitle());
        holder.tv.setVisibility(View.VISIBLE);
        //holder.tv.setText(indexEachColumn + "");
        if (indexEachColumn == 0) {
            holder.bkg.setImageResource(R.drawable.l_grid_item_background_left);
        } else if (indexEachColumn == (column - 1)) {
            holder.bkg.setImageResource(R.drawable.l_grid_item_background_right);
        } else {
            holder.bkg.setImageResource(R.drawable.l_grid_item_background_center);
        }

        LUIUtil.INSTANCE.setMargins(holder.iv, sizeMarginTopLeftRight, sizeMarginTopBottom, sizeMarginTopLeftRight, sizeMarginTopBottom);
        String url = movie.getCover();
        if (url != null) {
            LImageUtil.INSTANCE.load(context, url, holder.iv);
        } else {
            holder.iv.setImageResource(0);
            holder.tv.setVisibility(View.INVISIBLE);
        }

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