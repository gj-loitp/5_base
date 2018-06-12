package vn.loitp.core.loitp.gallery.album;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LDateUtils;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;

/**
 * Created by yahyabayramoglu on 14/04/15.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_album_core, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.rootView.getLayoutParams().height = sizeH;
        viewHolder.rootView.requestLayout();

        Photoset photoset = photosetList.get(position);
        //viewHolder.avLoadingIndicatorView.smoothToShow();
        LUIUtil.setProgressBarVisibility(viewHolder.progressBar, View.VISIBLE);
        LImageUtil.load((Activity) context, photoset.getPrimaryPhotoExtras().getUrlO(), viewHolder.iv, viewHolder.progressBar);

        viewHolder.tvLabel.setText(photoset.getTitle().getContent() + "");

        String update = LDateUtils.getDateCurrentTimeZone(photoset.getDateUpdate(), "dd-MM-yyyy HH:mm:ss");
        viewHolder.tvUpdate.setText(update);

        viewHolder.tvNumber.setText(photoset.getPhotos() + "");

        LUIUtil.setTextShadow(viewHolder.tvLabel);
        LUIUtil.setTextShadow(viewHolder.tvUpdate);
        LUIUtil.setTextShadow(viewHolder.tvNumber);

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(position);
                }
            }
        });
        viewHolder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(position);
                }
                return true;
            }
        });

        if (position == 0) {
            viewHolder.viewSpaceTop.setVisibility(View.VISIBLE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        } else if (position == getItemCount() - 1) {
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
        public final ProgressBar progressBar;
        public final LinearLayout rootView;
        public final View viewSpaceTop;
        public final View viewSpaceBottom;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv);
            tvLabel = (TextView) v.findViewById(R.id.tv_label);
            tvUpdate = (TextView) v.findViewById(R.id.tv_update);
            tvNumber = (TextView) v.findViewById(R.id.tv_number);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            rootView = (LinearLayout) v.findViewById(R.id.root_view);
            viewSpaceTop = (View) v.findViewById(R.id.view_space_top);
            viewSpaceBottom = (View) v.findViewById(R.id.view_space_bottom);

            LUIUtil.setColorProgressBar(progressBar, Color.WHITE);
        }
    }

    public interface Callback {
        public void onClick(int pos);

        public void onLongClick(int pos);
    }

    private Callback callback;
}