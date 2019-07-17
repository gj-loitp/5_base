package vn.loitp.app.activity.customviews.recyclerview.overflyingrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.views.LToast;

import loitp.basemaster.R;

public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.ViewHolder> {
    private int[] images = {R.drawable.iv, R.drawable.buildings, R.drawable.bus,
            R.drawable.plane, R.drawable.iv, R.drawable.buildings, R.drawable.iv,
            R.drawable.buildings, R.drawable.iv, R.drawable.buildings};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LToast.INSTANCE.show(v.getContext(), "Click " + v.getTag());
                }
            });
        }
    }
}
