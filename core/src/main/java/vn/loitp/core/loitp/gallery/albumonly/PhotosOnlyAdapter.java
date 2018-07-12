package vn.loitp.core.loitp.gallery.albumonly;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.imageview.bigimageview.LBigImageView;

/**
 * Created by yahyabayramoglu on 14/04/15.
 */
public class PhotosOnlyAdapter extends RecyclerView.Adapter<PhotosOnlyAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;

    public PhotosOnlyAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_photos_core, viewGroup, false));
    }

    @Override
    public void onViewRecycled(@NonNull PhotosOnlyAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        //LLog.d(TAG, "onViewRecycled");
        //LImageUtil.clear(context, holder.iv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //viewHolder.rootView.getLayoutParams().height = sizeH;
        //viewHolder.rootView.requestLayout();

        final Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);

        //LLog.d(TAG, ">>>getUrlO " + photo.getUrlO());
        //LLog.d(TAG, ">>>getFlickrLink640 " + photo.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photo.getFlickrLink1024());

        //LImageUtil.load(context, photo.getUrlO(), viewHolder.iv, viewHolder.progressBar, sizeW, sizeH);
        viewHolder.lBigImageView.setColorProgressBar(Color.WHITE);
        viewHolder.lBigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        viewHolder.lBigImageView.setZoomEnable(false);
        viewHolder.lBigImageView.load(photo.getUrlO());
        viewHolder.lBigImageView.setCallback(new LBigImageView.Callback() {
            @Override
            public void onSuccess(File image) {
                viewHolder.lBigImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                viewHolder.lBigImageView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                viewHolder.lBigImageView.requestLayout();
            }

            @Override
            public void onFail(Exception error) {
                viewHolder.lBigImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                viewHolder.lBigImageView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                viewHolder.lBigImageView.requestLayout();
            }
        });

        if (photo.getTitle() == null || photo.getTitle().toLowerCase().startsWith("null")) {
            viewHolder.tvTitle.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(photo.getTitle());
            LUIUtil.setTextShadow(viewHolder.tvTitle);
        }
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(photo, position);
                }
            }
        });
        viewHolder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClick(photo, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (PhotosDataCore.getInstance().getPhotoList() == null) {
            return 0;
        }
        return PhotosDataCore.getInstance().getPhotoList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvTitle;
        public final LinearLayout rootView;
        private LBigImageView lBigImageView;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            rootView = (LinearLayout) v.findViewById(R.id.root_view);
            lBigImageView = (LBigImageView) v.findViewById(R.id.l_big_image_view);
        }
    }

    public interface Callback {
        public void onClick(Photo photo, int pos);

        public void onLongClick(Photo photo, int pos);
    }

    private Callback callback;
}