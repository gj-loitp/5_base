package vn.loitp.views.layout.fixedgridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import loitp.core.R;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 고정된 row, column 을 갖는 그리드 형태의 레이아웃.
 */
public class FixedGridLayout extends ViewGroup {

    public FixedGridLayout(Context context) {
        this(context, null);
    }

    public FixedGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.lFixedGridLayout);

            int numRows = ta.getInteger(R.styleable.lFixedGridLayout_lNumRows, -1);
            if (numRows != -1) {
                this.numRows = numRows;
            }

            int numColumns = ta.getInteger(R.styleable.lFixedGridLayout_lNumColumns, -1);
            if (numColumns != -1) {
                this.numColumns = numColumns;
            }

            this.horizontalSpacing = ta.getDimensionPixelSize(R.styleable.lFixedGridLayout_lHorizontalSpacing, 0);
            this.verticalSpacing = ta.getDimensionPixelSize(R.styleable.lFixedGridLayout_lVerticalSpacing, 0);
            this.horizontalDivider = ta.getDrawable(R.styleable.lFixedGridLayout_lHorizontalDivider);

            ta.recycle();
        }
    }

    private int numRows;
    private int numColumns;

    private int horizontalSpacing = 0;
    private int verticalSpacing = 0;

    private int cellWidth;
    private int cellHeight;

    private Drawable horizontalDivider;

    private void init() {

    }

    public void setGridSize(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;

        requestLayout();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;

        requestLayout();
    }

    public void setHorizontalDivider(Drawable d) {
        this.horizontalDivider = d;

        invalidate();
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;

        requestLayout();
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return (p instanceof MarginLayoutParams);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        if (p == null) return generateDefaultLayoutParams();
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int numItemsPerPage = getNumColumns() * getNumRows();

        int overItemNum = getChildCount() - numItemsPerPage;

        int overHeight = 0;

        if (overItemNum > 0) {
            cellHeight = heightSize / getNumRows();
            overHeight = (int) (cellHeight * Math.ceil((double) overItemNum / getNumRows()));
        }

        setMeasuredDimension(widthSize, heightSize + overHeight);
        //===================================================================================
        int contentWidth = widthSize - getPaddingLeft() - getPaddingRight();
        int contentHeight = heightSize - getPaddingTop() - getPaddingBottom();

        cellWidth = Math.max((contentWidth - horizontalSpacing * (numColumns - 1)) / numColumns, 0);
        cellHeight = Math.max((contentHeight - verticalSpacing * (numRows - 1)) / numRows, 0);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidthSize = cellWidth - lp.leftMargin - lp.rightMargin;
            int childHeightSize = cellHeight - lp.topMargin - lp.bottomMargin;
            if (lp.width >= 0) {
                childWidthSize = Math.min(lp.width, childWidthSize);
            }
            if (lp.height >= 0) {
                childHeightSize = Math.min(lp.height, childHeightSize);
            }

            int childWidthMode = (lp.width == WRAP_CONTENT) ? AT_MOST : EXACTLY;
            int childHeightMode = (lp.height == WRAP_CONTENT) ? AT_MOST : EXACTLY;

            int childWidthSpec = MeasureSpec.makeMeasureSpec(
                    childWidthSize, childWidthMode);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(
                    childHeightSize, childHeightMode);
            child.measure(childWidthSpec, childHeightSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int pleft = getPaddingLeft();
        int ptop = getPaddingTop();

        int count = getChildCount();
        int position = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int row = position / numColumns;
                int column = position - row * numColumns;
                ++position;

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                int childLeft = pleft + lp.leftMargin + column * (cellWidth + horizontalSpacing) + (cellWidth - childWidth) / 2;
                int childTop = ptop + lp.topMargin + row * (cellHeight + verticalSpacing) + (cellHeight - childHeight) / 2;

                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (horizontalDivider != null) {
            Drawable d = horizontalDivider;
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            for (int column = 0, x = getPaddingLeft(); column < numColumns - 1; column++, x += cellWidth + horizontalSpacing) {
                int dl = x + cellWidth + (horizontalSpacing - dw) / 2;
                int dr = dl + dw;
                for (int row = 0, y = getPaddingTop(); row < numRows; row++, y += cellHeight + verticalSpacing) {
                    if (row * numColumns + column < getChildCount() - 1) {
                        int dt = y + (cellHeight - dh) / 2;
                        int db = dt + dh;

                        d.setBounds(dl, dt, dr, db);
                        d.draw(canvas);
                    }
                }
            }
        }

        super.dispatchDraw(canvas);
    }

    public int getRowAtY(int y) {
        if (cellHeight == 0) return 0;
        return Math.min(Math.max((y - getPaddingTop()) / cellHeight, 0), numRows - 1);
    }

    public int getColumnAtX(int x) {
        if (cellWidth == 0) return 0;
        return Math.min(Math.max((x - getPaddingLeft()) / cellWidth, 0), numColumns - 1);
    }

    public int getChildIndexAtPoint(int x, int y) {
        int row = getRowAtY(y);
        int column = getColumnAtX(x);

        int childIndex = row * numColumns + column;
        if (childIndex >= getChildCount()) {
            childIndex = -1;
        }

        return childIndex;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }
}
