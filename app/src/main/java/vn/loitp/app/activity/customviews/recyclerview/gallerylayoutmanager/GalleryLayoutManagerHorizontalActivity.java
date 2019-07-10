package vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData;
import vn.loitp.app.common.Constants;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.gallery.GalleryLayoutManager;

//https://github.com/BCsl/GalleryLayoutManager
public class GalleryLayoutManagerHorizontalActivity extends BaseFontActivity {
    private RecyclerView recyclerView;
    private GalleryAdapter mAdapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        tv = (TextView) findViewById(R.id.tv);

        mAdapter = new GalleryAdapter(activity, DummyData.getInstance().getMovieList(), new GalleryAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.INSTANCE.show(activity, "Click " + movie.getTitle());
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

        layoutManager.setCallbackInFling(true);//should receive callback when flinging, default is false
        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                tv.setText(position + "/" + mAdapter.getItemCount());
            }
        });

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
        if (DummyData.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 50; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.INSTANCE.getURL_IMG());
                DummyData.getInstance().getMovieList().add(movie);
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
