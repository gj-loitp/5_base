package com.core.loitp.gallery.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LDateUtils;
import com.core.utilities.LImageUtil;
import com.core.utilities.LScreenUtil;
import com.core.utilities.LUIUtil;

import java.util.List;

import loitp.core.R;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;

/**
 * Created by loitp on 14/04/15.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private List<Photoset> photosetList;
    private int sizeW;
    private int sizeH;

    public AlbumAdapter(Context context, List<Photoset> photosetList, Callback callback) {
        this.context = context;
        this.photosetList = photosetList;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        sizeW = LScreenUtil.getScreenWidth();
        sizeH = sizeW;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_album_core, viewGroup, false));
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        //LLog.d(TAG, "onViewRecycled");
        LImageUtil.clear(context, holder.iv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.rootView.getLayoutParams().height = sizeH;
        viewHolder.rootView.requestLayout();

        final Photoset photoset = photosetList.get(position);
        //LUIUtil.setProgressBarVisibility(viewHolder.progressBar, View.VISIBLE);

        //LLog.d(TAG, ">>>getUrlO " + photoset.getPrimaryPhotoExtras().getUrlO());
        //LLog.d(TAG, ">>>getFlickrLink640 " + photoset.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photoset.getFlickrLink1024());

        //LImageUtil.load(context, photoset.getFlickrLink1024(), viewHolder.iv, viewHolder.progressBar);
        LImageUtil.loadNoAmin(context, photoset.getFlickrLinkO(), photoset.getFlickrLinkM(), viewHolder.iv);

        viewHolder.tvLabel.setText(photoset.getTitle().getContent());

        String update = LDateUtils.getDateCurrentTimeZone(photoset.getDateUpdate(), "dd-MM-yyyy HH:mm:ss");
        viewHolder.tvUpdate.setText(update);

        viewHolder.tvNumber.setText(photoset.getPhotos());

        LUIUtil.setTextShadow(viewHolder.tvLabel);
        LUIUtil.setTextShadow(viewHolder.tvUpdate);
        LUIUtil.setTextShadow(viewHolder.tvNumber);

        viewHolder.rootView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(position);
            }
        });
        viewHolder.rootView.setOnLongClickListener(v -> {
            if (callback != null) {
                callback.onLongClick(position);
            }
            return true;
        });

        if (position == 0) {
            viewHolder.viewSpaceTop.setVisibility(View.VISIBLE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        } else if (position == (getItemCount() - 1)) {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.VISIBLE);
        } else {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (photosetList == null) {
            return 0;
        }
        return photosetList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final TextView tvLabel;
        public final TextView tvUpdate;
        public final TextView tvNumber;
        //public final ProgressBar progressBar;
        public final LinearLayout rootView;
        public final View viewSpaceTop;
        public final View viewSpaceBottom;

        public ViewHolder(View v) {
            super(v);
            iv = v.findViewById(R.id.iv);
            tvLabel = v.findViewById(R.id.tv_label);
            tvUpdate = v.findViewById(R.id.tv_update);
            tvNumber = v.findViewById(R.id.tv_number);
            //progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            rootView = v.findViewById(R.id.root_view);
            viewSpaceTop = v.findViewById(R.id.view_space_top);
            viewSpaceBottom = v.findViewById(R.id.view_space_bottom);

            //LUIUtil.setColorProgressBar(progressBar, Color.WHITE);
        }
    }

    public interface Callback {
        void onClick(int pos);

        void onLongClick(int pos);
    }

    private Callback callback;
}