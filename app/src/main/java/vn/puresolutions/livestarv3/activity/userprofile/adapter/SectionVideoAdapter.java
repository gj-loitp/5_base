package vn.puresolutions.livestarv3.activity.userprofile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;
import vn.puresolutions.livestarv3.activity.userprofile.model.SingleVideo;

/**
 * File created on 8/10/2017.
 *
 * @author anhdv
 */

public class SectionVideoAdapter extends RecyclerView.Adapter<SectionVideoAdapter.SectionVideoHolder> {
    private Context mContext;
    private ArrayList<SingleVideo> lstVideo;
    private Callback callback;

    public SectionVideoAdapter(Context mContext, ArrayList<SingleVideo> lstVideo, Callback callback) {
        this.mContext = mContext;
        this.lstVideo = lstVideo;
        this.callback = callback;
    }

    @Override
    public SectionVideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_single_video, null);
        SectionVideoHolder sh = new SectionVideoHolder(view);
        return sh;
    }

    @Override
    public void onBindViewHolder(SectionVideoHolder holder, int position) {
        LImageUtils.loadImage(holder.sdvAvatar,lstVideo.get(position).getUrlAvatar());
        holder.tvName.setText(lstVideo.get(position).getVideoName());
        holder.ltvHeart.setText(String.valueOf(lstVideo.get(position).getCountHeart()));
        holder.ltvHeart.setImage(R.drawable.ic_temp_avatar);
        holder.ltvHeart.setImageSize(20);
        holder.ltvView.setText(String.valueOf(lstVideo.get(position).getCountView()));
        holder.ltvView.setImage(R.drawable.ic_temp_avatar);
        holder.ltvView.setImageSize(20);

    }

    @Override
    public int getItemCount() {
        return (lstVideo != null ? lstVideo.size() : 0);
    }

    public interface Callback {
        public void onClick();
    }

    public class SectionVideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdvVideoManager_Avatar)
        SimpleDraweeView sdvAvatar;
        @BindView(R.id.tvVideoManager_Name)
        TextView tvName;
        @BindView(R.id.ltvVideoManager_View)
        LTextViewHorizontal ltvView;
        @BindView(R.id.ltvVideoManager_Heart)
        LTextViewHorizontal ltvHeart;

        public SectionVideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            if (callback != null) {
                callback.onClick();
            }
        }
    }
}
