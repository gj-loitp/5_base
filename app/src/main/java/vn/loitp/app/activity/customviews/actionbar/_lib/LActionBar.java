package vn.loitp.app.activity.customviews.actionbar._lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.github.mmin18.widget.RealtimeBlurView;

import vn.loitp.app.utilities.LAnimationUtil;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LActionBar extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivIconBack;
    private ImageView ivIconMenu;
    private TextView tvTitle;
    private RealtimeBlurView realtimeBlurView;
    private View shadowView;

    public ImageView getIvIconBack() {
        return ivIconBack;
    }

    public ImageView getIvIconMenu() {
        return ivIconMenu;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public LActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_action_bar, this);

        this.ivIconBack = (ImageView) findViewById(R.id.iv_icon_back);
        this.ivIconMenu = (ImageView) findViewById(R.id.iv_icon_menu);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.realtimeBlurView = (RealtimeBlurView) findViewById(R.id.blur_view);
        this.shadowView = (View) findViewById(R.id.shadow_view);

        ivIconBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(v, Techniques.Pulse);
                if (callback != null) {
                    callback.onClickBack();
                }
            }
        });
        ivIconMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(v, Techniques.Pulse);
                if (callback != null) {
                    callback.onClickMenu();
                }
            }
        });
    }

    public interface Callback {
        public void onClickBack();

        public void onClickMenu();
    }

    private Callback callback;

    public void setOnClickBack(Callback onClickBack) {
        this.callback = onClickBack;
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

    public void hideBackIcon() {
        ivIconBack.setVisibility(GONE);
    }

    public void inviBackIcon() {
        ivIconBack.setVisibility(INVISIBLE);
    }

    public void hideMenuIcon() {
        ivIconMenu.setVisibility(GONE);
    }

    public void showMenuIcon() {
        ivIconMenu.setVisibility(VISIBLE);
    }

    public void setTvTitlePositionLeft() {
        tvTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
    }

    public void setTvTitlePositionCenter() {
        tvTitle.setGravity(Gravity.CENTER);
    }

    public void setImageMenuIcon(int drawableRes) {
        ivIconMenu.setImageResource(drawableRes);
    }

    public void setImageBackIcon(int drawableRes) {
        ivIconBack.setImageResource(drawableRes);
    }

    public void hideBlurView() {
        realtimeBlurView.setVisibility(GONE);
    }

    public void hideShadowView() {
        shadowView.setVisibility(GONE);
    }
}