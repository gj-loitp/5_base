package vn.puresolutions.livestarv3.activity.userprofile.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.userprofile.model.SectionVideo;

/**
 * File created on 8/10/2017.
 *
 * @author anhdv
 */

public class VideoManagerAdapter extends RecyclerView.Adapter<VideoManagerAdapter.VideoManagerHolder> {
    private ArrayList<SectionVideo> lstSectionVideo;
    private Context mContext;
    private Callback callback;

    public VideoManagerAdapter(Context mContext, ArrayList<SectionVideo> lstSectionVideo, Callback callback) {
        this.mContext = mContext;
        this.lstSectionVideo = lstSectionVideo;
        this.callback = callback;

    }

    @Override
    public VideoManagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_list_video, null);
        VideoManagerHolder vh = new VideoManagerHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VideoManagerHolder holder, int position) {
        holder.tvDate.setText(lstSectionVideo.get(position).getDate());
        ArrayList lstSingleVideo = lstSectionVideo.get(position).getLstSingleVideo();

        SectionVideoAdapter sectionVideoAdapter = new SectionVideoAdapter(mContext, lstSingleVideo, new SectionVideoAdapter.Callback() {
            @Override
            public void onClick() {
                //TODO
                //callback.onClick();
            }
        });
        holder.rvSectionVideo.setHasFixedSize(true);
        holder.rvSectionVideo.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.rvSectionVideo.setAdapter(sectionVideoAdapter);
    }

    @Override
    public int getItemCount() {
        return (null != lstSectionVideo ? lstSectionVideo.size() : 0);
    }


    public interface Callback {
        public void onClick();
    }

    public class VideoManagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvVideoCreatedDate)
        TextView tvDate;
        @BindView(R.id.rvSectionVideo)
        RecyclerView rvSectionVideo;

        public VideoManagerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
