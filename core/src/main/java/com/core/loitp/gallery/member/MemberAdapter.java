package com.core.loitp.gallery.member;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.loitp.gallery.photos.PhotosDataCore;
import com.core.utilities.LDeviceUtil;
import com.core.utilities.LImageUtil;
import com.core.utilities.LScreenUtil;
import com.core.utilities.LUIUtil;
import com.restapi.flickr.model.photosetgetphotos.Photo;

import loitp.core.R;

/**
 * Created by loitp on 14/04/15.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private boolean isTablet;
    private int sizeW;
    private int sizeH;

    public MemberAdapter(final Context context, final int numCount, final Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        this.sizeW = LScreenUtil.INSTANCE.getScreenWidth() / numCount;
        this.sizeH = LScreenUtil.INSTANCE.getScreenHeight() / numCount;
        this.isTablet = LDeviceUtil.Companion.isTablet((Activity) context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_photos_member, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.fl.getLayoutParams().width = sizeW;
        viewHolder.fl.getLayoutParams().height = sizeH;
        viewHolder.fl.requestLayout();
        final Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);

        //LLog.d(TAG, ">>>getUrlO " + photo.getUrlO());
        //LLog.d(TAG, ">>>getFlickrLink640 " + photo.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photo.getFlickrLink1024());

        //LImageUtil.load(context, photo.getUrlM(), viewHolder.imageView);
        LImageUtil.INSTANCE.loadNoAmin(context, photo.getUrlO(), photo.getUrlS(), viewHolder.imageView);

        if (photo.getTitle() == null || photo.getTitle().toLowerCase().startsWith("null")) {
            viewHolder.tvTitle.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(photo.getTitle());
            LUIUtil.INSTANCE.setTextShadow(viewHolder.tvTitle);
        }
        viewHolder.imageView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(photo, position, viewHolder.imageView, viewHolder.tvTitle);
            }
        });
        viewHolder.imageView.setOnLongClickListener(v -> {
            if (callback != null) {
                callback.onLongClick(photo, position);
            }
            return true;
        });

        if (position == 0 || position == 1) {
            viewHolder.viewSpaceTop.setVisibility(View.VISIBLE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        } else if (getItemCount() % 2 == 0 && (position == (getItemCount() - 1) || position == (getItemCount() - 2))) {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.VISIBLE);
        } else if (getItemCount() % 2 != 0 && position == (getItemCount() - 1)) {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.VISIBLE);
        } else {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (PhotosDataCore.getInstance().getPhotoList() == null) {
            return 0;
        }
        return PhotosDataCore.getInstance().getPhotoList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout fl;
        private TextView tvTitle;
        private ImageView imageView;
        private View viewSpaceTop;
        private View viewSpaceBottom;

        public ViewHolder(View v) {
            super(v);
            fl = v.findViewById(R.id.fl);
            tvTitle = v.findViewById(R.id.tv_title);
            imageView = v.findViewById(R.id.image_view);
            viewSpaceTop = v.findViewById(R.id.view_space_top);
            viewSpaceBottom = v.findViewById(R.id.view_space_bottom);
        }
    }

    public interface Callback {
        void onClick(Photo photo, int pos, ImageView imageView, TextView textView);

        void onLongClick(Photo photo, int pos);
    }

    private Callback callback;
}