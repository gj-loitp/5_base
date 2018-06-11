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
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;

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
        LImageUtil.load((Activity) context, photoset.getPrimaryPhotoExtras().getUrlO(), viewHolder.iv);

        viewHolder.textView.setText(photoset.getTitle().getContent());
        LUIUtil.setTextShadow(viewHolder.textView);

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
        public final TextView textView;
        public final RelativeLayout rootView;

        public ViewHolder(View v) {
            super(v);
            iv = (ImageView) v.findViewById(R.id.iv);
            textView = (TextView) v.findViewById(R.id.label);
            rootView = (RelativeLayout) v.findViewById(R.id.root_view);
        }
    }

    public interface Callback {
        public void onClick(int pos);

        public void onLongClick(int pos);
    }

    private Callback callback;
}