package vn.loitp.app.activity.customviews.recyclerview.bookview;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MovieViewHolder> {
    private final String TAG = getClass().getSimpleName();
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface Callback {
        public void onClick(Movie movie, int position);

        public void onLongClick(Movie movie, int position);

        public void onLoadMore();
    }

    public void checkData() {
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
            tv = (TextView) view.findViewById(R.id.tv);
            iv = (ImageView) view.findViewById(R.id.imageView);
            bkg = (ImageView) view.findViewById(R.id.bkg);
            rootView = (RelativeLayout) view.findViewById(R.id.root_view);
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
        this.sizeW = LScreenUtil.getScreenWidth() / column;
        this.sizeH = sizeW * 15 / 9;
        this.sizeMarginTopBottom = sizeW / 5;
        this.sizeMarginTopLeftRight = sizeW / 10;
    }

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

        int indexEachColumn = position % column;
        holder.tv.setText(movie.getTitle());
        //holder.tv.setText(indexEachColumn + "");
        if (indexEachColumn == 0) {
            holder.bkg.setImageResource(R.drawable.grid_item_background_left);
        } else if (indexEachColumn == (column - 1)) {
            holder.bkg.setImageResource(R.drawable.grid_item_background_right);
        } else {
            holder.bkg.setImageResource(R.drawable.grid_item_background_center);
        }

        LUIUtil.setMargins(holder.iv, sizeMarginTopLeftRight, sizeMarginTopBottom, sizeMarginTopLeftRight, sizeMarginTopBottom);
        LImageUtil.load(context, movie.getCover(), holder.iv);

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
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}