package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;

public class SwipeRefreshLayoutRecyclerViewActivity extends BaseActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie) {
                ToastUtils.showShort("Click " + movie.getTitle());
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
        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                prepareMovieData();
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort("Finish refresh");
            }
        });
    }

    private void loadMore() {
        swipeRefreshLayout.setRefreshing(true);
        LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                int index = movieList.size();
                for (int i = index; i < index + 10; i++) {
                    Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i);
                    movieList.add(movie);
                }
                mAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(index);
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShort("Finish loadMore");
            }
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_swipe_refresh_recycler_view_layout;
    }

    private void prepareMovieData() {
        for (int i = 0; i < 50; i++) {
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i);
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
