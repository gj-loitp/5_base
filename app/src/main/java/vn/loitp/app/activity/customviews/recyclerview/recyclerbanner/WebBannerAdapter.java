package vn.loitp.app.activity.customviews.recyclerview.recyclerbanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.views.recyclerview.banner.BannerLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.loitp.app.R;

/**
 * Created by test on 2017/11/22.
 */
public class WebBannerAdapter extends RecyclerView.Adapter<WebBannerAdapter.MzViewHolder> {
    private Context context;
    private List<String> urlList;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

    WebBannerAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @NotNull
    @Override
    public WebBannerAdapter.MzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MzViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull WebBannerAdapter.MzViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty()) {
            return;
        }
        final int P = position % urlList.size();
        String url = urlList.get(P);
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


    class MzViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MzViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}
