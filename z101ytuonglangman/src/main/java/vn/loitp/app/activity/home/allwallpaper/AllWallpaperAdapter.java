package vn.loitp.app.activity.home.allwallpaper;

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
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.data.AlbumData;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxViewHolder;

public class AllWallpaperAdapter extends RecyclerView.Adapter<AllWallpaperAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;

    public AllWallpaperAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    public interface Callback {
        public void onClick(Photoset photoset, int position);
    }

    private Callback callback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_album, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.rootView.setBackgroundColor(AppUtil.getColor(context));
        LImageUtil.load((Activity) context, AlbumData.getInstance().getPhotosetListCategory().get(position).getPrimaryPhotoExtras().getUrlM(), viewHolder.getBackgroundImage());
        String s = AlbumData.getInstance().getPhotosetListCategory().get(position).getTitle().getContent() + " (" + AlbumData.getInstance().getPhotosetListCategory().get(position).getPhotos() + ")";
        viewHolder.tv.setText(s);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(AlbumData.getInstance().getPhotosetListCategory().get(position), position);
                }
            }
        });

        // # CAUTION:
        // Important to call this method
        viewHolder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return AlbumData.getInstance().getPhotosetListCategory().size();
    }

    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    public static class ViewHolder extends ParallaxViewHolder {
        public RelativeLayout rootView;
        public TextView tv;
        public View view;

        public ViewHolder(View v) {
            super(v);
            rootView = (RelativeLayout) v.findViewById(R.id.root_view);
            tv = (TextView) v.findViewById(R.id.tv);
            view = (View) v.findViewById(R.id.view);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }
    }
}