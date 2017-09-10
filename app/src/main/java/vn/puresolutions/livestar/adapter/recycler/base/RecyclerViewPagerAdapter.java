package vn.puresolutions.livestar.adapter.recycler.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.puresolutions.livestar.core.api.model.BaseModel;

/**
 * @author hoangphu
 * @since 7/31/16
 *
 * NOTE this adapter doesn't support header and footer view
 */
public abstract class RecyclerViewPagerAdapter<Model extends BaseModel, ViewHolder extends RecyclerViewPagerAdapter.ViewPagerHolder>
        extends NoneHeaderFooterRecyclerAdapter<Model, ViewHolder> {
    protected boolean canScrollHorizontally;

    public RecyclerViewPagerAdapter() {
        super();
        this.canScrollHorizontally = false;
    }

    public RecyclerViewPagerAdapter(List<Model> items, boolean canScrollHorizontally) {
        super(items);
        this.canScrollHorizontally = canScrollHorizontally;
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return createViewHolder(container, canScrollHorizontally);
    }

    protected abstract ViewHolder createViewHolder(View container, boolean canScrollHorizontally);

    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        public ViewPagerHolder(View itemView, boolean canScrollHorizontally) {
            super(itemView);
            ViewGroup.LayoutParams lp;
            if (itemView.getLayoutParams() == null) {
                lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                lp = itemView.getLayoutParams();
                if (canScrollHorizontally) {
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
            }
            itemView.setLayoutParams(lp);
        }
    }
}
