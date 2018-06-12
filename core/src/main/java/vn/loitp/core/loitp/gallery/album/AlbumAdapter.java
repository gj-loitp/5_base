package vn.loitp.core.loitp.gallery.album;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LDateUtils;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by yahyabayramoglu on 14/04/15.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Photoset> photosetList;

    public AlbumAdapter(Context context, List<Photoset> photosetList, Callback callback) {
        this.context = context;
        this.photosetList = photosetList;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_album_core, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Photoset photoset = photosetList.get(position);
        //viewHolder.avLoadingIndicatorView.smoothToShow();
        viewHolder.avLoadingIndicatorView.show();
        LImageUtil.load((Activity) context, photoset.getPrimaryPhotoExtras().getUrlO(), viewHolder.iv, viewHolder.avLoadingIndicatorView);

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
        public final AVLoadingIndicatorView avLoadingIndicatorView;
        public final RelativeLayout rootView;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv);
            tvLabel = (TextView) v.findViewById(R.id.tv_label);
            tvUpdate = (TextView) v.findViewById(R.id.tv_update);
            tvNumber = (TextView) v.findViewById(R.id.tv_number);
            avLoadingIndicatorView = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
            rootView = (RelativeLayout) v.findViewById(R.id.root_view);
        }
    }

    public interface Callback {
        public void onClick(int pos);

        public void onLongClick(int pos);
    }

    private Callback callback;
}