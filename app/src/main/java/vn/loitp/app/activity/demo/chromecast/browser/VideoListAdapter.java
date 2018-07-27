package vn.loitp.app.activity.demo.chromecast.browser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;

import java.util.List;

import loitp.basemaster.R;

/**
 * An {@link ArrayAdapter} to populate the list of videos.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private static final float ASPECT_RATIO = 9f / 16f;
    private final ItemClickListener mClickListener;
    private final Context mAppContext;
    private List<MediaInfo> videos;

    public VideoListAdapter(ItemClickListener clickListener, Context context) {
        mClickListener = clickListener;
        mAppContext = context.getApplicationContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(R.layout.gg_chromecast_item, viewGroup, false);
        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final MediaInfo mediaInfo = videos.get(position);
        MediaMetadata mediaInfoMetadata = mediaInfo.getMetadata();
        viewHolder.setTitle(mediaInfoMetadata.getString(MediaMetadata.KEY_TITLE));
        viewHolder.setDescription(mediaInfoMetadata.getString(MediaMetadata.KEY_SUBTITLE));
        viewHolder.setImage(mediaInfoMetadata.getImages().get(0).getUrl().toString());

        viewHolder.mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.itemClicked(view, mediaInfo, position);
            }
        });
        viewHolder.mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.itemClicked(view, mediaInfo, position);
            }
        });

        viewHolder.mTextContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.itemClicked(view, mediaInfo, position);
            }
        });
        CastSession castSession = CastContext.getSharedInstance(mAppContext).getSessionManager().getCurrentCastSession();
        viewHolder.mMenu.setVisibility((castSession != null && castSession.isConnected()) ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View mParent;
        private final View mMenu;
        private final View mTextContainer;
        private AQuery mAquery;
        private TextView mTitleView;
        private TextView mDescriptionView;
        private ImageView mImgView;

        public static ViewHolder newInstance(View parent) {
            ImageView imgView = (ImageView) parent.findViewById(R.id.imageView1);
            TextView titleView = (TextView) parent.findViewById(R.id.textView1);
            TextView descriptionView = (TextView) parent.findViewById(R.id.textView2);
            View menu = parent.findViewById(R.id.menu);
            View textContainer = parent.findViewById(R.id.text_container);
            AQuery aQuery = new AQuery(parent);
            return new ViewHolder(parent, imgView, textContainer, titleView, descriptionView, menu, aQuery);
        }

        private ViewHolder(View parent, ImageView imgView, View textContainer, TextView titleView, TextView descriptionView, View menu, AQuery aQuery) {
            super(parent);
            mParent = parent;
            mImgView = imgView;
            mTextContainer = textContainer;
            mMenu = menu;
            mTitleView = titleView;
            mDescriptionView = descriptionView;
            mAquery = aQuery;
        }

        public void setTitle(String title) {
            mTitleView.setText(title);
        }

        public void setDescription(String description) {
            mDescriptionView.setText(description);
        }

        public void setImage(String imgUrl) {
            mAquery.id(mImgView).width(114).image(imgUrl, true, true, 0, R.drawable.default_video, null, 0, ASPECT_RATIO);
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mParent.setOnClickListener(listener);
        }

        public ImageView getImageView() {
            return mImgView;
        }
    }

    public void setData(List<MediaInfo> data) {
        videos = data;
        notifyDataSetChanged();
    }

    /**
     * A listener called when an item is clicked in the video list.
     */
    public interface ItemClickListener {
        void itemClicked(View view, MediaInfo item, int position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
