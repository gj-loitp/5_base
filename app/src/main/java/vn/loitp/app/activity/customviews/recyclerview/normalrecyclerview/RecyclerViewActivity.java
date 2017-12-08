package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;

public class RecyclerViewActivity extends BaseActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie) {
                ToastUtils.showShort("Click " + movie.getTitle());
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

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
    }

    private void loadMore() {
        LLog.d(TAG, "loadMore");
        LUIUtil.setDelay(2000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                int newSize = 5;
                for (int i = 0; i < newSize; i++) {
                    Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i);
                    movieList.add(movie);
                }
                mAdapter.notifyDataSetChanged();
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
        return R.layout.activity_recycler_view;
    }

    private void prepareMovieData() {
        for (int i = 0; i < 100; i++) {
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i);
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
