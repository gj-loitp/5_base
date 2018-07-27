package vn.loitp.app.activity.demo.chromecast.queue.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.chromecast.queue.QueueDataProvider;
import vn.loitp.app.activity.demo.chromecast.settings.CastPreference;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

/**
 * An activity to show the queue list
 */
public class QueueListViewActivity extends BaseFontActivity {
    private static final String FRAGMENT_LIST_VIEW = "list view";
    private final RemoteMediaClient.Listener mRemoteMediaClientListener = new MyRemoteMediaClientListener();
    private final SessionManagerListener<CastSession> mSessionManagerListener = new MySessionManagerListener();
    private CastContext mCastContext;
    private RemoteMediaClient mRemoteMediaClient;
    private View mEmptyView;

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {
        @Override
        public void onSessionEnded(CastSession session, int error) {
            if (mRemoteMediaClient != null) {
                mRemoteMediaClient.removeListener(mRemoteMediaClientListener);
            }
            mRemoteMediaClient = null;
            mEmptyView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mRemoteMediaClient = getRemoteMediaClient();
            if (mRemoteMediaClient != null) {
                mRemoteMediaClient.addListener(mRemoteMediaClientListener);
            }
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mRemoteMediaClient = getRemoteMediaClient();
            if (mRemoteMediaClient != null) {
                mRemoteMediaClient.addListener(mRemoteMediaClientListener);
            }
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
            if (mRemoteMediaClient != null) {
                mRemoteMediaClient.removeListener(mRemoteMediaClientListener);
            }
            mRemoteMediaClient = null;
        }
    }

    private class MyRemoteMediaClientListener implements RemoteMediaClient.Listener {

        @Override
        public void onStatusUpdated() {
            updateMediaQueue();
        }

        @Override
        public void onQueueStatusUpdated() {
            updateMediaQueue();
        }

        @Override
        public void onMetadataUpdated() {
        }

        @Override
        public void onPreloadStatusUpdated() {
        }

        @Override
        public void onSendingRemoteMediaRequest() {
        }

        @Override
        public void onAdBreakStatusUpdated() {
        }

        private void updateMediaQueue() {
            MediaStatus mediaStatus = mRemoteMediaClient.getMediaStatus();
            List<MediaQueueItem> queueItems = (mediaStatus == null) ? null : mediaStatus.getQueueItems();
            if (queueItems == null || queueItems.isEmpty()) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.queue_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new QueueListViewFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }
        setupActionBar();
        mEmptyView = findViewById(R.id.empty);
        mCastContext = CastContext.getSharedInstance(this);
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.queue_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
    }

    @Override
    protected void onPause() {
        if (mRemoteMediaClient != null) {
            mRemoteMediaClient.removeListener(mRemoteMediaClientListener);
        }
        mCastContext.getSessionManager().removeSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.queue_menu, menu);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(activity, CastPreference.class));
                LActivityUtil.tranIn(activity);
                break;
            case R.id.action_clear_queue:
                QueueDataProvider.getInstance(getApplicationContext()).removeAll();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        return mCastContext.onDispatchVolumeKeyEventBeforeJellyBean(event) || super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        mCastContext.getSessionManager().addSessionManagerListener(mSessionManagerListener, CastSession.class);
        if (mRemoteMediaClient == null) {
            mRemoteMediaClient = getRemoteMediaClient();
        }
        if (mRemoteMediaClient != null) {
            mRemoteMediaClient.addListener(mRemoteMediaClientListener);
            MediaStatus mediaStatus = mRemoteMediaClient.getMediaStatus();
            List<MediaQueueItem> queueItems = (mediaStatus == null) ? null : mediaStatus.getQueueItems();
            if (queueItems != null && !queueItems.isEmpty()) {
                mEmptyView.setVisibility(View.GONE);
            }
        }
        super.onResume();
    }

    private RemoteMediaClient getRemoteMediaClient() {
        CastSession castSession = mCastContext.getSessionManager().getCurrentCastSession();
        return (castSession != null && castSession.isConnected()) ? castSession.getRemoteMediaClient() : null;
    }
}
