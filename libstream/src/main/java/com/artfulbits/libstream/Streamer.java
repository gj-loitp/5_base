//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.artfulbits.libstream;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.SurfaceHolder;
import com.artfulbits.libstream.StreamBuffer.VideoFormatParams;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Streamer {
    private static final String TAG = "Streamer";
    public static final String VERSION_NAME = "1.0.12";
    protected StreamBuffer streamBuffer;
    protected Streamer.CAMERA_API mCameraApi;
    protected CameraManager mCameraManager;
    protected AudioListener mAudioListener;
    protected VideoListener mVideoListener;
    protected ConnectionManager connectionManager;
    protected Map<Integer, String> connectionUri;
    protected Map<Integer, Streamer.MODE> connectionMode;

    public Streamer() {
        this(Streamer.CAMERA_API.CAMERA);
    }

    public Streamer(Streamer.CAMERA_API api) {
        this.mCameraApi = Streamer.CAMERA_API.CAMERA;
        this.init(api);
    }

    protected void init(Streamer.CAMERA_API api) {
        if(getVersion() < 21 && api == Streamer.CAMERA_API.CAMERA2) {
            throw new IllegalArgumentException("Need at least Android 5.0 to use Camera2");
        } else {
            this.mCameraApi = api;
            this.streamBuffer = new StreamBuffer(200, 100);
            this.connectionManager = new ConnectionManager(this.streamBuffer);
            this.connectionUri = new HashMap();
            this.connectionMode = new HashMap();
        }
    }

    static int getVersion() {
        return VERSION.SDK_INT;
    }

    public void release() {
        if(null != this.connectionManager) {
            this.connectionManager.release();
        }

        this.stopVideoCapture();
        this.stopAudioCapture();
        if(null != this.streamBuffer) {
            this.streamBuffer = null;
        }

    }

    protected CameraManager getCameraManager() {
        if(this.mCameraManager == null) {
            if(this.mCameraApi == Streamer.CAMERA_API.CAMERA) {
                this.mCameraManager = new CameraManager16();
            } else {
                this.mCameraManager = new CameraManager21();
            }
        }

        return this.mCameraManager;
    }

    public void startAudioCapture(int audioSource, AudioEncoder audioEncoder, Streamer.Listener listener) {
        Log.d("Streamer", "startAudioCapture");
        if(null == this.mAudioListener) {
            this.mAudioListener = new AudioListener(this.streamBuffer, audioSource, audioEncoder, listener);
            this.mAudioListener.start();
        }
    }

    public void stopAudioCapture() {
        Log.d("Streamer", "stopAudioCapture");
        if(null != this.mAudioListener) {
            try {
                this.mAudioListener.interrupt();
                this.mAudioListener.join();
            } catch (InterruptedException var2) {
                Log.e("Streamer", "failed to stop audio thread");
            }

            this.mAudioListener = null;
        }
    }

    public void startVideoCapture(Context context, String cameraId, SurfaceHolder previewSurfaceHolder, VideoEncoder videoEncoder, Streamer.Listener listener, int orientation) {
        Log.d("Streamer", "startVideoCapture");
        if(null == this.mVideoListener) {
            if(this.mCameraApi == Streamer.CAMERA_API.CAMERA) {
                this.mVideoListener = new VideoListener16(this.streamBuffer, listener);
            } else {
                Log.w("Streamer", "SurfaceHolder is not recommended for Camera2, use SurfaceTexture instead");
                this.mVideoListener = new VideoListener21(this.streamBuffer, listener);
            }

            this.mVideoListener.start(context, cameraId, previewSurfaceHolder, (SurfaceTexture)null, videoEncoder, orientation);
        }
    }

    public void startVideoCapture(Context context, String cameraId, SurfaceTexture texture, VideoEncoder videoEncoder, Streamer.Listener listener) {
        Log.d("Streamer", "startVideoCapture using camera2 api");
        if(null == this.mVideoListener) {
            if(this.mCameraApi == Streamer.CAMERA_API.CAMERA2) {
                this.mVideoListener = new VideoListener21(this.streamBuffer, listener);
                this.mVideoListener.start(context, cameraId, (SurfaceHolder)null, texture, videoEncoder);
            } else {
                throw new IllegalStateException("Use SurfaceHolder for Camera preview display");
            }
        }
    }

    public void stopVideoCapture() {
        Log.d("Streamer", "stopVideoCapture");
        if(null != this.mVideoListener) {
            this.mVideoListener.stop();
            this.mVideoListener = null;
        }
    }

    public int createConnection(String uri, Streamer.MODE mode, Streamer.Listener listener) {
        int connectionId = this.connectionManager.createRtspConnection(uri, mode, listener);
        if(connectionId != -1) {
            this.connectionUri.put(Integer.valueOf(connectionId), uri);
            this.connectionMode.put(Integer.valueOf(connectionId), mode);
        }

        return connectionId;
    }

    public String getConnectionUri(int connectionId) {
        return (String)this.connectionUri.get(Integer.valueOf(connectionId));
    }

    public Streamer.MODE getConnectionMode(int connectionId) {
        return (Streamer.MODE)this.connectionMode.get(Integer.valueOf(connectionId));
    }

    public long getBytesSent(int connectionId) {
        return this.connectionManager.getBytesSent(connectionId);
    }

    public long getBytesRecv(int connectionId) {
        return this.connectionManager.getBytesRecv(connectionId);
    }

    public long getAudioPacketsSent(int connectionId) {
        return this.connectionManager.getAudioPacketsSent(connectionId);
    }

    public long getAudioPacketsLost(int connectionId) {
        return this.connectionManager.getAudioPacketsLost(connectionId);
    }

    public long getVideoPacketsSent(int connectionId) {
        return this.connectionManager.getVideoPacketsSent(connectionId);
    }

    public long getVideoPacketsLost(int connectionId) {
        return this.connectionManager.getVideoPacketsLost(connectionId);
    }

    public void releaseConnection(int connectionId) {
        this.connectionManager.releaseRtspConnection(connectionId);
        this.connectionUri.remove(Integer.valueOf(connectionId));
        this.connectionMode.remove(Integer.valueOf(connectionId));
    }

    public List<Streamer.CameraInfo> getCameraList(Context context) {
        CameraManager cameraManager = this.getCameraManager();
        return cameraManager.getCameraList(context);
    }

    public Streamer.CameraInfo getCameraInfo(Context context, String cameraId) {
        CameraManager cameraManager = this.getCameraManager();
        return cameraManager.getCameraInfo(context, cameraId);
    }

    public byte[] getProfileLevelId() {
        VideoFormatParams videoFormatParams = this.streamBuffer.getVideoFormatParams();
        return videoFormatParams != null && videoFormatParams.sps_buf != null && videoFormatParams.sps_buf.length > 3?Arrays.copyOfRange(videoFormatParams.sps_buf, 1, 4):null;
    }

    public double getFps() {
        return null == this.streamBuffer?-1.0D:this.streamBuffer.getFps();
    }

    public void setUserAgent(String userAgent) {
        this.connectionManager.setUserAgent(userAgent);
    }

    public void getUserAgent() {
        this.connectionManager.getUserAgent();
    }

    public Streamer.CAMERA_API getCameraApi() {
        return this.mCameraApi;
    }

    public void setCameraParameters(Parameters param) {
        if(this.mCameraApi == Streamer.CAMERA_API.CAMERA2) {
            throw new IllegalStateException();
        } else {
            this.mVideoListener.setCameraParameters(param);
        }
    }

    public Parameters getCameraParameters() {
        if(this.mCameraApi == Streamer.CAMERA_API.CAMERA2) {
            throw new IllegalStateException();
        } else {
            return this.mVideoListener.getCameraParameters();
        }
    }

    public static class CameraInfo {
        public String cameraId;
        public Streamer.Size[] previewSizes;
        public Streamer.Size[] recordSizes;
        public boolean lensFacingBack;
        public Streamer.FpsRange[] fpsRanges;
        public int orientation;

        public CameraInfo() {
        }
    }

    public static class FpsRange {
        public float fps_min;
        public float fps_max;

        public FpsRange(float fps_min, float fps_max) {
            this.fps_min = fps_min;
            this.fps_max = fps_max;
        }
    }

    public static class Size {
        public int width;
        public int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public double getRatio() {
            return (double)this.width / (double)this.height;
        }

        public double getVerticalRatio() {
            return (double)this.height / (double)this.width;
        }
    }

    public interface Listener {
        Handler getHandler();

        void OnConnectionStateChanged(int var1, Streamer.CONNECTION_STATE var2, Streamer.STATUS var3);

        void OnVideoCaptureStateChanged(Streamer.CAPTURE_STATE var1);

        void OnAudioCaptureStateChanged(Streamer.CAPTURE_STATE var1);
    }

    public static enum CAPTURE_STATE {
        STARTED,
        STOPPED,
        ENCODER_FAIL,
        FAILED;

        private CAPTURE_STATE() {
        }
    }

    public static enum CONNECTION_STATE {
        INITIALIZED,
        CONNECTED,
        SETUP,
        RECORD,
        DISCONNECTED;

        private CONNECTION_STATE() {
        }
    }

    public static enum STATUS {
        SUCCESS,
        CONN_FAIL,
        AUTH_FAIL,
        UNKNOWN_FAIL;

        private STATUS() {
        }
    }

    public static enum CAMERA_API {
        CAMERA,
        CAMERA2;

        private CAMERA_API() {
        }
    }

    public static enum MODE {
        AUDIO_ONLY,
        VIDEO_ONLY,
        AUDIO_VIDEO;

        private MODE() {
        }
    }
}
