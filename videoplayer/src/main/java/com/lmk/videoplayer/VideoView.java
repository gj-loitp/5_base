package com.lmk.videoplayer;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewConfiguration;

import com.google.android.exoplayer.BehindLiveWindowException;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.util.Util;

/**
 * Created by khanh on 3/12/16.
 */
public class VideoView extends TextureView implements MediaPlayer.Listener, TextureView.SurfaceTextureListener {
    private static final String TAG = VideoView.class.getSimpleName();

    private MediaController mediaController;
    private MediaPlayer player;
    private Surface surface;
    private Context mContext;
    private OnVideoViewListener onVideoViewListener;

    private String path;
    private float videoAspectRatio;

    public VideoView(Context context) {
        this(context, null, 0);
        mContext=context;
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext=context;
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
        setKeepScreenOn(true);
    }

    public void play(String path) {
        this.path = path;
        start();
    }

    public void start() {
        preparePlayer();
        setKeepScreenOn(true);
    }

    public void pause() {
        if (player != null) {
            player.setPlayWhenReady(false);
            setKeepScreenOn(false);
        }
    }

    public void reset() {
        releasePlayer();
        setKeepScreenOn(false);
    }

    public void setMediaController(MediaController mediaController) {
        this.mediaController = mediaController;
    }

    public void onDestroy() {
        releasePlayer();
    }

    public long getDuration() {
        if (player != null) {
            return player.getDuration();
        }
        return 0;
    }

    /*private void adjustAspectRatio() {
        //original code
        Log.d(TAG, "getWidth() " + getWidth() + ", viewHeight " + getHeight());
        int viewWidth = getWidth();
        int viewHeight = getHeight()+getBottomBarHeight();

        Log.d(TAG, "getStatusBarHeight " + getStatusBarHeight() + ", getBottomBarHeight " + getBottomBarHeight());
        Log.d(TAG, "viewWidth " + viewWidth + ", viewHeight " + viewHeight + ", videoAspectRati " + videoAspectRatio);

        int newWidth, newHeight;
        newWidth = (int) (viewHeight / videoAspectRatio);
        newHeight = viewHeight;

        Log.d(TAG, "newWidth " + newWidth + ", newHeight " + newHeight);

        float xoff = (viewWidth - newWidth) / 2;
        float yoff = (viewHeight - newHeight) / 2;

        Log.d(TAG, "xoff " + xoff + ", yoff " + yoff);

        Matrix txform = new Matrix();
        getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        Log.d(TAG,"w "+ (float)newWidth/viewWidth+" h "+ (float) newHeight / viewHeight);
        txform.postTranslate(xoff, yoff);
        setTransform(txform);
    }*/

