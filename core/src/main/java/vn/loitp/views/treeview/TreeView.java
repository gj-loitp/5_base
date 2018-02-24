package vn.loitp.views.treeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Px;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import loitp.core.R;

public class TreeView extends AdapterView<TreeAdapter> implements GestureDetector.OnGestureListener {

    private static final int DEFAULT_LINE_LENGTH = 100;
    private static final int DEFAULT_LINE_THICKNESS = 5;
    private static final int DEFAULT_LINE_COLOR = Color.BLACK;
    private static final int INVALID_INDEX = -1;

    Path mLinePath = new Path();
    Paint mLinePaint = new Paint();
    private int mLineThickness;
    private int mLineColor;
    private int mLevelSeparation;

    private TreeAdapter mAdapter;
    private int mMaxChildWidth;
    private int mMaxChildHeight;
    private Rect mRect;
    private float mMaxTreeWidth;
    private float mMaxTreeHeight;

    private DataSetObserver mDataSetObserver;
    private TreeNodeSize mTreeNodeSize = new TreeNodeSize();

    private GestureDetector mGestureDetector;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TreeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TreeView, 0, 0);
        try {
            mLevelSeparation = a.getDimensionPixelSize(R.styleable.TreeView_level_separation, DEFAULT_LINE_LENGTH);
            mLineThickness = a.getDimensionPixelSize(R.styleable.TreeView_line_thickness, DEFAULT_LINE_THICKNESS);
            mLineColor = a.getColor(R.styleable.TreeView_line_color, DEFAULT_LINE_COLOR);
        } finally {
            a.recycle();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        mGestureDetector = new GestureDetector(context, this);

        if (attrs != null) {
            initAttrs(context, attrs);
        }
        initPaint();
    }

    private void initPaint() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(mLineThickness);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
