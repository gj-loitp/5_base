package com.views.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.R;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;

public abstract class StickerView extends FrameLayout {

    public static final String logTag = "com.knef.stickerView";
    private BorderView ivBorder;
    private ImageView ivScale;
    private ImageView ivDelete;
    private ImageView ivFlip;

    // For scalling
    private float thisOrgX = -1, thisOrgY = -1;
    private float scale_orgX = -1, scale_orgY = -1;
    private double scaleOrgWidth = -1, scaleOrgHeight = -1;
    // For rotating
    private float rotateOrgX = -1, rotateOrgY = -1, rotateNewX = -1, rotateNewY = -1;
    // For moving
    private float move_orgX = -1, move_orgY = -1;

    private double centerX, centerY;

    private final static int BUTTON_SIZE_DP = 30;
    private final static int SELF_SIZE_DP = 100;


    public StickerView(Context context) {
        super(context);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.ivBorder = new BorderView(context);
        this.ivScale = new ImageView(context);
        this.ivDelete = new ImageView(context);
        this.ivFlip = new ImageView(context);

        this.ivScale.setImageResource(R.drawable.ic_zoom_out_map_black_48dp);
        this.ivDelete.setImageResource(R.drawable.ic_close_black_48dp);
        this.ivFlip.setImageResource(R.drawable.ic_autorenew_black_48dp);

        if (LUIUtil.Companion.isDarkTheme()) {
            this.ivScale.setColorFilter(Color.WHITE);
            this.ivDelete.setColorFilter(Color.WHITE);
            this.ivFlip.setColorFilter(Color.WHITE);
        } else {
            this.ivScale.setColorFilter(Color.BLACK);
            this.ivDelete.setColorFilter(Color.BLACK);
            this.ivFlip.setColorFilter(Color.BLACK);
        }

        this.setTag("DraggableViewGroup");
        this.ivBorder.setTag("iv_border");
        this.ivScale.setTag("iv_scale");
        this.ivDelete.setTag("iv_delete");
        this.ivFlip.setTag("iv_flip");

        int margin = convertDpToPixel(BUTTON_SIZE_DP, getContext()) / 2;
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());

        LayoutParams thisParams =
                new LayoutParams(
                        size,
                        size
                );
        thisParams.gravity = Gravity.CENTER;

        LayoutParams ivMainParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        ivMainParams.setMargins(margin, margin, margin, margin);

        LayoutParams ivBorderParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
        ivBorderParams.setMargins(margin, margin, margin, margin);

        LayoutParams ivScaleParams =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        ivScaleParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        LayoutParams ivDeleteParams =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        ivDeleteParams.gravity = Gravity.TOP | Gravity.RIGHT;

        LayoutParams ivFlipParams =
                new LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        ivFlipParams.gravity = Gravity.TOP | Gravity.LEFT;

