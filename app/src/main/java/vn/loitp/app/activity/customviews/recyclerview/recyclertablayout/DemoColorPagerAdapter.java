package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;

public class DemoColorPagerAdapter extends PagerAdapter {

    private List<ColorItem> mItems = new ArrayList<>();

    public DemoColorPagerAdapter() {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = view.findViewById(R.id.title);
        textView.setText("Page: " + mItems.get(position).hex);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return mItems.get(position).name;
    }

    public ColorItem getColorItem(int position) {
        return mItems.get(position);
    }

    public void addAll(List<ColorItem> items) {
        mItems = new ArrayList<>(items);
    }
}
