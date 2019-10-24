package vn.loitp.app.activity.customviews.recyclerview.bookview;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.views.LToast;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.common.Constants;

public class BookViewActivity extends BaseFontActivity {
    private List<Movie> movieList = new ArrayList<>();
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.rv);

        /*findViewById(R.id.bt_add_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = new Movie();
                movie.setTitle("Add TITLE 3");
                movie.setYear("Add YEAR 3");
                movie.setGenre("Add GENRE 3");
                movieList.add(3, movie);
                mAdapter.notifyItemInserted(3);
            }
        });
        findViewById(R.id.bt_remove_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieList.remove(1);
                mAdapter.notifyItemRemoved(1);
            }
        });*/

        int COLUMN = 3;
        mAdapter = new BookAdapter(getActivity(), COLUMN, movieList, new BookAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getActivity(), "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                boolean isRemoved = movieList.remove(movie);
                if (isRemoved) {
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, movieList.size());
                    mAdapter.checkData();
                }
            }

            @Override
            public void onLoadMore() {
                //loadMore();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN));
        recyclerView.setAdapter(mAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_bookview;
    }

    /*private void loadMore() {
        LLog.d(TAG, "loadMore");
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                int newSize = 5;
                for (int i = 0; i < newSize; i++) {
                    Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.URL_IMG);
                    movieList.add(movie);
                }
                mAdapter.notifyDataSetChanged();
                LToast.show(activity, "Finish loadMore");
            }
        });
    }*/

    private void prepareMovieData() {
        String cover;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                cover = Constants.INSTANCE.getURL_IMG_1();
            } else {
                cover = Constants.INSTANCE.getURL_IMG_2();
            }
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, cover);
            movieList.add(movie);
        }
        mAdapter.checkData();
        mAdapter.notifyDataSetChanged();
    }
}
