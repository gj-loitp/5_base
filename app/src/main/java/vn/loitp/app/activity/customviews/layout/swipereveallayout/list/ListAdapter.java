package vn.loitp.app.activity.customviews.layout.swipereveallayout.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.views.LToast;
import com.views.layout.swipereveallayout.LSwipeRevealLayout;
import com.views.layout.swipereveallayout.ViewBinderHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import loitp.basemaster.R;

public class ListAdapter extends ArrayAdapter<String> {
    private final LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper;

    public ListAdapter(Context context, List<String> objects) {
        super(context, R.layout.item_swipe_reveal_layout_list, objects);
        mInflater = LayoutInflater.from(context);
        binderHelper = new ViewBinderHelper();

        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_swipe_reveal_layout_list, parent, false);

            holder = new ViewHolder();
            holder.swipeLayout = convertView.findViewById(R.id.swipe_layout);
            holder.frontView = convertView.findViewById(R.id.front_layout);
            holder.deleteView = convertView.findViewById(R.id.delete_layout);
            holder.textView = convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String item = getItem(position);
        if (item != null) {
            binderHelper.bind(holder.swipeLayout, item);

            holder.textView.setText(item);
            holder.deleteView.setOnClickListener(v -> remove(item));
            holder.frontView.setOnClickListener(view -> {
                String displayText = "" + item + " clicked";
                LToast.showShort(getContext(), displayText);
            });
        }

        return convertView;
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder {
        LSwipeRevealLayout swipeLayout;
        View frontView;
        View deleteView;
        TextView textView;
    }
}
