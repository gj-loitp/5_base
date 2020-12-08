package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.common.Constants;

@LogTag("SwipeRefreshLayoutRecyclerViewActivity")
@IsFullScreen(false)
//TODO convert kotlin
public class SwipeRefreshLayoutRecyclerViewActivity extends BaseFontActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private final List<Movie> movieList = new ArrayList<>();
    private MoviesAdapter mAdapter;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_swipe_refresh_recycler_view_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
        LUIUtil.Companion.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        RecyclerView rv = findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(@NotNull Movie movie, int position) {
                showShortInformation("Click " + movie.getTitle(), true);
            }

            @Override
            public void onLongClick(@NotNull Movie movie, int position) {

            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
    }

    private void refresh() {
        movieList.clear();
        mAdapter.notifyDataSetChanged();
        LUIUtil.Companion.setDelay(3000, () -> {
            prepareMovieData();
            swipeRefreshLayout.setRefreshing(false);
            showShortInformation("Finish refresh", true);
        });
    }

    private void loadMore() {
        swipeRefreshLayout.setRefreshing(true);
        LUIUtil.Companion.setDelay(2000, () -> {
            swipeRefreshLayout.setRefreshing(false);
            int newSize = 5;
            for (int i = 0; i < newSize; i++) {
                Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.Companion.getURL_IMG());
                movieList.add(movie);
            }
            mAdapter.notifyDataSetChanged();
            showShortInformation("Finish loadMore", true);
        });
    }

    private void prepareMovieData() {
        for (int i = 0; i < 50; i++) {
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.Companion.getURL_IMG());
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
