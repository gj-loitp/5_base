package vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.views.recyclerview.banner.BannerLayout;

/**
 * Created by test on 2017/11/22.
 */
public class VGReVAdapter extends RecyclerView.Adapter<VGReVAdapter.HViewHolder> {
    private Context context;
    private List<String> urlList;
    private BannerLayout.OnBannerItemClickListener onBannerItemClickListener;

    public VGReVAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    public void setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_vgre_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, final int position) {
        if (urlList == null || urlList.isEmpty()) {
            return;
        }
        final int P = position % urlList.size();
        String url = urlList.get(P);
        ImageView img = (ImageView) holder.imageView;
        Glide.with(context).load(url).into(img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onItemClick(P);
                }
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


    public class HViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        HViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
