package com.artfulbits.libstream;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.List;

public class StreamerGL extends Streamer {

    private static final String TAG = "StreamerGL";
    private AudioEncoder mAudioEncoder;
    private VideoEncoder mVideoEncoder;
    protected Context mContext;
    Streamer.Listener mListener;
    private int mAudioSource;
    String cameraId = "0";
    protected List mFlipCameraInfo;
    private Surface mSurface;
    private Streamer.Size surfaceSize;
    private int displayOrientation;
    private int videoOrientation;
    private int mFilterId;


    public StreamerGL(Streamer.CAMERA_API api) {
        this.init(api);
    }

    protected void setAudioEncoder(AudioEncoder audioEncoder) {
        this.mAudioEncoder = audioEncoder;
    }

    protected void setVideoEncoder(VideoEncoder videoEncoder) {
        this.mVideoEncoder = videoEncoder;
    }

    protected void setSurface(Surface s) {
        this.mSurface = s;
    }

    public void setSurfaceSize(Streamer.Size s) {
        this.surfaceSize = s;
        if (null != this.mVideoListener) {
            this.mVideoListener.surfaceSize = this.surfaceSize;
        }

    }

    public void setDisplayRotation(int r) {
        this.displayOrientation = this.getDisplayOrientation(r);
        if (null != this.mVideoListener) {
            Log.d("StreamerGL", "display rotation is " + Integer.toString(this.displayOrientation));
            this.mVideoListener.displayOrientation = this.displayOrientation;
        }

    }

    public int getDisplayOrientation(int rotation) {
        short degrees = 0;
        switch (rotation) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = 180;
                break;
            case 3:
                degrees = 270;
        }

        return degrees;
    }

    public void setVideoOrientation(int o) {
        this.videoOrientation = o;
        if (null != this.mVideoListener) {
            Log.d("StreamerGL", "video rotation is " + Integer.toString(this.videoOrientation));
            this.mVideoListener.videoRotation = this.videoOrientation;
        }

    }

    public void startAudioCapture() {
        Log.d("StreamerGL", "startAudioCapture");
        if (null == this.mAudioListener) {
            this.mAudioListener = new AudioListener(this.streamBuffer, this.mAudioSource, this.mAudioEncoder, this.mListener);
            this.mAudioListener.start();
        }
    }

    public void startVideoCapture() {
        Log.d("StreamerGL", "startVideoCapture");
        if (null == this.mVideoListener) {
            if (this.mCameraApi == Streamer.CAMERA_API.CAMERA) {
                this.mVideoListener = new VideoListenerGLES16(this.streamBuffer, this.mListener);
            } else {
                this.mVideoListener = new VideoListenerGLES21(this.streamBuffer, this.mListener);
            }

            this.mVideoListener.setFilter(mFilterId);
            this.mVideoListener.mSurface = this.mSurface;
            this.mVideoListener.surfaceSize = this.surfaceSize;
            this.mVideoListener.displayOrientation = this.displayOrientation;
            this.mVideoListener.videoRotation = this.videoOrientation;
            this.mVideoListener.mFlipCameraInfo = this.mFlipCameraInfo;
            this.mVideoListener.start(this.mContext, this.cameraId, (SurfaceHolder) null, (SurfaceTexture) null, this.mVideoEncoder);
        }
    }

    public void setFilter(int filterId) {
        mFilterId = filterId;
        if (null != this.mVideoListener)
            this.mVideoListener.setFilter(mFilterId);
    }

    public Streamer.Size getActiveCameraVideoSize() {
        if (this.mVideoListener == null) {
            return new Streamer.Size(640, 480);
        } else {
            for (int i = 0; i < this.mFlipCameraInfo.size(); ++i) {
                VideoListener.FlipCameraInfo info = (VideoListener.FlipCameraInfo) this.mFlipCameraInfo.get(i);
                if (info.cameraId == this.mVideoListener.cameraId) {
                    return info.videoSize;
                }
            }

            return new Streamer.Size(640, 480);
        }
    }

    public void flip() {
        Log.d("StreamerGL", "flip Camera");
        if (null != this.mVideoListener) {
            this.mVideoListener.flip();
        }
    }

    public void release() {
        super.release();
        if (null != this.mVideoEncoder) {
            this.mVideoEncoder.release();
            this.mVideoEncoder = null;
        }

        if (null != this.mAudioEncoder) {
            this.mAudioEncoder.release();
            this.mAudioEncoder = null;
        }

    }

    public static class ORIENTATIONS {

        public static int LANDSCAPE = 0;
        public static int PORTRAIT = 1;


    }
}
