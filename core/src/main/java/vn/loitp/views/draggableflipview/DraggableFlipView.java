package vn.loitp.views.draggableflipview;

import android.content.Context;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import loitp.core.R;

/**
 * a class performing animation of view depending on the movement of user
 * <p>
 * Created by sasakicks on 2015/09/09.
 */
public class DraggableFlipView extends FrameLayout implements DragGestureDetector.DragGestureListener {

    private static final float DRAG_THRESHOLD_PARAM = 50.0f;
    private static final int DEFAULT_VALUE = 0;
    private static final int DEFAULT_DRAGGABLE_VALUE = 50;
    private static final int DEFAULT_DRAG_DETECT_VALUE = 7;

    private DragGestureDetector mDragGestureDetector;
    private boolean isAnimation;
    private boolean isDragging;
    private int mAngle;
    private int mDraggableAngle;
    private int mDragDetectAngle;
    private boolean mIsReverse;
    private FlipListener mFlipListener;

    private RelativeLayout mFrontLayout;
    private RelativeLayout mBackLayout;

    private enum RotateDirection {
        RIGHT(1), LEFT(-1);

        private int mValue;

        RotateDirection(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    public DraggableFlipView(Context context) {
        this(context, null);
    }

    public DraggableFlipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableFlipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mFrontLayout = new RelativeLayout(context);
        mFrontLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        mBackLayout = new RelativeLayout(context);
        mBackLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        this.addView(mFrontLayout);
        this.addView(mBackLayout);
        mBackLayout.setVisibility(View.INVISIBLE);

        mFlipListener = new FlipListener(mFrontLayout, mBackLayout, this);
        mDragGestureDetector = new DragGestureDetector(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DraggableFlipView);
        LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.DraggableFlipView_frontView, DEFAULT_VALUE), mFrontLayout);
        LayoutInflater.from(context).inflate(a.getResourceId(R.styleable.DraggableFlipView_backView, DEFAULT_VALUE), mBackLayout);

        mDraggableAngle = a.getInteger(R.styleable.DraggableFlipView_draggableAngle, DEFAULT_DRAGGABLE_VALUE);
        mDragDetectAngle = a.getInteger(R.styleable.DraggableFlipView_dragDetectAngle, DEFAULT_DRAG_DETECT_VALUE);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mDragGestureDetector == null) return false;
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - mDragGestureDetector.getTouchPoint().getX()) > DRAG_THRESHOLD_PARAM
                        || Math.abs(ev.getY() - mDragGestureDetector.getTouchPoint().getY()) > DRAG_THRESHOLD_PARAM) {
                    mDragGestureDetector.setPointMap(ev);
                    return true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDragGestureDetector != null) {
            mDragGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    @Override
    public void onDragGestureListener(DragGestureDetector dragGestureDetector, int action) {
        if (isAnimation) return;
        if (action == MotionEvent.ACTION_UP) {
            if (mAngle >= mDragDetectAngle) {
                startAutoRotateAnimation(RotateDirection.RIGHT);
            } else if (mAngle < -mDragDetectAngle) {
                startAutoRotateAnimation(RotateDirection.LEFT);
            }
            return;
        }

        mAngle = (dragGestureDetector.deltaX - dragGestureDetector.prevDeltaX) > 0 ? ++mAngle : --mAngle;
        if (Math.abs(mAngle) > mDragDetectAngle) isDragging = true;
        if (isDragging) this.setRotationY(mAngle);

        if (mAngle >= mDraggableAngle) {
            startAutoRotateAnimation(RotateDirection.RIGHT);
        } else if (mAngle < -mDraggableAngle) {
            startAutoRotateAnimation(RotateDirection.LEFT);
        }
    }

    private void startAutoRotateAnimation(RotateDirection rotateDirection) {
        isAnimation = true;
        if (mIsReverse) {
            mFlipListener.reverse();
        } else {
            mIsReverse = true;
        }

        mFlipListener.setRotateDirection(rotateDirection.getValue());
        ValueAnimator flipAnimator = ValueAnimator.ofFloat(0f, 1f);
        flipAnimator.addUpdateListener(mFlipListener);
        flipAnimator.start();
        flipAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAngle = 0;
                isAnimation = false;
                isDragging = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }
}

