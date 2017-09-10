package vn.puresolutions.livestar.adapter.recycler.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.puresolutions.livestar.core.api.model.BaseModel;

/**
 * @author hoangphu
 * @since 7/31/16
 */
public abstract class RecyclerAdapter<Model extends BaseModel, ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Defines available view type integers for headers and footers.
     * <p/>
     * - Regular views use view types starting from 0, counting upwards
     * - Header views use view types starting from -1000, counting upwards
     * - Footer views use view types starting from -2000, counting upwards
     * <p/>
     * This means that you're safe as long as the base adapter doesn't use negative view types,
     * and as long as you have fewer than 1000 headers and footers
     */
    protected static final int HEADER_VIEW_TYPE = -1000;
    protected static final int FOOTER_VIEW_TYPE = -2000;

    protected List<Model> items;
    protected OnItemClickListener itemClickListener;
    protected OnItemLongClickListener itemLongClickListener;
    protected final List<View> mHeaders = new ArrayList<>();
    protected final List<View> mFooters = new ArrayList<>();
    protected LayoutInflater inflater;

    @SuppressWarnings("unchecked")
    public RecyclerAdapter(List<Model> items) {
        this.items = items;
    }

    public RecyclerAdapter() {
        this(new ArrayList<>());
    }

    public void addHeader(@NonNull View view) {
        mHeaders.add(view);
    }

    public void addFooter(@NonNull View view) {
        mFooters.add(view);
    }

    public int getHeaderCount() {
        return mHeaders.size();
    }

    public int getFooterCount() {
        return mFooters.size();
    }

    public void setHeaderVisibility(boolean shouldShow) {
        for (View header : mHeaders) {
            header.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        }
    }

    public void setFooterVisibility(boolean shouldShow) {
        for (View footer : mFooters) {
            footer.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
        }
    }

    public boolean isHeader(int viewType) {
        return viewType >= HEADER_VIEW_TYPE && viewType < (HEADER_VIEW_TYPE + mHeaders.size());
    }

    public boolean isFooter(int viewType) {
        return viewType >= FOOTER_VIEW_TYPE && viewType < (FOOTER_VIEW_TYPE + mFooters.size());
    }

    public boolean isEmpty() {
        return items == null || items.size() == 0;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return itemLongClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public void add(Model item) {
        items.add(item);
    }

    public void add(int index, Model item) {
        items.add(index, item);
    }

    public void remove(Model item) {
        items.remove(item);
    }

    public void remove(int index) {
        items.remove(index);
    }

    public void clear() {
        items.clear();
    }

    public void clearNtf() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addNtf(Model item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addNtf(int index, Model item) {
        items.add(index, item);
        notifyDataSetChanged();
    }

    public void removeNtf(Model item) {
        items.remove(item);
        notifyDataSetChanged();
    }

    public void removeNtf(int index) {
        items.remove(index);
        notifyDataSetChanged();
    }

    public View getHeader(int position) {
        return position < mHeaders.size() ? mHeaders.get(position) : null;
    }

    public View getFooter(int i) {
        return i < mFooters.size() ? mFooters.get(i) : null;
    }

    public void setItems(List<Model> items) {
        this.items = items;
    }

    public void setItemsNtf(List<Model> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addAll(List<Model> items) {
        this.items.addAll(items);
    }

    public void addAll(int index, List<Model> items) {
        this.items.addAll(index, items);
    }

    public void addAllNtf(List<Model> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addAllNtf(int index, List<Model> items) {
        this.items.addAll(index, items);
        notifyDataSetChanged();
    }

    public List<Model> getItems() {
        return items;
    }

    public Model getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mHeaders.size()) {
            return HEADER_VIEW_TYPE + position;
        } else if (position < (mHeaders.size() + getActualItemCount())) {
            return getViewType(position - mHeaders.size());
        } else {
            return FOOTER_VIEW_TYPE + position - mHeaders.size() - getActualItemCount();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - HEADER_VIEW_TYPE);
            View headerView = mHeaders.get(whichHeader);
            return new RecyclerView.ViewHolder(headerView) {};
        } else if (isFooter(viewType)) {
            int whichFooter = Math.abs(viewType - FOOTER_VIEW_TYPE);
            View footerView = mFooters.get(whichFooter);
            return new RecyclerView.ViewHolder(footerView) {};
        } else {
            if (inflater == null) {
                this.inflater = LayoutInflater.from(parent.getContext());
            }
            View view = inflater.inflate(getLayoutItem(), parent, false);
            return createViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position >= mHeaders.size() && position < mHeaders.size() + getActualItemCount()) {
            // This is a real position, not a header or footer. Bind it.
            bindItemViewHolder(holder, position - mHeaders.size());
        } else {
            // header or footer => show full row
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
                // handle show all row when load more view appear with StaggeredGridLayoutManager
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void bindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Model model = getItem(position);
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                //noinspection unchecked
                itemClickListener.onItemSelected(holder.itemView, position);
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClickListener(holder.itemView, position);
            }
            return false;
        });
        updateView(model, (ViewHolder) holder, position);
    }

    @Deprecated
    @Override
    public final int getItemCount() {
        return mHeaders.size() + getActualItemCount() + mFooters.size();
    }

    public int getActualItemCount() {
        return items.size();
    }

    protected int getViewType(int position) {
        return 0;
    }

    public int convertToActualItemIndex(int index) {
        return index + mHeaders.size();
    }

    /**
     * Abstract method
     */
    protected abstract int getLayoutItem();
    protected abstract void updateView(Model model, ViewHolder viewHolder, int position);
    protected abstract ViewHolder createViewHolder(View container);

    /**
     * On item clicked event
     */
    public interface OnItemClickListener {
        void onItemSelected(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View view, int position);
    }
}
