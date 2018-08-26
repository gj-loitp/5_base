package vn.loitp.core.loitp.gallery.albumonly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.imageview.bigimageview.LBigImageView;

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
        holder.lBigImageView.clear();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        //viewHolder.rootView.getLayoutParams().height = sizeH;
        //viewHolder.rootView.requestLayout();

        //viewHolder.llControl.setVisibility(View.GONE);

        final Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);

        int height = photo.getHeightO() * screenW / photo.getWidthO();
        //LLog.d(TAG, photo.getWidthO() + "x" + photo.getHeightO() + "->" + screenW + "x" + height);

        //viewHolder.tvTitle.setText(photo.getWidthO() + "x" + photo.getHeightO() + "->" + screenW + "x" + height);

        //LLog.d(TAG, ">>>getFlickrLink640 " + photo.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photo.getFlickrLink1024());

        viewHolder.lBigImageView.getLayoutParams().width = screenW;
        viewHolder.lBigImageView.getLayoutParams().height = height;
        viewHolder.lBigImageView.requestLayout();

        //LImageUtil.load(context, photo.getUrlO(), viewHolder.iv, viewHolder.progressBar, sizeW, sizeH);
        //viewHolder.lBigImageView.setBackgroundColor(LStoreUtil.getRandomColor());
        viewHolder.lBigImageView.setColorProgressBar(ContextCompat.getColor(context, R.color.White));
        viewHolder.lBigImageView.setColorProgressTextView(ContextCompat.getColor(context, R.color.White));
        viewHolder.lBigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CUSTOM);
        viewHolder.lBigImageView.setZoomEnable(false);
        viewHolder.lBigImageView.load(photo.getUrlO());
        viewHolder.lBigImageView.setCallback(new LBigImageView.Callback() {
            @Override
            public void onSuccess(File image) {
                viewHolder.lBigImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                viewHolder.lBigImageView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                viewHolder.lBigImageView.requestLayout();
                /*LUIUtil.setDelay(300, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        viewHolder.llControl.setVisibility(View.VISIBLE);
                    }
                });*/
            }

            @Override
            public void onFail(Exception error) {
                viewHolder.lBigImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                viewHolder.lBigImageView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                viewHolder.lBigImageView.requestLayout();
                /*LUIUtil.setDelay(300, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        viewHolder.llControl.setVisibility(View.VISIBLE);
                    }
                });*/
            }
        });

        if (photo.getTitle() == null || photo.getTitle().toLowerCase().startsWith("null")) {
            viewHolder.tvTitle.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(photo.getTitle());
            LUIUtil.setTextShadow(viewHolder.tvTitle);
        }

        viewHolder.lBigImageView.getSSIV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LLog.d(TAG, "setOnClickListener");
                LAnimationUtil.play(viewHolder.lBigImageView, Techniques.Pulse);
                if (callback != null) {
                    callback.onClick(photo, position);
                }
            }
        });
        viewHolder.lBigImageView.getSSIV().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //LLog.d(TAG, "onLongClick");
                LAnimationUtil.play(viewHolder.lBigImageView, Techniques.Pulse);
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
        private LBigImageView lBigImageView;
        private ImageView btDownload;
        private ImageView btShare;
        private ImageView btReport;
        private ImageView btCmt;
        private LinearLayout llControl;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            rootView = (LinearLayout) v.findViewById(R.id.root_view);
            lBigImageView = (LBigImageView) v.findViewById(R.id.l_big_image_view);
            btDownload = (ImageView) v.findViewById(R.id.bt_download);
            btShare = (ImageView) v.findViewById(R.id.bt_share);
            btReport = (ImageView) v.findViewById(R.id.bt_report);
            btCmt = (ImageView) v.findViewById(R.id.bt_cmt);
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