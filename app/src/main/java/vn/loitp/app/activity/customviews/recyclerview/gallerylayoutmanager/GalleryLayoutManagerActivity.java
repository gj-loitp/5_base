package vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.gallery.GalleryLayoutManager;

//https://github.com/BCsl/GalleryLayoutManager
public class GalleryLayoutManagerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        mAdapter = new GalleryAdapter(activity, DummyData.getInstance().getMovieList(), new GalleryAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(activity, "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                //do nothing
            }

            @Override
            public void onLoadMore() {
                //do nothing
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(mAdapter);

        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager.attach(recyclerView);  //default selected position is 0
        //layoutManager.attach(recyclerView, 30);

        //...
        //setup adapter for your RecycleView
        recyclerView.setAdapter(mAdapter);

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
        return R.layout.activity_menu_gallery_layout_manager;
    }

    private void prepareMovieData() {
        if (DummyData.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 50; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.URL_IMG);
                DummyData.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
