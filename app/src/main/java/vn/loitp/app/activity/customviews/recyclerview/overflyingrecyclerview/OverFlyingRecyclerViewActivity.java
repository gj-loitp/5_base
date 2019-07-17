package vn.loitp.app.activity.customviews.recyclerview.overflyingrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.views.recyclerview.banner.layoutmanager.CenterScrollListener;
import com.views.recyclerview.banner.layoutmanager.OverFlyingLayoutManager;

import loitp.basemaster.R;

public class OverFlyingRecyclerViewActivity extends BaseFontActivity {
    private RecyclerView recyclerView;
    private OverFlyingLayoutManager mOverFlyingLayoutManager;
    private Handler mHandler;
    private Runnable mRunnable;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_banner);
        mOverFlyingLayoutManager = new OverFlyingLayoutManager(0.75f, 385, OverFlyingLayoutManager.HORIZONTAL);

        recyclerView.setAdapter(new LocalDataAdapter());
        recyclerView.setLayoutManager(mOverFlyingLayoutManager);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        mOverFlyingLayoutManager.setOnPageChangeListener(new OverFlyingLayoutManager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                currentPosition++;
                mOverFlyingLayoutManager.scrollToPosition(currentPosition);
                //  recyclerView.smoothScrollToPosition(currentPosition);
                mHandler.postDelayed(this, 3000);
            }
        };
        mHandler.postDelayed(mRunnable, 3000);

        SeekBar seekBar = (SeekBar) findViewById(R.id.progress);
        seekBar.setProgress(385);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mOverFlyingLayoutManager.setItemSpace(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        return R.layout.activity_overflying_recyclerview;
    }

}
