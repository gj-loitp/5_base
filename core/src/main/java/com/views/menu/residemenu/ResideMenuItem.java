package com.views.menu.residemenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.utilities.LUIUtil;
import com.utils.util.ConvertUtils;

import loitp.core.R;

/**
 * User: loitp
 * Date: 13-12-10
 * Time: 下午11:05
 * Mail: www.muathu@gmail.com
 */
public class ResideMenuItem extends LinearLayout {

    /**
     * menu item  icon
     */
    private ImageView ivIcon;
    /**
     * menu item  title
     */
    private TextView tvTitle;

    public ResideMenuItem(Context context) {
        super(context);
        initViews(context);
    }

    public ResideMenuItem(Context context, int icon, int title) {
        super(context);
        initViews(context);
        ivIcon.setImageResource(icon);
        tvTitle.setText(title);
    }

    public ResideMenuItem(Context context, int icon, String title) {
        super(context);
        initViews(context);
        ivIcon.setImageResource(icon);
        tvTitle.setText(title);
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.residemenu_item, this);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * set the icon color;
     *
     * @param icon
     */
    public void setIcon(int icon) {
        ivIcon.setImageResource(icon);
    }

    /**
     * set the title with resource
     * ;
     *
     * @param title
     */
    public void setTitle(int title) {
        tvTitle.setText(title);
    }

    /**
     * set the title with string;
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setTextSize(int typedValue, int size) {
        LUIUtil.setTextSize(tvTitle, typedValue, size);
    }

    public void setTextShadow(int color) {
        LUIUtil.setTextShadow(tvTitle, color);
    }

    public void setIvIconSizePx(int sizeInPx) {
        ivIcon.getLayoutParams().width = sizeInPx;
        ivIcon.getLayoutParams().height = sizeInPx;
        ivIcon.requestLayout();
    }

    public void setIvIconSizeDp(int sizeInDp) {
        ivIcon.getLayoutParams().width = ConvertUtils.dp2px(sizeInDp);
        ivIcon.getLayoutParams().height = ConvertUtils.dp2px(sizeInDp);
        ivIcon.requestLayout();
    }
}
