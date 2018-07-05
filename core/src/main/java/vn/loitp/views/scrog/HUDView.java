/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.loitp.views.scrog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

import loitp.core.R;
import vn.loitp.views.LToast;

import static android.content.Context.WINDOW_SERVICE;

/**
 * @author Anton Smyshliaiev (anton.emale@gmail.com)
 */
@SuppressLint("DrawAllocation")
class HUDView extends ScrollView {
    private Paint mPaint = new Paint();
    private WindowManager mWm;
    private WindowManager.LayoutParams mParams;
    private GestureDetector mDetector;
    private float mLastDownRawX = 0;
    private float mLastDownRawY = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mMinWidth = 0;
    private int mMinHeight = 0;
    private TapMode mTap = TapMode.MOVE;
    private ColorManager colMan = new ColorManager();

    private Context mContext;

    TextObjectManager mTextObjectManager = new TextObjectManager(mPaint);
    boolean mClear = false;

    private enum TapMode {
        MOVE, RESIZE, CLEAR, IGNORE;
    }

    HUDView(Context context) {
        super(context);
        this.mContext = context;
        mWm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        init();
    }

    private void init() {
        TapDetector tapDetector = new TapDetector(mPaint, this, colMan);
        mDetector = new GestureDetector(mContext, tapDetector);
        LToast.show(mContext, "You may double tap, move or resize info window");
    }

    public void initView() {
        clear();

        Display display = mWm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mWidth = size.x / 2;
        mHeight = size.x / 3;
        mMinWidth = size.x / 4;
        mMinHeight = size.y / 4;

        mPaint.setColor(colMan.getColor().fg);
        setBackgroundColor(colMan.getColor().bg);
        getBackground().setAlpha(ColorManager.ALPHA);

        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTypeface(Typeface.MONOSPACE);

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mParams = new WindowManager.LayoutParams(
                mWidth,
                mHeight,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        addView(mWm, mParams);

        applyWH(size.x / 2, size.y / 3);
        setTextObjectMaxes();
        postInvalidate();
    }

    private void addView(WindowManager wm, WindowManager.LayoutParams params) {
        wm.addView(this, params);
    }

    public void updateViewLayout() {
        mWm.updateViewLayout(this, mParams);
    }

    public void setWidthHeight(int w, int h) {
        applyWH(w, h);
        setTextObjectMaxes();
    }

    private void setTextObjectMaxes() {
        int scaledFontSizePx = getResources().getDimensionPixelSize(R.dimen.txt_8);
        mPaint.setTextSize(scaledFontSizePx);
        float width = mPaint.measureText("A");
        mTextObjectManager.setMaxLines(mParams.height / scaledFontSizePx);
        mTextObjectManager.setMaxSymbols((int) (mParams.width / width) - 1);
    }

    private void applyWH(int w, int h) {
        if (w < mMinWidth) return;
        if (h < mMinHeight) return;

        mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, w, getResources().getDisplayMetrics());
        mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, h, getResources().getDisplayMetrics());

        mParams.width = mWidth;
        mParams.height = mHeight;
    }

    protected void onDraw(Canvas canvas) {
        if (mClear == false) {

            mTextObjectManager.drawObjects(canvas);

        } else {
            Paint clearPaint = new Paint();
            clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRect(0, 0, mParams.width, mParams.height, clearPaint);
        }
    }

    public void destroyView() {
        clear();
        mWm.removeView(this);
    }

    public void setText(String text) {
        mClear = false;
        mTextObjectManager.addLine(text);
        postInvalidate();
    }

    public void clear() {
        mClear = true;
        mTextObjectManager.clear();
        postInvalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                checkTapAction(event);
                checkMinMaxBounds();
                updateViewLayout();
                return true;

            case MotionEvent.ACTION_DOWN:
                //check where tapped
                setTapMode((int) event.getX(), (int) event.getY());
                setLastTouchCoordinates(event);

                return true;

            case MotionEvent.ACTION_UP:
                return true;
        }
        return true;
    }

    public void setTapMode(int tapX, int tapY) {
        mTap = TapMode.IGNORE;

        Rect rect = new Rect(0, 0, (int) (mParams.width), (int) (mParams.height));

        if ((tapX >= rect.left) && (tapX <= rect.right * 0.8)) {
            if ((tapY >= rect.top) && (tapY <= rect.bottom * 0.8)) {
                mTap = TapMode.MOVE;
                return;
            }
        }

        if ((tapX >= rect.right * 0.8) && (tapX <= rect.right)) {
            if ((tapY >= rect.bottom * 0.8) && (tapY <= rect.bottom)) {
                mTap = TapMode.RESIZE;
                return;
            }
        }

        if ((tapX >= rect.right * 0.8) && (tapX <= rect.right)) {
            if ((tapY >= rect.top) && (tapY <= rect.bottom * 0.2)) {
                mTap = TapMode.CLEAR;
                return;
            }
        }

    }

    private void checkTapAction(MotionEvent event) {
        //move area
        switch (mTap) {
            case MOVE:
                mParams.x = Math.round(event.getRawX() - mLastDownRawX);
                mParams.y = Math.round(event.getRawY() - mLastDownRawY);
                break;

            case RESIZE:
                if (Math.round(event.getX()) >= mMinWidth) mParams.width = Math.round(event.getX());
                else mParams.width = mMinWidth;

                //check for minimal size height
                if (Math.round(event.getY()) >= mMinHeight)
                    mParams.height = Math.round(event.getY());
                else mParams.height = mMinHeight;

                setTextObjectMaxes();
                break;

            case CLEAR:
                mTextObjectManager.clear();
                postInvalidate();
                break;
            default:
                break;

        }
    }

    private synchronized void setLastTouchCoordinates(MotionEvent event) {
        mLastDownRawX = event.getRawX() - mParams.x;
        mLastDownRawY = event.getRawY() - mParams.y;
    }

    private void checkMinMaxBounds() {
        Display display = mWm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if (mParams.x > size.x / 2) {
            mParams.x = (size.x / 2 - mParams.width / 3);
        }
        if (mParams.y > size.y / 2) {
            mParams.y = (size.y / 2 - mParams.height / 3);
        }
        if (mParams.x < -size.x / 2) {
            mParams.x = (-size.x / 2 + mParams.width / 3);
        }
        if (mParams.y < -size.y / 2) {
            mParams.y = (-size.y / 2 + mParams.height / 3);
        }
    }

    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {

    }
}
