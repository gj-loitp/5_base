package vn.loitp.app.activity.read;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.helper.pagelist.GetReadImgTask;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.viewpager.parrallaxviewpager.lib.parrallaxviewpager.Mode;
import vn.loitp.views.viewpager.parrallaxviewpager.lib.parrallaxviewpager.ParallaxViewPager;

public class ReadActivity extends BaseActivity {
    private ImageView ivBkg;
    private AVLoadingIndicatorView avi;
    private ParallaxViewPager parallaxViewPager;
    private GetReadImgTask getReadImgTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parallaxViewPager = (ParallaxViewPager) findViewById(R.id.viewpager);
        ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        parallaxViewPager.setMode(Mode.RIGHT_OVERLAY);

        parallaxViewPager.setAdapter(new SlidePagerAdapter());
        parallaxViewPager.setOffscreenPageLimit(3);
        LUIUtil.setPullLikeIOSHorizontal(parallaxViewPager);

        load("http://truyentranhtuan.com/one-piece-chuong-69/");
    }

    private void load(String link) {
        getReadImgTask = new GetReadImgTask(link, avi, new GetReadImgTask.Callback() {
            @Override
            public void onSuccess(List<String> imagesListOfOneChap) {
                LLog.d(TAG, "load onSuccess GetReadImgTask " + LSApplication.getInstance().getGson().toJson(imagesListOfOneChap));
            }

            @Override
            public void onError() {
                ToastUtils.showShort("load onError");
            }
        });
        if (getReadImgTask != null) {
            getReadImgTask.execute();
        }
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_read_slide;
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_strech_iv, collection, false);

            ScrollView scrollView = (ScrollView) layout.findViewById(R.id.scroll_view);
            if (scrollView != null) {
                LUIUtil.setPullLikeIOSVertical(scrollView);
            }

            AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView) layout.findViewById(R.id.avi);
            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.load(activity, "https://kenh14cdn.com/2018/84a2b164b59e12659b70fc4adfbd7a33-1516711720908.jpg", imageView, avLoadingIndicatorView);

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    protected void onDestroy() {
        if (getReadImgTask != null) {
            getReadImgTask.cancel(true);
        }
        super.onDestroy();
    }
}
