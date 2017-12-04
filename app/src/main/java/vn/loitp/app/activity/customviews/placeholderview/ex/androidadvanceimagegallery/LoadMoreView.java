package vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.placeholderview.lib.placeholderview.InfinitePlaceHolderView;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Layout;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.infinite.LoadMore;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@Layout(R.layout.load_more_view)
public class LoadMoreView {
    public static final int LOAD_VIEW_SET_COUNT = 5;
    private InfinitePlaceHolderView mLoadMoreView;
    private List<Image> mFeedList;

    public LoadMoreView(InfinitePlaceHolderView loadMoreView, List<Image> feedList) {
        this.mLoadMoreView = loadMoreView;
        this.mFeedList = feedList;
        LLog.d("DEBUG", ">>>LoadMoreView " + mFeedList.size());
    }

    @LoadMore
    private void onLoadMore() {
        Log.d("DEBUG", ">>>onLoadMore");
        new ForcedWaitedLoading();
    }

    class ForcedWaitedLoading implements Runnable {

        public ForcedWaitedLoading() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    int count = mLoadMoreView.getViewCount();
                    LLog.d("DEBUG", ">>>before count " + count);
                    for (int i = count - 1; i < (count - 1 + LoadMoreView.LOAD_VIEW_SET_COUNT) && mFeedList.size() > i; i++) {
                        mLoadMoreView.addView(new ImageTypeBig(mLoadMoreView.getContext(), mLoadMoreView, mFeedList.get(i).getImageUrl()));
                        if (i == mFeedList.size() - 1) {
                            mLoadMoreView.noMoreToLoad();
                            break;
                        }
                    }
                    mLoadMoreView.loadingDone();
                    LLog.d("DEBUG", ">>>loadingDone " + mLoadMoreView.getViewCount());
                }
            });
        }
    }
}