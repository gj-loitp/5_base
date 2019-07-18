package vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.views.placeholderview.lib.placeholderview.ExpandablePlaceHolderView;
import com.views.placeholderview.lib.placeholderview.PlaceHolderView;

import loitp.basemaster.R;

public class AndroidExpandableNewsFreedActivity extends BaseFontActivity {
    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;
    private ExpandablePlaceHolderView.OnScrollListener mOnScrollListener;
    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        mExpandableView = (ExpandablePlaceHolderView) findViewById(R.id.expandableView);

        LUIUtil.INSTANCE.setPullLikeIOSVertical(mExpandableView);

        for (Feed feed : Utils.loadFeeds(this.getApplicationContext())) {
            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
            for (Info info : feed.getInfoList()) {
                mExpandableView.addView(new InfoView(mContext, info));
            }
        }
        setLoadMoreListener(mExpandableView);
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
        return R.layout.activity_android_expandable_news_feed;
    }

    private void setLoadMoreListener(ExpandablePlaceHolderView expandableView) {
        mOnScrollListener =
                new PlaceHolderView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        ExpandablePlaceHolderView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int totalItemCount = linearLayoutManager.getItemCount();
                            int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (!mIsLoadingMore
                                    && !mNoMoreToLoad
                                    && totalItemCount > 0
                                    && totalItemCount == lastVisibleItem + 1) {
                                mIsLoadingMore = true;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // do api call to fetch data
                                        // example of loading from file:
                                        for (Feed feed : Utils.loadFeeds(getApplicationContext())) {
                                            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
                                            for (Info info : feed.getInfoList()) {
                                                mExpandableView.addView(new InfoView(mContext, info));
                                            }
                                        }
                                        mIsLoadingMore = false;
                                    }
                                });
                            }
                        }
                    }
                };
        expandableView.addOnScrollListener(mOnScrollListener);
    }
}
