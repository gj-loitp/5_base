package vn.puresolutions.livestar.adapter.listview;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.puresolutions.livestar.core.api.model.BaseModel;

/**
 * @author hoangphu
 * @since 7/31/16
 */
public abstract class ItemAdapter<Model extends BaseModel, ViewHolder> extends BaseAdapter {
    protected List<Model> items;
    private LayoutInflater inflater;

    public ItemAdapter() {
        this(new ArrayList<Model>());
    }

    @SuppressWarnings("unchecked")
    public ItemAdapter(List<Model> items) {
        this.items = items;
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

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Model getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            if (inflater == null) {
                this.inflater = LayoutInflater.from(parent.getContext());
            }
            view = inflater.inflate(getLayoutResource(), parent, false);
            holder = createViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        updateView(parent.getContext(), getItem(position), holder, position);
        return view;
    }

    /**
     * Abstract method
     */
    protected abstract int getLayoutResource();
    protected abstract ViewHolder createViewHolder(View container);
    protected abstract void updateView(Context context, Model model, ViewHolder holder, int position);
}
