package vn.loitp.core.loitp.gallery.photos;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.loitp.gallery.album.AlbumAdapter;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

/**
 * Created by yahyabayramoglu on 14/04/15.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    public static int COLUMN = 2;
    private Context context;
    private LayoutInflater inflater;
    private int sizeW;
    private int sizeH;

    public PhotosAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        sizeW = LScreenUtil.getScreenWidth() / COLUMN;
        sizeH = sizeW * 16 / 9;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_photos_core, viewGroup, false));
    }

    @Override
    public void onViewRecycled(@NonNull PhotosAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        //LLog.d(TAG, "onViewRecycled");
        LImageUtil.clear(context, holder.iv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.rootView.getLayoutParams().height = sizeH;
        viewHolder.rootView.requestLayout();

        final Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);
        LUIUtil.setProgressBarVisibility(viewHolder.progressBar, View.VISIBLE);

        int sizeS = photo.getWidthS();
        int sizeM = photo.getWidthM();
        int sizeO = photo.getWidthO();
        LLog.d(TAG, sizeW + " - " + sizeS + "/" + sizeM + "/" + sizeO);
        if (sizeW < sizeS) {
            //LLog.d(TAG, "sizeW < sizeS " + sizeW + " < " + sizeS);
            LImageUtil.load(context, photo.getUrlS(), viewHolder.iv, viewHolder.progressBar, sizeW, sizeH);
        } else if (sizeW < sizeM) {
            //LLog.d(TAG, "sizeW < sizeM " + sizeW + " < " + sizeM);
            LImageUtil.load(context, photo.getUrlM(), viewHolder.iv, viewHolder.progressBar, sizeW, sizeH);
        } else if (sizeW < sizeO) {
            //LLog.d(TAG, "sizeW < sizeO " + sizeW + " < " + sizeO);
            LImageUtil.load(context, photo.getUrlO(), viewHolder.iv, viewHolder.progressBar, sizeW, sizeH);
        } else {
            //LLog.d(TAG, "sizeW > " + sizeW + " > " + sizeO);
            LImageUtil.load(context, photo.getUrlO(), viewHolder.iv, viewHolder.progressBar);
        }

        viewHolder.tvSize.setText(photo.getWidthO() + "x" + photo.getHeightO());
        LUIUtil.setTextShadow(viewHolder.tvSize);
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

        if (position == 0 || position == 1) {
            viewHolder.viewSpaceTop.setVisibility(View.VISIBLE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        } else if (position == getItemCount() - 1 || position == getItemCount() - 2) {
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
        public final ImageView iv;
        public final TextView tvSize;
        public final ProgressBar progressBar;
        public final LinearLayout rootView;
        public final View viewSpaceTop;
        public final View viewSpaceBottom;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv);
            tvSize = (TextView) v.findViewById(R.id.tv_size);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            rootView = (LinearLayout) v.findViewById(R.id.root_view);
            viewSpaceTop = (View) v.findViewById(R.id.view_space_top);
            viewSpaceBottom = (View) v.findViewById(R.id.view_space_bottom);
            LUIUtil.setColorProgressBar(progressBar, Color.WHITE);
        }
    }

    public interface Callback {
        public void onClick(Photo photo, int pos);

        public void onLongClick(Photo photo, int pos);
    }

    private Callback callback;
}