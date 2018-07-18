package vn.loitp.app.activity.customviews.videoview.exoplayer2cast;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

public class ExoPlayer2CastActivity extends BaseFontActivity implements OnClickListener, PlayerManager.QueuePositionListener {

    private PlayerView localPlayerView;
    private PlayerControlView castControlView;
    private PlayerManager playerManager;
    private RecyclerView mediaQueueList;
    private MediaQueueListAdapter mediaQueueListAdapter;
    private CastContext castContext;
    private MediaRouteButton mMediaRouteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// Getting the cast context later than onStart can cause device discovery not to take place.

        mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);

        castContext = CastContext.getSharedInstance(this);

        if (castContext.getCastState() != CastState.NO_DEVICES_AVAILABLE)
            mMediaRouteButton.setVisibility(View.VISIBLE);

        castContext.addCastStateListener(new CastStateListener() {
            @Override
            public void onCastStateChanged(int state) {
                LLog.d(TAG, "onCastStateChanged " + state);
                if (state == CastState.NO_DEVICES_AVAILABLE)
                    mMediaRouteButton.setVisibility(View.GONE);
                else {
                    if (mMediaRouteButton.getVisibility() == View.GONE)
                        mMediaRouteButton.setVisibility(View.VISIBLE);
                }
            }
        });


        localPlayerView = findViewById(R.id.local_player_view);
        localPlayerView.requestFocus();

        castControlView = findViewById(R.id.cast_control_view);

        mediaQueueList = findViewById(R.id.sample_list);
        ItemTouchHelper helper = new ItemTouchHelper(new RecyclerViewCallback());
        helper.attachToRecyclerView(mediaQueueList);
        mediaQueueList.setLayoutManager(new LinearLayoutManager(this));
        mediaQueueList.setHasFixedSize(true);
        mediaQueueListAdapter = new MediaQueueListAdapter();

        findViewById(R.id.add_sample_button).setOnClickListener(this);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_exoplayer2_cast;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_exo_player2_cast, menu);
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item);
        return true;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        playerManager =
                PlayerManager.createPlayerManager(
            /* queuePositionListener= */ this,
                        localPlayerView,
                        castControlView,
            /* context= */ this,
                        castContext);
        mediaQueueList.setAdapter(mediaQueueListAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaQueueListAdapter.notifyItemRangeRemoved(0, mediaQueueListAdapter.getItemCount());
        mediaQueueList.setAdapter(null);
        playerManager.release();
    }

    // Activity input.

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // If the event was not handled then see if the player view can handle it.
        return super.dispatchKeyEvent(event) || playerManager.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View view) {
        new AlertDialog.Builder(this).setTitle(R.string.sample_list_dialog_title)
                .setView(buildSampleListView()).setPositiveButton(android.R.string.ok, null).create()
                .show();
    }

    // PlayerManager.QueuePositionListener implementation.

    @Override
    public void onQueuePositionChanged(int previousIndex, int newIndex) {
        if (previousIndex != C.INDEX_UNSET) {
            mediaQueueListAdapter.notifyItemChanged(previousIndex);
        }
        if (newIndex != C.INDEX_UNSET) {
            mediaQueueListAdapter.notifyItemChanged(newIndex);
        }
    }

    // Internal methods.

    private View buildSampleListView() {
        View dialogList = getLayoutInflater().inflate(R.layout.sample_list, null);
        ListView sampleList = dialogList.findViewById(R.id.sample_list);
        sampleList.setAdapter(new SampleListAdapter(this));
        sampleList.setOnItemClickListener(
                new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        playerManager.addItem(DemoUtil.SAMPLES.get(position));
                        mediaQueueListAdapter.notifyItemInserted(playerManager.getMediaQueueSize() - 1);
                    }
                });
        return dialogList;
    }

    // Internal classes.

    private class QueueItemViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final TextView textView;

        public QueueItemViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            playerManager.selectQueueItem(getAdapterPosition());
        }

    }

    private class MediaQueueListAdapter extends RecyclerView.Adapter<QueueItemViewHolder> {

        @Override
        public QueueItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new QueueItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(QueueItemViewHolder holder, int position) {
            TextView view = holder.textView;
            view.setText(playerManager.getItem(position).name);
            // TODO: Solve coloring using the theme's ColorStateList.
            view.setTextColor(ColorUtils.setAlphaComponent(view.getCurrentTextColor(),
                    position == playerManager.getCurrentItemIndex() ? 255 : 100));
        }

        @Override
        public int getItemCount() {
            return playerManager.getMediaQueueSize();
        }

    }

    private class RecyclerViewCallback extends ItemTouchHelper.SimpleCallback {

        private int draggingFromPosition;
        private int draggingToPosition;

        public RecyclerViewCallback() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END);
            draggingFromPosition = C.INDEX_UNSET;
            draggingToPosition = C.INDEX_UNSET;
        }

        @Override
        public boolean onMove(RecyclerView list, RecyclerView.ViewHolder origin,
                              RecyclerView.ViewHolder target) {
            int fromPosition = origin.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (draggingFromPosition == C.INDEX_UNSET) {
                // A drag has started, but changes to the media queue will be reflected in clearView().
                draggingFromPosition = fromPosition;
            }
            draggingToPosition = toPosition;
            mediaQueueListAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (playerManager.removeItem(position)) {
                mediaQueueListAdapter.notifyItemRemoved(position);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (draggingFromPosition != C.INDEX_UNSET) {
                // A drag has ended. We reflect the media queue change in the player.
                if (!playerManager.moveItem(draggingFromPosition, draggingToPosition)) {
                    // The move failed. The entire sequence of onMove calls since the drag started needs to be
                    // invalidated.
                    mediaQueueListAdapter.notifyDataSetChanged();
                }
            }
            draggingFromPosition = C.INDEX_UNSET;
            draggingToPosition = C.INDEX_UNSET;
        }

    }

    private static final class SampleListAdapter extends ArrayAdapter<DemoUtil.Sample> {

        public SampleListAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, DemoUtil.SAMPLES);
        }

    }

}
