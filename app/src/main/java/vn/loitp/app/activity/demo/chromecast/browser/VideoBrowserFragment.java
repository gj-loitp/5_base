package vn.loitp.app.activity.demo.chromecast.browser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.common.images.WebImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.chromecast.browser.model.Category;
import vn.loitp.app.activity.demo.chromecast.browser.model.DataCastVideo;
import vn.loitp.app.activity.demo.chromecast.browser.model.Track;
import vn.loitp.app.activity.demo.chromecast.browser.model.Video;
import vn.loitp.app.activity.demo.chromecast.mediaplayer.LocalPlayerActivity;
import vn.loitp.app.activity.demo.chromecast.utils.ChromeCastUtils;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LStoreUtil;

public class VideoBrowserFragment extends BaseFragment implements VideoListAdapter.ItemClickListener {
    private final String TAG = VideoBrowserFragment.class.getSimpleName();
    private static final String CATALOG_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/f.json";
    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private View mEmptyView;
    private View mLoadingView;
    private final SessionManagerListener<CastSession> mSessionManagerListener = new MySessionManagerListener();

    @Override
    protected int setLayoutResourceId() {
        return R.layout.video_browser_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);
        mEmptyView = getView().findViewById(R.id.empty_view);
        mLoadingView = getView().findViewById(R.id.progress_indicator);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new VideoListAdapter(this, getContext());
        mRecyclerView.setAdapter(mAdapter);
        //getLoaderManager().initLoader(0, null, this);

        getData();
    }

    private void getData() {
        String json = LStoreUtil.readTxtFromRawFolder(getActivity(), R.raw.json_chrome_cast_home);
        //LLog.d(TAG, "getData json " + json);
        DataCastVideo dataCastVideo = LSApplication.getInstance().getGson().fromJson(json, DataCastVideo.class);
        LLog.d(TAG, "getData " + LSApplication.getInstance().getGson().toJson(dataCastVideo));
        List<MediaInfo> mediaInfoList = new ArrayList<>();

        List<Category> categoryList = dataCastVideo.getCategories();
        Category category = categoryList.get(0);
        List<Video> videoList = category.getVideos();

        for (int i = 0; i < videoList.size(); i++) {
            Video video = videoList.get(i);
            MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);

            movieMetadata.putString(MediaMetadata.KEY_SUBTITLE, i + " - " + video.getSubtitle());
            movieMetadata.putString(MediaMetadata.KEY_TITLE, i + " - " + video.getTitle());
            movieMetadata.addImage(new WebImage(Uri.parse(Constants.URL_IMG_1)));
            movieMetadata.addImage(new WebImage(Uri.parse(Constants.URL_IMG_2)));

            //subtitle
            List<MediaTrack> mediaTrackList = new ArrayList<>();
            if (video.getTracks() != null) {
                for (int j = 0; j < video.getTracks().size(); j++) {
                    Track track = video.getTracks().get(j);
                    MediaTrack mediaTrack = buildTrack(track.getId(),
                            track.getType(), track.getSubtype(), track.getContentId(), track.getName(), track.getLanguage());
                    mediaTrackList.add(mediaTrack);
                }
            }

            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject();
                jsonObj.put(VideoProvider.KEY_DESCRIPTION, video.getSubtitle());
            } catch (JSONException e) {
                Log.e(TAG, "Failed to add description to the json object", e);
            }

            //String link = "https://toanvkstag-vod.uizacdn.net/68e7ef2b8a14438c921d9a06c710d011-stream/fec81ce4-a945-422e-8e01-a627e09f4db8/package/manifest.mpd";
            String link = "https://android-vod.uizacdn.net/16f8e65d8e2643ffa3ff5ee9f4f9ba03-stream/fe2865b7-09ec-4f71-afb6-12d7815555ca/package/manifest.mpd";

            MediaInfo mediaInfo = new MediaInfo.Builder(
                    link)
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setContentType("application/dash+xml")
                    .setMetadata(movieMetadata)
                    .setMediaTracks(mediaTrackList)
                    .setStreamDuration(video.getDuration() * 1000)
                    .setCustomData(jsonObj)
                    .build();
            mediaInfoList.add(mediaInfo);
        }

        mAdapter.setData(mediaInfoList);
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(null == mediaInfoList || mediaInfoList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private MediaTrack buildTrack(long id, String type, String subType, String contentId, String name, String language) {
        int trackType = MediaTrack.TYPE_UNKNOWN;
        if ("text".equals(type)) {
            trackType = MediaTrack.TYPE_TEXT;
        } else if ("video".equals(type)) {
            trackType = MediaTrack.TYPE_VIDEO;
        } else if ("audio".equals(type)) {
            trackType = MediaTrack.TYPE_AUDIO;
        }

        int trackSubType = MediaTrack.SUBTYPE_NONE;
        if (subType != null) {
            if ("captions".equals(type)) {
                trackSubType = MediaTrack.SUBTYPE_CAPTIONS;
            } else if ("subtitle".equals(type)) {
                trackSubType = MediaTrack.SUBTYPE_SUBTITLES;
            }
        }

        return new MediaTrack.Builder(id, trackType)
                .setName(name)
                .setSubtype(trackSubType)
                .setContentId(contentId)
                .setLanguage(language).build();
    }

    @Override
    public void itemClicked(View view, MediaInfo item, int position) {
        if (view instanceof ImageButton) {
            ChromeCastUtils.showQueuePopup(getActivity(), view, item);
        } else {
            /*String transitionName = getString(R.string.transition_image);
            VideoListAdapter.ViewHolder viewHolder = (VideoListAdapter.ViewHolder) mRecyclerView.findViewHolderForPosition(position);
            Pair<View, String> imagePair = Pair.create((View) viewHolder.getImageView(), transitionName);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair);*/
            Intent intent = new Intent(getActivity(), LocalPlayerActivity.class);
            intent.putExtra("media", item);
            intent.putExtra("shouldStart", false);
            startActivity(intent);
            //ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }

    @Override
    public void onStart() {
        CastContext.getSharedInstance(getContext()).getSessionManager().addSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onStart();
    }

    @Override
    public void onStop() {
        CastContext.getSharedInstance(getContext()).getSessionManager().removeSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onStop();
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {
        @Override
        public void onSessionEnded(CastSession session, int error) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSessionStarting(CastSession session) {
        }

        @Override
        public void onSessionStartFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionEnding(CastSession session) {
        }

        @Override
        public void onSessionResuming(CastSession session, String sessionId) {
        }

        @Override
        public void onSessionResumeFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionSuspended(CastSession session, int reason) {
        }
    }
}
