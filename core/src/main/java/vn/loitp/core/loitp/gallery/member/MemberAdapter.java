package vn.loitp.core.loitp.gallery.member;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

/**
 * Created by loitp on 14/04/15.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private boolean isTablet;

    public MemberAdapter(Context context, int numCount, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
        //LLog.d(TAG, "size: " + size);
        this.isTablet = LDeviceUtil.isTablet((Activity) context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_photos_member, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (isTablet) {
            if (position == 0 || position == 1 || position == 2) {
                viewHolder.viewSpaceTop.setVisibility(View.VISIBLE);
                viewHolder.viewSpaceBottom.setVisibility(View.GONE);
            } else if (position == getItemCount() - 1 || position == getItemCount() - 2) {
                viewHolder.viewSpaceTop.setVisibility(View.GONE);
                viewHolder.viewSpaceBottom.setVisibility(View.VISIBLE);
            } else {
                viewHolder.viewSpaceTop.setVisibility(View.GONE);
                viewHolder.viewSpaceBottom.setVisibility(View.GONE);
            }
        } else {
            if (position == 0 || position == 1) {
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
        /*if (position == 0) {
            viewHolder.viewSpaceTop.setVisibility(View.VISIBLE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        } else if (position == getItemCount() - 1) {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.VISIBLE);
        } else {
            viewHolder.viewSpaceTop.setVisibility(View.GONE);
            viewHolder.viewSpaceBottom.setVisibility(View.GONE);
        }*/

        final Photo photo = PhotosDataCore.getInstance().getPhotoList().get(position);

        //LLog.d(TAG, ">>>getUrlO " + photo.getUrlO());
        //LLog.d(TAG, ">>>getFlickrLink640 " + photo.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photo.getFlickrLink1024());

        LImageUtil.load(context, photo.getUrlM(), viewHolder.imageView);

        if (photo.getTitle() == null || photo.getTitle().toLowerCase().startsWith("null")) {
            viewHolder.tvTitle.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(photo.getTitle());
            LUIUtil.setTextShadow(viewHolder.tvTitle);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(photo, position, viewHolder.imageView, viewHolder.tvTitle);
                }
            }
        });
        viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
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
        private TextView tvTitle;
        private ImageView imageView;
        private View viewSpaceTop;
        private View viewSpaceBottom;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            imageView = (ImageView) v.findViewById(R.id.image_view);
            viewSpaceTop = (View) v.findViewById(R.id.view_space_top);
            viewSpaceBottom = (View) v.findViewById(R.id.view_space_bottom);
        }
    }

    public interface Callback {
        public void onClick(Photo photo, int pos, ImageView imageView, TextView textView);

        public void onLongClick(Photo photo, int pos);
    }

    private Callback callback;
}