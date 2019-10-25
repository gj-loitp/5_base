package vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.views.LToast;
import com.views.recyclerview.gallery.GalleryLayoutManager;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData;
import vn.loitp.app.common.Constants;

//https://github.com/BCsl/GalleryLayoutManager
public class GalleryLayoutManagerVerticalActivity extends BaseFontActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private GalleryAdapterVertical mAdapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.rv);
        recyclerView1 = findViewById(R.id.rv_1);
        tv = findViewById(R.id.tv);

        mAdapter = new GalleryAdapterVertical(getActivity(), DummyData.Companion.getInstance().getMovieList(), new GalleryAdapterVertical.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getActivity(), "onClick " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                LToast.show(getActivity(), "onLongClick " + movie.getTitle());
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

        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.VERTICAL);
        //layoutManager.attach(recyclerView);  //default selected position is 0
        layoutManager.attach(recyclerView, 5);

        //...
        //setup adapter for your RecycleView
        recyclerView.setAdapter(mAdapter);

        layoutManager.setCallbackInFling(true);//should receive callback when flinging, default is false
        layoutManager.setOnItemSelectedListener((recyclerView, item, position) -> tv.setText(position + "/" + mAdapter.getItemCount()));

        // Apply ItemTransformer just like ViewPager
        layoutManager.setItemTransformer(new ScaleTransformer());

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
        if (DummyData.Companion.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 50; i++) {
                Movie movie = new Movie("Menu " + i, "Action & Adventure " + i, "Year: " + i, Constants.INSTANCE.getURL_IMG());
                DummyData.Companion.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {

        @Override
        public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
            item.setPivotX(item.getWidth() / 2.f);
            item.setPivotY(item.getHeight() / 2.0f);
            float scale = 1 - 0.4f * Math.abs(fraction);
            item.setScaleX(scale);
            item.setScaleY(scale);
        }
    }
}
