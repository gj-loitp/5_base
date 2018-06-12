package vn.loitp.core.loitp.gallery.photos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

/**
 * Created by yahyabayramoglu on 14/04/15.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    public static int COLUMN = 2;
    private Context context;
    private LayoutInflater inflater;
    private int sizeW;
    private int sizeH;

    public PhotosAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        sizeW = LScreenUtil.getScreenWidth();
        sizeH = sizeW / COLUMN * 16 / 9;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_photos_core, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.rootView.getLayoutParams().height = sizeH;
        viewHolder.rootView.requestLayout();

        Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);
        LUIUtil.setProgressBarVisibility(viewHolder.progressBar, View.VISIBLE);
        LImageUtil.load((Activity) context, photo.getUrlO(), viewHolder.iv, viewHolder.progressBar);
        viewHolder.tvSize.setText(photo.getWidthO() + "x" + photo.getHeightO());
        LUIUtil.setTextShadow(viewHolder.tvSize);
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
    }

    @Override
    public int getItemCount() {
        if (PhotosDataCore.getInstance().getPhotoList() == null) {
            return 0;
        }
        return PhotosDataCore.getInstance().getPhotoList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iv;
        public final TextView tvSize;
        public final ProgressBar progressBar;
        public final RelativeLayout rootView;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv);
            tvSize = (TextView) v.findViewById(R.id.tv_size);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            rootView = (RelativeLayout) v.findViewById(R.id.root_view);
            LUIUtil.setColorProgressBar(progressBar, Color.WHITE);
        }
    }

    public interface Callback {
        public void onClick(int pos);

        public void onLongClick(int pos);
    }

    private Callback callback;
}