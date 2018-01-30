package vn.loitp.app.activity.read;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.ComicInfoData;
import vn.loitp.app.helper.pagelist.GetReadImgTask;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.imageview.touchimageview.lib.LTouchImageView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class ReadActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBkg;
    private AVLoadingIndicatorView avi;
    private ViewPager viewPager;
    private GetReadImgTask getReadImgTask;
    private ImageView btPrevChap;
    private ImageView btNextChap;
    private List<String> imagesListOfOneChap = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ComicInfoData.getInstance().getTttChap() == null) {
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        try {
            LImageUtil.load(activity, ComicInfoData.getInstance().getTttChap().getInfo().getCover(), ivBkg);
        } catch (NullPointerException e) {
            LLog.e(TAG, "set cover NullPointerException " + e.toString());
        }

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        btPrevChap = (ImageView) findViewById(R.id.bt_prev_chap);
        btNextChap = (ImageView) findViewById(R.id.bt_next_chap);
        btPrevChap.setOnClickListener(this);
        btNextChap.setOnClickListener(this);

        LUIUtil.setImageFromAsset(activity, Constants.ASSET_PREV_CHAP, btPrevChap);
        LUIUtil.setImageFromAsset(activity, Constants.ASSET_NEXT_CHAP, btNextChap);

        viewPager.setAdapter(new SlidePagerAdapter());
        viewPager.setOffscreenPageLimit(3);
        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btPrevChap.setVisibility(View.VISIBLE);
                    LAnimationUtil.play(btPrevChap, Techniques.SlideInRight);
                } else if (position == imagesListOfOneChap.size() - 1) {
                    btNextChap.setVisibility(View.VISIBLE);
                    LAnimationUtil.play(btNextChap, Techniques.SlideInLeft);
                } else {
                    if (btPrevChap.getVisibility() != View.GONE) {
                        LAnimationUtil.play(btPrevChap, Techniques.SlideOutRight, new LAnimationUtil.Callback() {
                            @Override
                            public void onCancel() {
                                //do nothing
                            }

                            @Override
                            public void onEnd() {
                                btPrevChap.setVisibility(View.GONE);
                            }

                            @Override
                            public void onRepeat() {
                                //do nothing
                            }

                            @Override
                            public void onStart() {
                                //do nothing
                            }
                        });
                    }
                    if (btNextChap.getVisibility() != View.GONE) {
                        LAnimationUtil.play(btNextChap, Techniques.SlideOutLeft, new LAnimationUtil.Callback() {
                            @Override
                            public void onCancel() {
                                //do nothing
                            }

                            @Override
                            public void onEnd() {
                                btNextChap.setVisibility(View.GONE);
                            }

                            @Override
                            public void onRepeat() {
                                //do nothing
                            }

                            @Override
                            public void onStart() {
                                //do nothing
                            }
                        });
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });

        load(ComicInfoData.getInstance().getCurrentLinkChap());
    }

    private void load(String link) {
        LLog.d(TAG, "load link " + link);
        getReadImgTask = new GetReadImgTask(link, avi, new GetReadImgTask.Callback() {
            @Override
            public void onSuccess(List<String> stringList) {
                LLog.d(TAG, "load onSuccess GetReadImgTask " + LSApplication.getInstance().getGson().toJson(stringList));
                imagesListOfOneChap = stringList;
                viewPager.getAdapter().notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_prev_chap:
                //TODO
                break;
            case R.id.bt_next_chap:
                //TODO
                break;
        }
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_slide_touch_iv, collection, false);

            if (position >= 0 && position < imagesListOfOneChap.size()) {
                //ScrollView scrollView = (ScrollView) layout.findViewById(R.id.scroll_view);
                //if (scrollView != null) {
                //    LUIUtil.setPullLikeIOSVertical(scrollView);
                //}

                AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView) layout.findViewById(R.id.avi);
                LTouchImageView imageView = (LTouchImageView) layout.findViewById(R.id.imageView);
                LLog.d(TAG, ">instantiateItem: " + imagesListOfOneChap.get(position));
                LImageUtil.load(activity, imagesListOfOneChap.get(position), imageView, avLoadingIndicatorView, R.color.transparent);
            } else {
                LLog.e(TAG, "SlidePagerAdapter instantiateItem with incorrect position " + position);
            }
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return imagesListOfOneChap == null ? 0 : imagesListOfOneChap.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
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
