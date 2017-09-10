package com.lmk.videoplayer;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.util.PlayerControl;
import com.lmk.videoplayer.utils.StringUtils;


/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/8/2015
 */
public class MediaController extends FrameLayout implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private static final int SHOW_TIMEOUT = 3000;
    private static final int HIDE = 1;
    private static final int SHOW_PROGRESS = 2;

    private View topControls;
    private View bottomControls;
    private TextView txtCurrentTime;
    private TextView txtTotalTime;
    private SeekBar sbProgress;
    private ImageView imgPlay;
    private ProgressBar pgbLoading;

    private PlayerControl player;

    private boolean isDragging;
    private boolean isShowing = true;
    private boolean isCanSeek = false;
    private long lastTouchedDown;

    private long duration;

    public MediaController(Context context) {
        super(context);
    }

    public MediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.media_controller, this, false);
        addView(v);

        topControls = v.findViewById(R.id.mediaController_rltTopControls);
        bottomControls = v.findViewById(R.id.mediaController_rltBottomControls);

        txtCurrentTime = (TextView) v.findViewById(R.id.mediaController_txtTimeCurrent);
        txtTotalTime = (TextView) v.findViewById(R.id.mediaController_txtTimeTotal);

        sbProgress = (SeekBar) v.findViewById(R.id.mediaController_sbProgress);
        imgPlay = (ImageView) v.findViewById(R.id.mediaController_imgPlay);
        pgbLoading = (ProgressBar) v.findViewById(R.id.mediaController_pgbLoading);

        imgPlay.setOnClickListener(this);
        sbProgress.setOnSeekBarChangeListener(this);
        sbProgress.setMax(1000);

        setIsCanSeek(isCanSeek);
    }

    public void onStateChanged(boolean playWhenReady, int playbackState) {
        switch(playbackState) {
            case ExoPlayer.STATE_PREPARING:
            case ExoPlayer.STATE_BUFFERING:
                showLoading(true);
                break;
            case ExoPlayer.STATE_READY:
                showLoading(false);
                show(SHOW_TIMEOUT);
                setBackgroundColor(Color.TRANSPARENT);
                break;
            case ExoPlayer.STATE_ENDED:
                hide();
                showLoading(false);
                break;
            case ExoPlayer.STATE_IDLE:
                hide();
                showLoading(false);
                break;
        }
    }

    private void showLoading(boolean isShow) {
        if (isShow) {
            pgbLoading.setVisibility(VISIBLE);
            imgPlay.setVisibility(INVISIBLE);
        } else {
            pgbLoading.setVisibility(INVISIBLE);
            if (isShowing) {
                imgPlay.setVisibility(VISIBLE);
            }
        }
    }

    private long setProgress() {
        if (player == null || isDragging)
            return 0;

        long position = player.getCurrentPosition();
        duration = player.getDuration();
        if (sbProgress != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                sbProgress.setProgress((int) pos);
            }
            int percent = player.getBufferPercentage();
            sbProgress.setSecondaryProgress(percent * 10);
        }

        txtTotalTime.setText(StringUtils.generateTime(duration));
        txtCurrentTime.setText(StringUtils.generateTime(position));

        return position;
    }

    private void updatePausePlay() {
        if (player != null && player.isPlaying()) {
            imgPlay.setImageResource(R.drawable.ic_pause);
        } else {
            imgPlay.setImageResource(R.drawable.ic_play);
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void toggleVisibility() {
        if (isShowing()) {
            hide();
        } else {
            show(SHOW_TIMEOUT);
        }
    }

    public void show() {
        show(0);
    }

    public void show(long timeout) {
        if (!isShowing) {
            showControls();
        }
        updatePausePlay();
        mHandler.sendEmptyMessage(SHOW_PROGRESS);
        mHandler.removeMessages(HIDE);

        if (timeout != 0) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(HIDE), timeout);
        }
    }

    public void hide() {
        if (isShowing) {
            mHandler.removeMessages(SHOW_PROGRESS);
            mHandler.removeMessages(HIDE);
            hideControls();
        }
    }

    private void showControls() {
        isShowing = true;
        topControls.setVisibility(VISIBLE);
        bottomControls.setVisibility(VISIBLE);
        if (pgbLoading.getVisibility() != VISIBLE) {
            imgPlay.setVisibility(VISIBLE);
        }
    }

    private void hideControls() {
        isShowing = false;
        topControls.setVisibility(INVISIBLE);
        bottomControls.setVisibility(INVISIBLE);
        imgPlay.setVisibility(INVISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.lastTouchedDown = System.currentTimeMillis();
        }
        else if (event.getAction() == MotionEvent.ACTION_UP
                && System.currentTimeMillis() - this.lastTouchedDown < 300) {
            toggleVisibility();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (player == null) return;

        if (!player.isPlaying()) {
            player.start();
            if (!isCanSeek) {
                // live streaming
                player.seekTo(0);
            }
        } else {
            player.pause();
            show();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser)
            return;

        long newPosition = (duration * seekBar.getProgress()) / 1000;
        String time = StringUtils.generateTime(newPosition);
        txtCurrentTime.setText(time);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isDragging = true;
        show(3600000);
        mHandler.removeMessages(SHOW_PROGRESS);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        player.seekTo((int) ((duration * seekBar.getProgress()) / 1000));

        show(SHOW_TIMEOUT);
        mHandler.removeMessages(SHOW_PROGRESS);
        isDragging = false;
        mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
    }

    public void setMediaPlayerControl(PlayerControl player) {
        this.player = player;
    }

    public void setIsCanSeek(boolean isCanSeek) {
        this.isCanSeek = isCanSeek;
        if (isCanSeek) {
            txtCurrentTime.setVisibility(VISIBLE);
            txtTotalTime.setVisibility(VISIBLE);
            sbProgress.setVisibility(VISIBLE);
        } else {
            txtCurrentTime.setVisibility(INVISIBLE);
            txtTotalTime.setVisibility(INVISIBLE);
            sbProgress.setVisibility(INVISIBLE);
        }
    }

    public void reset() {
        this.topControls.setVisibility(VISIBLE);
        this.bottomControls.setVisibility(VISIBLE);
        this.imgPlay.setVisibility(INVISIBLE);
        this.sbProgress.setProgress(0);
        this.sbProgress.setSecondaryProgress(0);
        this.pgbLoading.setVisibility(VISIBLE);
        this.isShowing = true;
        this.isDragging = false;
        this.txtCurrentTime.setText("00:00");
        this.txtTotalTime.setText("00:00");
        setBackgroundColor(Color.BLACK);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case HIDE:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    pos = setProgress();
                    if (!isDragging) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        if (isShowing) {
                            updatePausePlay();
                        }
                    }
                    break;
            }
        }
    };
}
