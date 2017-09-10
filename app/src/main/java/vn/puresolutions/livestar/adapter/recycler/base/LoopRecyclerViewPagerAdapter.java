package vn.puresolutions.livestar.adapter.recycler.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

import vn.puresolutions.livestar.core.api.model.BaseModel;

/** @author hoangphu
 * @since 7/31/16
 *
 * NOTE this adapter doesn't support header and footer view
 */
public abstract class LoopRecyclerViewPagerAdapter<Model extends BaseModel, ViewHolder extends RecyclerViewPagerAdapter.ViewPagerHolder>
        extends RecyclerViewPagerAdapter<Model, ViewHolder> {

    private static final String TAG = LoopRecyclerViewPagerAdapter.class.getName();
    private Field mPositionField;

    public LoopRecyclerViewPagerAdapter(List<Model> items, boolean canScrollHorizontally) {
        super(items, canScrollHorizontally);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(getActualPosition(position));
    }

    @Override
    public Model getItem(int position) {
        return items.get(getActualPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(getActualPosition(position));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, getActualPosition(position));
        // because of getCurrentPosition may return Builder‘s position,
        // so we must reset mPosition if exists.
        if (mPositionField == null) {
            try {
                mPositionField = holder.getClass().getDeclaredField("mPosition");
                mPositionField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                // The holder doesn't have a mPosition field
            }
        }
        if (mPositionField != null) {
            try {
                mPositionField.set(holder, position);
            } catch (Exception e) {
                Log.w(TAG, "Error while updating holder's mPosition field", e);
            }
        }
    }

    public int getActualItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getActualPosition(int position) {
        int actualPosition = position;
        if (position >= getActualItemCount()) {
            actualPosition = position % getActualItemCount();
        }
        return actualPosition;
    }
}