//        mLinePaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        mLinePaint.setPathEffect(new CornerPathEffect(10));   // set the path effect when they join.
    }

    private void positionItems() {
        for (int index = 0; index < mAdapter.getCount(); index++) {
            final View child = mAdapter.getView(index, null, this);

            addAndMeasureChild(child);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            final Point screenPosition = mAdapter.getScreenPosition(index);
            TreeNode node = mAdapter.getNode(index);

            // calculate the size and position of this child
            final int left = screenPosition.x;
            final int top = screenPosition.y + (node.getLevel() * mLevelSeparation);
            final int right = left + width;
            final int bottom = top + height;

            child.layout(left, top, right, bottom);

            // save the max width and height of the whole tree, so we have the exact boundaries
            mMaxTreeWidth = Math.max(mMaxTreeWidth, right);
            mMaxTreeHeight = Math.max(mMaxTreeHeight, bottom);
        }
    }

    /**
     * Returns the index of the child that contains the coordinates given.
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return The index of the child that contains the coordinates. If no child
     * is found then it returns INVALID_INDEX
     */
    private int getContainingChildIndex(final int x, final int y) {
        if (mRect == null) {
            mRect = new Rect();
        }
        for (int index = 0; index < getChildCount(); index++) {
            getChildAt(index).getHitRect(mRect);
            if (mRect.contains(x, y)) {
                return index;
            }
        }
        return INVALID_INDEX;
    }

    private void clickChildAt(final int x, final int y) {
        final int index = getContainingChildIndex(x, y);
        // no child found at this position
        if (index == INVALID_INDEX) {
            return;
        }

        final View itemView = getChildAt(index);
        final long id = mAdapter.getItemId(index);
        performItemClick(itemView, index, id);
    }

    private void addAndMeasureChild(final View child) {
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        addViewInLayout(child, -1, params, false);

        final int widthSpec = MeasureSpec.makeMeasureSpec(
                mMaxChildWidth, MeasureSpec.EXACTLY);
        final int heightSpec = MeasureSpec.makeMeasureSpec(
                mMaxChildHeight, MeasureSpec.EXACTLY);

        child.measure(widthSpec, heightSpec);
    }

    private void longClickChildAt(final int x, final int y) {

        final int index = getContainingChildIndex(x, y);
        // no child found at this position
        if (index == INVALID_INDEX) {
            return;
        }

        final View itemView = getChildAt(index);
        final long id = mAdapter.getItemId(index);
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            listener.onItemLongClick(this, itemView, index, id);
        }
    }

    private void drawLines(Canvas canvas, TreeNode treeNode) {
        if (treeNode.hasChildren()) {
            mLinePath.reset();

            mLinePath.moveTo(treeNode.getX() + (mMaxChildWidth / 2),
                    treeNode.getY() + mMaxChildHeight + (mLevelSeparation * treeNode.getLevel()));
            mLinePath.lineTo(treeNode.getX() + (mMaxChildWidth / 2),
                    treeNode.getY() + mMaxChildHeight + (mLevelSeparation * treeNode.getLevel()) + (mLevelSeparation / 2));
            canvas.drawPath(mLinePath, mLinePaint);

            for (TreeNode child : treeNode.getChildren()) {
                drawLines(canvas, child);
            }
        }

        if (treeNode.hasParent()) {
            mLinePath.reset();

            TreeNode parent = treeNode.getParent();
            mLinePath.moveTo(treeNode.getX() + (mMaxChildWidth / 2), treeNode.getY() + (mLevelSeparation * treeNode.getLevel()));
            mLinePath.lineTo(treeNode.getX() + (mMaxChildWidth / 2), treeNode.getY() + (mLevelSeparation * treeNode.getLevel()) - (mLevelSeparation / 2));
            mLinePath.lineTo(parent.getX() + (mMaxChildWidth / 2),
                    parent.getY() + mMaxChildHeight + (mLevelSeparation * parent.getLevel()) + mLevelSeparation / 2);
            canvas.drawPath(mLinePath, mLinePaint);
        }
    }

    /**
     * @return Returns the value of how thick the lines between the nodes are.
     */
    public int getLineThickness() {
        return mLineThickness;
    }

    /**
     * Sets a new value for the thickness of the lines between the nodes.
     *
     * @param lineThickness new value for the thickness
     */
    public void setLineThickness(int lineThickness) {
        mLineThickness = lineThickness;
        initPaint();
        invalidate();
    }

    /**
     * @return Returns the color of the lines between the nodes.
     */
    @ColorInt
    public int getLineColor() {
        return mLineColor;
    }

    /**
     * Sets a new color for the lines between the nodes.A change to this value
     * invokes a re-drawing of the tree.
     *
     * @param lineColor the new color
     */
    public void setLineColor(@ColorInt int lineColor) {
        mLineColor = lineColor;
        initPaint();
        invalidate();
    }

    /**
     * Returns the value of how much space should be used between two levels.
     *
     * @return level separation value
     */
    @Px
    public int getLevelSeparation() {
        return mLevelSeparation;
    }

    /**
     * Sets a new value of how much space should be used between two levels. A change to this value
     * invokes a re-drawing of the tree.
     *
     * @param levelSeparation new value for the level separation
     */
    public void setLevelSeparation(@Px int levelSeparation) {
        mLevelSeparation = levelSeparation;
        invalidate();
        requestLayout();
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        clickChildAt((int) e.getX() + getScrollX(), (int) e.getY() + getScrollY());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent event, float distanceX, float distanceY) {
        final float newScrollX = getScrollX() + distanceX;
        final float newScrollY = getScrollY() + distanceY;

        final float maxLeftScroll = -getWidth() + mMaxChildWidth + getPaddingLeft();
        final float maxRightScroll = mMaxTreeWidth - mMaxChildWidth - getPaddingRight();
        final float maxTopScroll = -getHeight() + mMaxChildHeight + getPaddingTop();
        final float maxBottomScroll = mMaxTreeHeight - mMaxChildHeight - getPaddingBottom();

        if (newScrollX >= maxLeftScroll &&
                newScrollX <= maxRightScroll &&
                newScrollY >= maxTopScroll &&
                newScrollY <= maxBottomScroll) {
            scrollBy((int) distanceX, (int) distanceY);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        longClickChildAt((int) event.getX() + getScrollX(), (int) event.getY() + getScrollY());
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public TreeAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(TreeAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }

        mAdapter = adapter;
        mDataSetObserver = new TreeDataSetObserver();
        mAdapter.registerDataSetObserver(mDataSetObserver);

        requestLayout();
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right,
                            final int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mAdapter == null) {
            return;
        }

        removeAllViewsInLayout();
        positionItems();

        invalidate();
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        TreeNode rootNode = mAdapter.getNode(0);
        if (rootNode != null) {
            drawLines(canvas, rootNode);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mAdapter == null) {
            return;
        }

        final int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int maxWidth = 0;
        int maxHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View child = mAdapter.getView(i, null, this);
            final int childSpecWidth = MeasureSpec.makeMeasureSpec(
                    specWidth, MeasureSpec.UNSPECIFIED);
            final int childSpecHeight = MeasureSpec.makeMeasureSpec(
                    specHeight, MeasureSpec.UNSPECIFIED);

            LayoutParams params = child.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            }
            addViewInLayout(child, -1, params, true);

            child.measure(childSpecWidth, childSpecHeight);
            maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }

        mMaxChildWidth = maxWidth;
        mMaxChildHeight = maxHeight;
        mTreeNodeSize.set(maxWidth, maxHeight);
        mAdapter.notifySizeChanged(mTreeNodeSize);
    }

    private class TreeDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();

            refresh();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();

            refresh();
        }

        private void refresh() {
            invalidate();
            requestLayout();
        }
    }
}
