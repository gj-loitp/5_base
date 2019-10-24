package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.views.recyclerview.recyclertablayout.RecyclerTabLayout;

import org.jetbrains.annotations.NotNull;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoImagePagerAdapter;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoCustomView02Adapter
        extends RecyclerTabLayout.Adapter<DemoCustomView02Adapter.ViewHolder> {

    private DemoImagePagerAdapter mAdapater;

    DemoCustomView02Adapter(ViewPager viewPager) {
        super(viewPager);
        mAdapater = (DemoImagePagerAdapter) mViewPager.getAdapter();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_custom_view02_tab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drawable drawable = loadIconWithTint(holder.imageView.getContext(),
                mAdapater.getImageResourceId(position));

        holder.imageView.setImageDrawable(drawable);
        holder.imageView.setSelected(position == getCurrentIndicatorPosition());
    }

    private Drawable loadIconWithTint(Context context, @DrawableRes int resourceId) {
        Drawable icon = ContextCompat.getDrawable(context, resourceId);
        ColorStateList colorStateList = ContextCompat
                .getColorStateList(context, R.color.custom_view02_tint);
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTintList(icon, colorStateList);
        return icon;
    }

    @Override
    public int getItemCount() {
        return mAdapater.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> getViewPager().setCurrentItem(getAdapterPosition()));
        }
    }
}
