package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
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

@LayoutId(R.layout.activity_swipe_refresh_recycler_view_layout)
@LogTag("SwipeRefreshLayoutRecyclerViewActivity")
@IsFullScreen(false)
public class SwipeRefreshLayoutRecyclerViewActivity extends BaseFontActivity {
    private SwipeRefreshLayout swipe_refresh_layout;
    private List<Movie> movieList = new ArrayList<>();
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipe_refresh_layout = findViewById(R.id.swipeRefreshLayout);
        swipe_refresh_layout.setOnRefreshListener(this::refresh);
        LUIUtil.Companion.setColorForSwipeRefreshLayout(swipe_refresh_layout);

        RecyclerView rv = findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(@NotNull Movie movie, int position) {
                showShort("Click " + movie.getTitle(), true);
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
            swipe_refresh_layout.setRefreshing(false);
            showShort("Finish refresh", true);
        });
    }

    private void loadMore() {
        swipe_refresh_layout.setRefreshing(true);
        LUIUtil.Companion.setDelay(2000, () -> {
            swipe_refresh_layout.setRefreshing(false);
            int newSize = 5;
            for (int i = 0; i < newSize; i++) {
                Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.Companion.getURL_IMG());
                movieList.add(movie);
            }
            mAdapter.notifyDataSetChanged();
            showShort("Finish loadMore", true);
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
