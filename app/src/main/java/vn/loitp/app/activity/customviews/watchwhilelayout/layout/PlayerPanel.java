package vn.loitp.app.activity.customviews.watchwhilelayout.layout;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import loitp.basemaster.R;

/**
 * Created by thangn on 3/1/17.
 */

public class PlayerPanel extends FrameLayout {
    public ColorDrawable mBgColorDrawable;
    public View mMiniControlsContainer;
    public FrameLayout mMusicPlayerView;
    public ImageView mPlayButton;
    public ImageView mPauseButton;

    public PlayerPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBgColorDrawable = new ColorDrawable();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMusicPlayerView = (FrameLayout) findViewById(R.id.wwl_music_player_view);
        this.mMiniControlsContainer = findViewById(R.id.mini_controls_container);
        this.mPlayButton = (ImageView) this.mMiniControlsContainer.findViewById(R.id.play_button);
        this.mPauseButton = (ImageView) this.mMiniControlsContainer.findViewById(R.id.pause_button);
        if (Build.VERSION.SDK_INT >= 16) {
            this.mMiniControlsContainer.setBackground(this.mBgColorDrawable);
        } else {
            this.mMiniControlsContainer.setBackgroundDrawable(this.mBgColorDrawable);
        }
        layout();
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void layout() {
        this.mBgColorDrawable.setColor(getResources().getColor(R.color.bg_color_1));
        LayoutParams layoutParams = (LayoutParams) this.mMusicPlayerView.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        this.mMusicPlayerView.setLayoutParams(layoutParams);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int w169 = (int) (((float) (height + 2)) * 1.777f);
        this.mMusicPlayerView.measure(MeasureSpec.makeMeasureSpec(w169, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        if (this.mMiniControlsContainer.getVisibility() == VISIBLE) {
            this.mMiniControlsContainer.measure(MeasureSpec.makeMeasureSpec(width - w169, MeasureSpec.EXACTLY), heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int innerH = bottom - top;
        int playerW = this.mMusicPlayerView.getMeasuredWidth();
        int playerH = this.mMusicPlayerView.getMeasuredHeight();
        this.mMusicPlayerView.layout(0, 0, playerW, playerH);
        if (this.mMiniControlsContainer.getVisibility() == VISIBLE) {
            this.mMiniControlsContainer.layout(playerW, 0, this.mMiniControlsContainer.getMeasuredWidth() + playerW, innerH);
        }
    }
}
