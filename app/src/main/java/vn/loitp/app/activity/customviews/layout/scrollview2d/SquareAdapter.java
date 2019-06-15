package vn.loitp.app.activity.customviews.layout.scrollview2d;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import loitp.basemaster.R;

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.SquareViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private int sizeWViewHolder;
    private int sizeHViewHolder;

    public interface Callback {
        void onClick(Square square, int position);

        void onLongClick(Square square, int position);
    }

    private Context context;
    private Callback callback;
    private List<Square> squareList;

    public class SquareViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public RelativeLayout rootView;

        SquareViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.tv);
            rootView = view.findViewById(R.id.root_view);
        }
    }

    SquareAdapter(@NonNull final Context context, @Nullable final List<Square> squareList, final int sizeWViewHolder, final int sizeHViewHolder, @NonNull Callback callback) {
        this.context = context;
        this.squareList = squareList;
        this.sizeWViewHolder = sizeWViewHolder;
        this.sizeHViewHolder = sizeHViewHolder;
        this.callback = callback;
    }

    @NonNull
    @Override
    public SquareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square, parent, false);
        itemView.getLayoutParams().width = sizeWViewHolder;
        itemView.getLayoutParams().height = sizeHViewHolder;
        return new SquareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SquareViewHolder holder, int position) {
        final Square square = squareList.get(position);
        holder.tv.setText(square.getName());
        holder.rootView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(square, position);
            }
        });
        holder.rootView.setOnLongClickListener(v -> {
            if (callback != null) {
                callback.onLongClick(square, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        if (squareList == null) {
            return 0;
        }
        return squareList.size();
    }
}