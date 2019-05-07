package vn.loitp.app.activity.demo.film.groupviewpager;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.viewpager.swipeoutviewpager.SwipeOutViewPager;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGViewPager extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private TextView tv;
    private SwipeOutViewPager viewPager;
    private ViewPagerAdapter adapter;

    private List<Page> pageArrayList = new ArrayList<>();

    public interface Callback {
        public void onClickRemove();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public VGViewPager(Context context) {
        super(context);
        findViews();
    }

    public VGViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        findViews();
    }

    public VGViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        findViews();
    }

    private void findViews() {
        inflate(getContext(), R.layout.vg_view_pager, this);
        tv = (TextView) findViewById(R.id.tv);
        viewPager = (SwipeOutViewPager) findViewById(R.id.vp);
    }

    private List<Page> genData() {
        //5<=max<=10
        List<Page> pages = new ArrayList<>();
        int max = LDeviceUtil.getRandomNumber(5) + 5;
        LLog.d(TAG, "genData max " + max);
        for (int i = 0; i < max; i++) {
            Page page = new Page();
            page.setColor(LStoreUtil.getRandomColor());
            page.setName("Loitp " + i + "/" + (max));
            if (i % 2 == 0) {
                page.setUrlImg(Constants.URL_IMG_1);
            } else {
                page.setUrlImg(Constants.URL_IMG_2);
            }
            pages.add(page);
        }
        return pages;
    }

    public void init() {
        tv.setText("Hello, its me!");
        viewPager.setOnSwipeOutListener(new SwipeOutViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {
                LToast.show(getContext(), "onSwipeOutAtStart");
            }

            @Override
            public void onSwipeOutAtEnd() {
                LToast.show(getContext(), "onSwipeOutAtEnd");
            }
        });
        //LUIUtil.setPullLikeIOSHorizontal(viewPager);

        findViewById(R.id.bt_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickRemove();
                }
            }
        });

        pageArrayList.addAll(genData());
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_film_page, collection, false);
            TextView tv = (TextView) viewGroup.findViewById(R.id.tv_film);
            ImageView iv = (ImageView) viewGroup.findViewById(R.id.iv_film);

            Page page = pageArrayList.get(position);
            viewGroup.setBackgroundColor(page.getColor());
            tv.setText(page.getName());
            LImageUtil.load(getContext(), page.getUrlImg(), iv);
            //event click
            viewGroup.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LToast.show(getContext(), "Click " + page.getName());
                }
            });
            collection.addView(viewGroup);
            return viewGroup;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return pageArrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}