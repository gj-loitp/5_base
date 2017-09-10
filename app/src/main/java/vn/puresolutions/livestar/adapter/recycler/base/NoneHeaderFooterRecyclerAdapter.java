package vn.puresolutions.livestar.adapter.recycler.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.puresolutions.livestar.core.api.model.BaseModel;

/**
 * Created by Phu Tran on 8/10/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 *
 * NOTE this adapter doesn't support header and footer view
 */
abstract class NoneHeaderFooterRecyclerAdapter<Model extends BaseModel, ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerAdapter<Model, ViewHolder>  {

    public NoneHeaderFooterRecyclerAdapter() {
        super();
    }

    public NoneHeaderFooterRecyclerAdapter(List<Model> items) {
        super(items);
    }

    @Deprecated
    public boolean isHeader(int viewType) {
        throw new IllegalArgumentException("This adapter isn't support header and footer");
    }

    @Deprecated
    public boolean isFooter(int viewType) {
        throw new IllegalArgumentException("This adapter isn't support header and footer");
    }

    @Deprecated
    public void setHeaderVisibility(boolean shouldShow) {
        throw new IllegalArgumentException("This adapter isn't support header and footer");
    }

    @Deprecated
    public void setFooterVisibility(boolean shouldShow) {
        throw new IllegalArgumentException("This adapter isn't support header and footer");
    }

    @Deprecated
    public View getHeader(int position) {
        throw new IllegalArgumentException("This adapter isn't support header and footer");
    }

    @Deprecated
    public View getFooter(int i) {
        throw new IllegalArgumentException("This adapter isn't support header and footer");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindItemViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            this.inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(getLayoutItem(), parent, false);
        return createViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }
}
