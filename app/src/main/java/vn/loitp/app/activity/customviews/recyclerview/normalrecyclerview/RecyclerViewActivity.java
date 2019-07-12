package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview;

import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LPopupMenu;
import com.core.utilities.LUIUtil;
import com.views.LToast;
import com.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import com.views.recyclerview.animator.animators.SlideInRightAnimator;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;

//https://github.com/wasabeef/recyclerview-animators
public class RecyclerViewActivity extends BaseFontActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private TextView tvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.rv);

        final SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        //recyclerView.getItemAnimator().setAddDuration(1000);

        tvType = findViewById(R.id.tv_type);

        findViewById(R.id.bt_add_3).setOnClickListener(v -> {
            final Movie movie = new Movie();
            movie.setTitle("Add TITLE 3");
            movie.setYear("Add YEAR 3");
            movie.setGenre("Add GENRE 3");
            movieList.add(3, movie);
            mAdapter.notifyItemInserted(3);
        });
        findViewById(R.id.bt_remove_1).setOnClickListener(v -> {
            movieList.remove(1);
            mAdapter.notifyItemRemoved(1);
        });

        mAdapter = new MoviesAdapter(getActivity(), movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getActivity(), "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                final boolean isRemoved = movieList.remove(movie);
                if (isRemoved) {
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, movieList.size());
                }
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //recyclerView.setAdapter(mAdapter);

        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //alphaAdapter.setDuration(1000);
        //alphaAdapter.setInterpolator(new OvershootInterpolator());
        //alphaAdapter.setFirstOnly(true);
        //recyclerView.setAdapter(alphaAdapter);

        final ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();

        findViewById(R.id.bt_setting).setOnClickListener(v -> LPopupMenu.INSTANCE.show(getActivity(), v,
                R.menu.menu_recycler_view,
                menuItem -> {
                    tvType.setText(menuItem.getTitle().toString());
                    switch (menuItem.getItemId()) {
                        case R.id.menu_linear_vertical:
                            final RecyclerView.LayoutManager lmVertical = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(lmVertical);
                            break;
                        case R.id.menu_linear_horizontal:
                            final RecyclerView.LayoutManager lmHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(lmHorizontal);
                            break;
                        case R.id.menu_gridlayoutmanager_2:
                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            break;
                        case R.id.menu_gridlayoutmanager_3:
                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                            break;
                        case R.id.menu_staggeredgridlayoutmanager_2:
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            break;
                        case R.id.menu_staggeredgridlayoutmanager_4:
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
                            break;
                    }
                }));
    }

    private void loadMore() {
        LLog.d(getTAG(), "loadMore");
        LUIUtil.setDelay(2000, mls -> {
            final int newSize = 5;
            for (int i = 0; i < newSize; i++) {
                final Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.INSTANCE.getURL_IMG());
                movieList.add(movie);
            }
            mAdapter.notifyDataSetChanged();
            LToast.show(getActivity(), "Finish loadMore");
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
            final Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.INSTANCE.getURL_IMG());
            movieList.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }

}