        this.setLayoutParams(thisParams);
        this.addView(getMainView(), ivMainParams);
        this.addView(ivBorder, ivBorderParams);
        this.addView(ivScale, ivScaleParams);
        this.addView(ivDelete, ivDeleteParams);
        this.addView(ivFlip, ivFlipParams);
        this.setOnTouchListener(mTouchListener);
        this.ivScale.setOnTouchListener(mTouchListener);
        this.ivDelete.setOnClickListener(view -> {
            if (StickerView.this.getParent() != null) {
                ViewGroup myCanvas = ((ViewGroup) StickerView.this.getParent());
                myCanvas.removeView(StickerView.this);
            }
        });
        this.ivFlip.setOnClickListener(view -> {
            View mainView = getMainView();
            mainView.setRotationY(mainView.getRotationY() == -180f ? 0f : -180f);
            mainView.invalidate();
            requestLayout();
        });
    }

    public boolean isFlip() {
        return getMainView().getRotationY() == -180f;
    }

    protected abstract View getMainView();

    private final OnTouchListener mTouchListener = new OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (view.getTag().equals("DraggableViewGroup")) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LLog.d(logTag, "sticker view action down");
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LLog.d(logTag, "sticker view action move");
                        float offsetX = event.getRawX() - move_orgX;
                        float offsetY = event.getRawY() - move_orgY;
                        StickerView.this.setX(StickerView.this.getX() + offsetX);
                        StickerView.this.setY(StickerView.this.getY() + offsetY);
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        LLog.d(logTag, "sticker view action up");
                        break;
                }
            } else if (view.getTag().equals("iv_scale")) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LLog.d(logTag, "iv_scale action down");

                        thisOrgX = StickerView.this.getX();
                        thisOrgY = StickerView.this.getY();

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();
                        scaleOrgWidth = StickerView.this.getLayoutParams().width;
                        scaleOrgHeight = StickerView.this.getLayoutParams().height;

                        rotateOrgX = event.getRawX();
                        rotateOrgY = event.getRawY();

                        centerX = StickerView.this.getX() +
                                ((View) StickerView.this.getParent()).getX() +
                                (float) StickerView.this.getWidth() / 2;


                        int result = 0;
                        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        if (resourceId > 0) {
                            result = getResources().getDimensionPixelSize(resourceId);
                        }
                        double statusBarHeight = result;
                        centerY = StickerView.this.getY() +
                                ((View) StickerView.this.getParent()).getY() +
                                statusBarHeight +
                                (float) StickerView.this.getHeight() / 2;

                        break;
                    case MotionEvent.ACTION_MOVE:
                        LLog.d(logTag, "iv_scale action move");

                        rotateNewX = event.getRawX();
                        rotateNewY = event.getRawY();

                        double angleDiff = Math.abs(
                                Math.atan2(event.getRawY() - scale_orgY, event.getRawX() - scale_orgX)
                                        - Math.atan2(scale_orgY - centerY, scale_orgX - centerX)) * 180 / Math.PI;

                        LLog.d(logTag, "angle_diff: " + angleDiff);

                        double length1 = getLength(centerX, centerY, scale_orgX, scale_orgY);
                        double length2 = getLength(centerX, centerY, event.getRawX(), event.getRawY());

                        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
                        if (length2 > length1
                                && (angleDiff < 25 || Math.abs(angleDiff - 180) < 25)
                        ) {
                            //scale up
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            StickerView.this.getLayoutParams().width += offset;
                            StickerView.this.getLayoutParams().height += offset;
                            onScaling(true);
                            //DraggableViewGroup.this.setX((float) (getX() - offset / 2));
                            //DraggableViewGroup.this.setY((float) (getY() - offset / 2));
                        } else if (length2 < length1
                                && (angleDiff < 25 || Math.abs(angleDiff - 180) < 25)
                                && StickerView.this.getLayoutParams().width > size / 2
                                && StickerView.this.getLayoutParams().height > size / 2) {
                            //scale down
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            StickerView.this.getLayoutParams().width -= offset;
                            StickerView.this.getLayoutParams().height -= offset;
                            onScaling(false);
                        }

                        //rotate

                        double angle = Math.atan2(event.getRawY() - centerY, event.getRawX() - centerX) * 180 / Math.PI;
                        LLog.d(logTag, "log angle: " + angle);

                        //setRotation((float) angle - 45);
                        setRotation((float) angle - 45);
                        LLog.d(logTag, "getRotation(): " + getRotation());

                        onRotating();

                        rotateOrgX = rotateNewX;
                        rotateOrgY = rotateNewY;

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();

                        postInvalidate();
                        requestLayout();
                        break;
                    case MotionEvent.ACTION_UP:
                        LLog.d(logTag, "iv_scale action up");
                        break;
                }
            }
            return true;
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    private float[] getRelativePos(float absX, float absY) {
        LLog.d("ken", "getRelativePos getX:" + ((View) this.getParent()).getX());
        LLog.d("ken", "getRelativePos getY:" + ((View) this.getParent()).getY());
        float[] pos = new float[]{
                absX - ((View) this.getParent()).getX(),
                absY - ((View) this.getParent()).getY()
        };
        LLog.d(logTag, "getRelativePos absY:" + absY);
        LLog.d(logTag, "getRelativePos relativeY:" + pos[1]);
        return pos;
    }

    public void setControlItemsHidden(boolean isHidden) {
        if (isHidden) {
            ivBorder.setVisibility(View.INVISIBLE);
            ivScale.setVisibility(View.INVISIBLE);
            ivDelete.setVisibility(View.INVISIBLE);
            ivFlip.setVisibility(View.INVISIBLE);
        } else {
            ivBorder.setVisibility(View.VISIBLE);
            ivScale.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            ivFlip.setVisibility(View.VISIBLE);
        }
    }

    protected View getImageViewFlip() {
        return ivFlip;
    }

    protected void onScaling(boolean scaleUp) {
    }

    protected void onRotating() {
    }

    private static class BorderView extends View {

        public BorderView(Context context) {
            super(context);
        }

        public BorderView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BorderView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // Draw sticker border

            LayoutParams params = (LayoutParams) this.getLayoutParams();

            LLog.d(logTag, "params.leftMargin: " + params.leftMargin);

            Rect border = new Rect();
            border.left = (int) this.getLeft() - params.leftMargin;
            border.top = (int) this.getTop() - params.topMargin;
            border.right = (int) this.getRight() - params.rightMargin;
            border.bottom = (int) this.getBottom() - params.bottomMargin;
            Paint borderPaint = new Paint();
            borderPaint.setStrokeWidth(6);
            if (LUIUtil.Companion.isDarkTheme()) {
                borderPaint.setColor(Color.WHITE);
            } else {
                borderPaint.setColor(Color.BLACK);
            }
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(border, borderPaint);

        }
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public void setControlsVisibility(boolean isVisible) {
        if (!isVisible) {
            ivBorder.setVisibility(View.GONE);
            ivDelete.setVisibility(View.GONE);
            ivFlip.setVisibility(View.GONE);
            ivScale.setVisibility(View.GONE);
        } else {
            ivBorder.setVisibility(View.VISIBLE);
            ivDelete.setVisibility(View.VISIBLE);
            ivFlip.setVisibility(View.VISIBLE);
            ivScale.setVisibility(View.VISIBLE);
        }

    }
}
