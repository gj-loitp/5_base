package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.animator.adapters.AlphaInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInLeftAnimator;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

//https://github.com/wasabeef/recyclerview-animators

public class RecyclerViewActivity extends BaseActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private TextView tvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        //recyclerView.getItemAnimator().setAddDuration(1000);

        tvType = (TextView) findViewById(R.id.tv_type);

        findViewById(R.id.bt_add_3).setOnClickListener(new View.OnClickListener() {
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
        });

        mAdapter = new MoviesAdapter(activity, movieList, new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(activity, "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                boolean isRemoved = movieList.remove(movie);
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //recyclerView.setAdapter(mAdapter);

        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //alphaAdapter.setDuration(1000);
        //alphaAdapter.setInterpolator(new OvershootInterpolator());
        //alphaAdapter.setFirstOnly(true);
        //recyclerView.setAdapter(alphaAdapter);

        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();

        findViewById(R.id.bt_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LPopupMenu.show(activity, v, R.menu.menu_recycler_view, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        tvType.setText(menuItem.getTitle().toString());
                        switch (menuItem.getItemId()) {
                            case R.id.menu_linear_vertical:
                                RecyclerView.LayoutManager lmVertical = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(lmVertical);
                                break;
                            case R.id.menu_linear_horizontal:
                                RecyclerView.LayoutManager lmHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(lmHorizontal);
                                break;
                            case R.id.menu_gridlayoutmanager_2:
                                recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
                                break;
                            case R.id.menu_gridlayoutmanager_3:
                                recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
                                break;
                            case R.id.menu_staggeredgridlayoutmanager_2:
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                                break;
                            case R.id.menu_staggeredgridlayoutmanager_4:
                                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
                                break;
                        }
                    }
                });
            }
        });
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
                LToast.show(activity, "Finish loadMore");
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
