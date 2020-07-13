package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.common.Constants;

public class SwipeRefreshLayoutRecyclerViewActivity extends BaseFontActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
        LUIUtil.Companion.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        recyclerView = findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                showShort("Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {

            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
    }

    private void refresh() {
        movieList.clear();
        mAdapter.notifyDataSetChanged();
        LUIUtil.Companion.setDelay(3000, () -> {
            prepareMovieData();
            swipeRefreshLayout.setRefreshing(false);
            showShort("Finish refresh");
        });
    }

    private void loadMore() {
        LLog.d(getTAG(), "loadMore");
        swipeRefreshLayout.setRefreshing(true);
        LUIUtil.Companion.setDelay(2000, () -> {
            swipeRefreshLayout.setRefreshing(false);
            int newSize = 5;
            for (int i = 0; i < newSize; i++) {
                Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.Companion.getURL_IMG());
                movieList.add(movie);
            }
            mAdapter.notifyDataSetChanged();
            showShort("Finish loadMore");
        });
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
        return R.layout.activity_swipe_refresh_recycler_view_layout;
    }

    private void prepareMovieData() {
        for (int i = 0; i < 50; i++) {
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.Companion.getURL_IMG());
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
