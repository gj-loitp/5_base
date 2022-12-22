package com.loitp.views.cal.cosmoCalendar.utils.snap;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Suppress(names = "unused")
public class GravityPagerSnapHelper extends PagerSnapHelper {

    private final GravityDelegate delegate;

    @Suppress(names = "unused")
    public GravityPagerSnapHelper(int gravity) {
        this(gravity, false, null);
    }

    @Suppress(names = "unused")
    public GravityPagerSnapHelper(int gravity, boolean enableSnapLastItem) {
        this(gravity, enableSnapLastItem, null);
    }

    public GravityPagerSnapHelper(
            int gravity,
            boolean enableSnapLastItem,
            GravitySnapHelper.SnapListener snapListener
    ) {
        delegate = new GravityDelegate(gravity, enableSnapLastItem, snapListener);
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (recyclerView != null
                && (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)
                || recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            throw new IllegalStateException("GravityPagerSnapHelper needs a RecyclerView" +
                    " with a LinearLayoutManager");
        }
        delegate.attachToRecyclerView(recyclerView);
        super.attachToRecyclerView(recyclerView);
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(
            @NonNull RecyclerView.LayoutManager layoutManager,
            @NonNull View targetView
    ) {
        return delegate.calculateDistanceToFinalSnap(layoutManager, targetView);
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return delegate.findSnapView(layoutManager);
    }

    /**
     * Enable snapping of the last item that's snappable.
     * The default value is false, because you can't see the last item completely
     * if this is enabled.
     *
     * @param snap true if you want to enable snapping of the last snappable item
     */
    @Suppress(names = "unused")
    public void enableLastItemSnap(boolean snap) {
        delegate.enableLastItemSnap(snap);
    }
}
