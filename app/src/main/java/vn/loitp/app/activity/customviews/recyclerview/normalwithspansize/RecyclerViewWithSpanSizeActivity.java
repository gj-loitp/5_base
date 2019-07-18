package vn.loitp.app.activity.customviews.recyclerview.normalwithspansize;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.views.LToast;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.common.Constants;

public class RecyclerViewWithSpanSizeActivity extends BaseFontActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.rv);

        mAdapter = new MoviesAdapter(getActivity(), movieList, new MoviesAdapter.Callback() {
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 5 == 0) {
                    return 1;
                }
                return 2;
            }
        });

        LUIUtil.INSTANCE.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();
    }

    private void loadMore() {
        LLog.d(getTAG(), "loadMore");
        LUIUtil.INSTANCE.setDelay(2000, () -> {
            int newSize = 5;
            for (int i = 0; i < newSize; i++) {
                Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.INSTANCE.getURL_IMG());
                movieList.add(movie);
            }
            mAdapter.notifyDataSetChanged();
            LToast.show(activity, "Finish loadMore", R.drawable.bkg_horizontal);
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
        return R.layout.activity_recycler_view;
    }

    private void prepareMovieData() {
        for (int i = 0; i < 100; i++) {
            Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.INSTANCE.getURL_IMG());
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
