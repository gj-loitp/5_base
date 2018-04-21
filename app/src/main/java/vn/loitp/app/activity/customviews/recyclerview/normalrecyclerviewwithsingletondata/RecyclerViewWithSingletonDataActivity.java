package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

public class RecyclerViewWithSingletonDataActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private TextView tvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        tvType = (TextView) findViewById(R.id.tv_type);

        mAdapter = new MoviesAdapter(DummyData.getInstance().getMovieList(), new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(activity, "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                boolean isRemoved = DummyData.getInstance().getMovieList().remove(movie);
                if (isRemoved) {
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, DummyData.getInstance().getMovieList().size());
                }
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
                    DummyData.getInstance().getMovieList().add(movie);
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
        if (DummyData.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i);
                DummyData.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
