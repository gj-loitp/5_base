package vn.loitp.app.activity.photos;

/**
 * Created by www.muathu@gmail.com on 1/2/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxViewHolder;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Photo> photoList;

    public PhotosAdapter(Context context, List<Photo> photoList, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.photoList = photoList;
        this.callback = callback;
    }

    public interface Callback {
        public void onClick(Photo photo, int position);

        public void onLastItem(int positionLastItem);
    }

    private Callback callback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_img, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.rootView.setBackgroundColor(AppUtil.getColor(context));
        LImageUtil.load((Activity) context, photoList.get(position).getUrlO(), viewHolder.getBackgroundImage());

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(photoList.get(position), position);
                }
            }
        });

        // # CAUTION:
        // Important to call this method
        viewHolder.getBackgroundImage().reuse();

        if (position == photoList.size() - 1) {
            if (callback != null) {
                callback.onLastItem(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return photoList == null ? 0 : photoList.size();
    }

    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    public static class ViewHolder extends ParallaxViewHolder {
        public RelativeLayout rootView;

        public ViewHolder(View v) {
            super(v);
            rootView = (RelativeLayout) v.findViewById(R.id.root_view);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }
    }
}