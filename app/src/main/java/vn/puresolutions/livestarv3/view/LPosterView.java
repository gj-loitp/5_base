package vn.puresolutions.livestarv3.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.puresolutions.livestarv3.activity.homescreen.HomeMainActivity;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolposter.ModelIdolPoster;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LPosterView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private ViewPager viewPager;
    private LViewPagerIndicator lViewPagerIndicator;
    private TextView tvTitle;
    private Activity mActivity;
    private ModelIdolPoster mModelIdolPoster;

    public LPosterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LPosterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_poster, this);

        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.lViewPagerIndicator = (LViewPagerIndicator) findViewById(R.id.l_view_pager_indicator);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    private int currentPage = 0;

    private Handler handler = new Handler();

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private boolean isDrag;

    public void switchPage(final int maxPage) {
        //dangerous to use LUIUtils.setDelay here
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mActivity instanceof HomeMainActivity) {
                    boolean isPausedActivity = ((HomeMainActivity) mActivity).isPausedActivity();
                    if (!isPausedActivity) {
                        if (viewPager != null) {
                            if (!isDrag) {
                                currentPage++;
                                if (currentPage == maxPage) {
                                    currentPage = 0;
                                }
                                viewPager.setCurrentItem(currentPage);
                            } else {
                                LLog.d(TAG, "dont auto scroll because user is dragging");
                            }
                            switchPage(maxPage);
                        }
                    } else {
                        switchPage(maxPage);
                    }
                }
            }
        }, 7000);
    }

    public void setData(Activity activity, ModelIdolPoster modelIdolPoster) {
        this.mActivity = activity;
        this.mModelIdolPoster = modelIdolPoster;

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mModelIdolPoster.getGetPosterArrayList());
        viewPager.setAdapter(viewPagerAdapter);
        lViewPagerIndicator.setMaxDot(mModelIdolPoster.getGetPosterArrayList().size());
        tvTitle.setText(R.string.fall_in_love_with_hot_girl_in_vietnam);
        lViewPagerIndicator.setPosition(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentPage(position);
                lViewPagerIndicator.setPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == LOnlyPageViewPager.SCROLL_STATE_DRAGGING) {
                    LLog.d(TAG, "SCROLL_STATE_DRAGGING");
                    isDrag = true;
                } else if (state == LOnlyPageViewPager.SCROLL_STATE_IDLE) {
                    LLog.d(TAG, "SCROLL_STATE_IDLE");
                    isDrag = false;
                }
            }
        });
        switchPage(mModelIdolPoster.getGetPosterArrayList().size());
    }

    private class ViewPagerAdapter extends PagerAdapter {
        private ArrayList<GetPoster> getPosterArrayList;

        public ViewPagerAdapter(ArrayList<GetPoster> getPosterArrayList) {
            this.getPosterArrayList = getPosterArrayList;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, final int position) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_image_view, collection, false);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LAnimationUtil.play(v, Techniques.Pulse);
                    //do nothing
                }
            });
            SimpleDraweeView imageView = (SimpleDraweeView) layout.findViewById(R.id.iv);
            LImageUtils.loadImage(imageView, getPosterArrayList.get(position).getImagesPath().getW720h415());
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            if (getPosterArrayList == null) {
                return 0;
            }
            return getPosterArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}