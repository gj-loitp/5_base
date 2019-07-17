package vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.views.recyclerview.banner.BannerLayout;

import java.util.List;

import loitp.basemaster.R;

/**
 * Created by test on 2017/11/22.
 */
public class VGReVAdapter extends RecyclerView.Adapter<VGReVAdapter.HViewHolder> {
    private Context context;
    private List<String> urlList;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

    VGReVAdapter(@NonNull final Context context, @Nullable final List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @NonNull
    @Override
    public HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_vgre_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty()) {
            return;
        }
        final int P = position % urlList.size();
        final String url = urlList.get(P);
        ImageView img = holder.imageView;
        Glide.with(context).load(url).into(img);
        img.setOnClickListener(v -> {
            if (onBannerItemClickListener != null) {
                onBannerItemClickListener.onItemClick(P);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (urlList != null) {
            return urlList.size();
        }
        return 0;
    }

    class HViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        HViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}
