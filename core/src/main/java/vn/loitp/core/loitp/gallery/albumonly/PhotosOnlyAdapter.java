package vn.loitp.core.loitp.gallery.albumonly;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

/**
 * Created by loitp on 14/04/15.
 */
public class PhotosOnlyAdapter extends RecyclerView.Adapter<PhotosOnlyAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private int screenW;

    public PhotosOnlyAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        screenW = LScreenUtil.getScreenWidth();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_photos_core_only, viewGroup, false));
    }

    @Override
    public void onViewRecycled(@NonNull PhotosOnlyAdapter.ViewHolder holder) {
        super.onViewRecycled(holder);
        //LLog.d(TAG, "onViewRecycled");
        holder.bigImageView.cancel();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);
        int height = photo.getHeightO() * screenW / photo.getWidthO();
        //LLog.d(TAG, photo.getWidthO() + "x" + photo.getHeightO() + "->" + screenW + "x" + height);

        //viewHolder.rootView.setBackgroundColor(LStoreUtil.getRandomColorLight());

        viewHolder.bigImageView.getLayoutParams().width = screenW;
        viewHolder.bigImageView.getLayoutParams().height = height;
        viewHolder.bigImageView.requestLayout();

        viewHolder.bigImageView.showImage(Uri.parse(photo.getUrlS()), Uri.parse(photo.getUrlO()));
        viewHolder.bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {
            }

            @Override
            public void onCacheMiss(int imageType, File image) {
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(File image) {
                if (viewHolder != null) {
                    if (viewHolder.bigImageView.getSSIV() != null) {
                        viewHolder.bigImageView.getSSIV().setZoomEnabled(false);
                    }
                    //if (viewHolder.rootView != null) {
                    //    viewHolder.rootView.setBackgroundColor(Color.TRANSPARENT);
                    //}
                }
            }

            @Override
            public void onFail(Exception error) {
            }
        });
        if (photo.getTitle() == null || photo.getTitle().toLowerCase().startsWith("null")) {
            viewHolder.tvTitle.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(photo.getTitle());
            LUIUtil.setTextShadow(viewHolder.tvTitle);
        }
        viewHolder.bigImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LLog.d(TAG, "setOnClickListener");
                LAnimationUtil.play(viewHolder.bigImageView, Techniques.Pulse);
                if (callback != null) {
                    callback.onClick(photo, position);
                }
            }
        });
        viewHolder.bigImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LLog.d(TAG, "onLongClick");
                LAnimationUtil.play(viewHolder.bigImageView, Techniques.Pulse);
                if (callback != null) {
                    callback.onLongClick(photo, position);
                }
                return true;
            }
        });
        viewHolder.btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(v, Techniques.Flash);
                if (callback != null) {
                    callback.onClickDownload(photo, position);
                }
            }
        });
        viewHolder.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(v, Techniques.Flash);
                if (callback != null) {
                    callback.onClickShare(photo, position);
                }
            }
        });
        viewHolder.btReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(v, Techniques.Flash);
                if (callback != null) {
                    callback.onClickReport(photo, position);
                }
            }
        });
        viewHolder.btCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(v, Techniques.Flash);
                if (callback != null) {
                    callback.onClickCmt(photo, position);
                }
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
        private TextView tvTitle;
        private LinearLayout rootView;
        private BigImageView bigImageView;
        private FloatingActionButton btDownload;
        private FloatingActionButton btShare;
        private FloatingActionButton btReport;
        private FloatingActionButton btCmt;
        private LinearLayout llControl;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            rootView = (LinearLayout) v.findViewById(R.id.root_view);
            bigImageView = (BigImageView) v.findViewById(R.id.biv);
            btDownload = (FloatingActionButton) v.findViewById(R.id.bt_download);
            btShare = (FloatingActionButton) v.findViewById(R.id.bt_share);
            btReport = (FloatingActionButton) v.findViewById(R.id.bt_report);
            btCmt = (FloatingActionButton) v.findViewById(R.id.bt_cmt);
            llControl = (LinearLayout) v.findViewById(R.id.ll_control);
        }
    }

    public interface Callback {
        public void onClick(Photo photo, int pos);

        public void onLongClick(Photo photo, int pos);

        public void onClickDownload(Photo photo, int pos);

        public void onClickShare(Photo photo, int pos);

        public void onClickReport(Photo photo, int pos);

        public void onClickCmt(Photo photo, int pos);
    }

    private Callback callback;
}