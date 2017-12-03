package vn.loitp.views.button.circularimageclick.lib;

/**
 * Created by www.muathu@gmail.com on 11/1/2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * MIT License
 * <p>
 * Copyright (c) 2017 AndroidArk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p>
 * Created by ahmed-basyouni on 3/4/17.
 */

public class CircularClickImageButton extends ImageButton {

    //center x of Image
    private float centerX;
    //center y of Image
    private float centerY;
    //the radius of circle
    private float radius;

    //the circle view on click listener
    private OnCircleClickListener onCircleClickListener;

    public CircularClickImageButton(Context context) {
        super(context);
    }

    public CircularClickImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularClickImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @param onCircleClickListener The callback that will run
     */
    public void setOnCircleClickListener(OnCircleClickListener onCircleClickListener) {
        if (!isClickable())
            setClickable(true);
        this.onCircleClickListener = onCircleClickListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = radius = w / 2;
        centerY = h / 2;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //here we get the toucj event and then we make sure that the touch event
        //was inside the circle not outside it if so invoke the call back
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float eventX = event.getX();
                float eventY = event.getY();
                if (eventY == centerY) {
                    if (Math.abs(eventX - centerX) <= radius) {
                        if (onCircleClickListener != null)
                            onCircleClickListener.onClick(this);
                        return super.onTouchEvent(event);
                    }
                } else {
                    double firstLeg = Math.pow(Math.abs(eventX - centerX), 2);
                    double secLeg = Math.pow(Math.abs(eventY - centerY), 2);
                    double hypotenuse = Math.sqrt(firstLeg + secLeg);
                    if (hypotenuse <= radius) {
                        if (onCircleClickListener != null)
                            onCircleClickListener.onClick(this);
                        return super.onTouchEvent(event);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                return super.onTouchEvent(event);
        }
        return false;
    }

    /**
     * Interface definition for a callback to be invoked when a circle view is clicked
     */
    public interface OnCircleClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void onClick(View v);
    }
}