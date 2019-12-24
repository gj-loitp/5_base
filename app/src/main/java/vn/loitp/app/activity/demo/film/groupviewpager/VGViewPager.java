package vn.loitp.app.activity.demo.film.groupviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.core.common.Constants;
import com.core.utilities.LDeviceUtil;
import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.core.utilities.LStoreUtil;
import com.views.LToast;
import com.views.viewpager.swipeout.LSwipeOutViewPager;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGViewPager extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private TextView tv;
    private LSwipeOutViewPager viewPager;

    private List<Page> pageArrayList = new ArrayList<>();

    public interface Callback {
        void onClickRemove();
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
        tv = findViewById(R.id.tv);
        viewPager = findViewById(R.id.vp);
    }

    private List<Page> genData() {
        //5<=max<=10
        final List<Page> pages = new ArrayList<>();
        final int max = LDeviceUtil.INSTANCE.getRandomNumber(5) + 5;
        LLog.d(TAG, "genData max " + max);
        for (int i = 0; i < max; i++) {
            final Page page = new Page();
            page.setColor(LStoreUtil.INSTANCE.getRandomColor());
            page.setName("genData " + i + "/" + (max));
            if (i % 2 == 0) {
                page.setUrlImg(Constants.INSTANCE.getURL_IMG_1());
            } else {
                page.setUrlImg(Constants.INSTANCE.getURL_IMG_2());
            }
            pages.add(page);
        }
        return pages;
    }

    public void init() {
        tv.setText("Hello, its me!");
        viewPager.setOnSwipeOutListener(new LSwipeOutViewPager.OnSwipeOutListener() {
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

        findViewById(R.id.bt_remove).setOnClickListener(v -> {
            if (callback != null) {
                callback.onClickRemove();
            }
        });

        pageArrayList.addAll(genData());
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_film_page, collection, false);
            final TextView tv = viewGroup.findViewById(R.id.tv_film);
            final ImageView iv = viewGroup.findViewById(R.id.iv_film);

            final Page page = pageArrayList.get(position);
            viewGroup.setBackgroundColor(page.getColor());
            tv.setText(page.getName());
            LImageUtil.INSTANCE.load(getContext(), page.getUrlImg(), iv);
            //event click
            viewGroup.setOnClickListener(v -> LToast.show(getContext(), "Click " + page.getName()));
            collection.addView(viewGroup);
            return viewGroup;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return pageArrayList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