    private void adjustAspectRatio() {
        Log.d(TAG, "getWidth() " + getWidth() + ", viewHeight " + getHeight());
        int viewWidth = getWidth();
        int viewHeight = getHeight()+getStatusBarHeight()+getBottomBarHeight();
        Log.d(TAG, "getStatusBarHeight " + getStatusBarHeight() + ", getBottomBarHeight " + getBottomBarHeight());
        Log.d(TAG, "viewWidth " + viewWidth + ", viewHeight " + viewHeight + ", videoAspectRati " + videoAspectRatio);
        int newWidth, newHeight;
        if (viewHeight < (int) (viewWidth * videoAspectRatio)) {
            // limited by narrow width; restrict height
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * videoAspectRatio);
        } else {
            // limited by short height; restrict width
            newWidth = (int) (viewHeight / videoAspectRatio);
            newHeight = viewHeight;
            /*newHeight = (int) (viewWidth / videoAspectRatio);
            newWidth = viewWidth+getStatusBarHeight();*/
        }
        Log.d(TAG, "newWidth " + newWidth + ", newHeight " + newHeight);
        int xoff = (viewWidth - newWidth) / 2;
        int yoff = (viewHeight - newHeight) / 2;
        Log.d(TAG, "xoff " + xoff + ", yoff " + yoff);
        Matrix txform = new Matrix();
        getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight / viewHeight);
        /*txform.setScale((float)2.3, (float) 1.0);*/
        Log.d(TAG,"w "+ (float)newWidth/viewWidth+" h "+ (float) newHeight / viewHeight);
        txform.postTranslate(xoff, yoff);
        setTransform(txform);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG,"result bar height: "+result);
        return result;
    }

    public int getBottomBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(mContext).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            // Do whatever you need to do, this device has a navigation bar
            int result = 0;
            int resourceId = getResources().getIdentifier("design_bottom_navigation_height", "dimen", mContext.getPackageName());
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            Log.d(TAG,"result botbar height: "+result);
            return  result;
        }
        return 0;

        // Do whatever you need to do, this device has a navigation bar
       /* int result = 0;
        int resourceId = getResources().getIdentifier("design_bottom_navigation_height", "dimen", mContext.getPackageName());
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG,"result botbar height: "+result);
        return  result;*/
    }

    private MediaPlayer.RendererBuilder getRendererBuilder() {
        String userAgent = Util.getUserAgent(getContext(), "LiveStar");
        return new HlsRendererBuilder(getContext(), userAgent, path);
    }

    private void preparePlayer() {
        if (!TextUtils.isEmpty(path)) {
            if (player == null) {
                player = new MediaPlayer(getRendererBuilder());
                player.addListener(this);

                if (mediaController != null) {
                    mediaController.setMediaPlayerControl(player.getPlayerControl());
                    mediaController.setEnabled(true);
                }
            }
            player.setSurface(surface);
            player.prepare();
            player.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (mediaController != null) {
            mediaController.onStateChanged(playWhenReady, playbackState);
        }

        switch (playbackState) {
            case ExoPlayer.STATE_PREPARING:
                //Log.d(TAG, "STATE_PREPARING");
                if (onVideoViewListener != null) {
                    onVideoViewListener.onPreparing();
                }
                break;
            case ExoPlayer.STATE_ENDED:
                //Log.d(TAG, "STATE_ENDED");
                if (onVideoViewListener != null) {
                    onVideoViewListener.onEnded();
                }
                break;
            case ExoPlayer.STATE_IDLE:
                //Log.d(TAG, "STATE_IDLE");
                if (onVideoViewListener != null) {
                    onVideoViewListener.onIdle();
                }
                break;
            case ExoPlayer.STATE_BUFFERING:
                //Log.d(TAG, "STATE_BUFFERING");
                if (onVideoViewListener != null) {
                    onVideoViewListener.onBuffering();
                }
                break;
            case ExoPlayer.STATE_READY:
                //Log.d(TAG, "STATE_READY");
                if (onVideoViewListener != null) {
                    onVideoViewListener.onReady();
                }
                break;
        }
    }

    @Override
    public void onError(Exception e) {
        //Log.e("ViewView", "Error");
        e.printStackTrace();
        if (e instanceof BehindLiveWindowException || e instanceof ExoPlaybackException) {
            preparePlayer();
            //Log.i("VideoView", "retry!!!");
        } else if (onVideoViewListener != null) {
            onVideoViewListener.onError(e);
        }
    }

    public void setOnVideoViewListener(OnVideoViewListener onVideoViewListener) {
        this.onVideoViewListener = onVideoViewListener;
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
        if (width > 0 && height > 0) {
            Log.d(TAG,"onVideoSizeChanged height bot: "+getBottomBarHeight() +"status "+getStatusBarHeight());
            Log.d(TAG,"onVideoSizeChanged height: " + height);
            Log.d(TAG,"onVideoSizeChanged width: " + width);

            //videoAspectRatio = (float) (height) / (width+getBottomBarHeight()+getStatusBarHeight());
            videoAspectRatio = (float) (height) / (width);
            adjustAspectRatio();
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surface = new Surface(surface);
        if (player != null) {
            player.setSurface(this.surface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        adjustAspectRatio();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (player != null) {
            player.blockingClearSurface();
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public interface OnVideoViewListener {
        void onPreparing();

        void onError(Exception e);

        void onEnded();

        void onIdle();

        void onBuffering();

        void onReady();
    }
}