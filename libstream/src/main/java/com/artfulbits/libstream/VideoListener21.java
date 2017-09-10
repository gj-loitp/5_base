//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera.Parameters;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CaptureRequest.Builder;
import android.media.MediaCrypto;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Range;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.artfulbits.libstream.StreamBuffer.VideoFormatParams;
import com.artfulbits.libstream.Streamer.Listener;
import com.artfulbits.libstream.Streamer.CAPTURE_STATE;
import java.util.ArrayList;

@TargetApi(21)
class VideoListener21 extends VideoListener {
    private static final String TAG = "VideoListener21";
    private CameraCaptureSession session_;
    private HandlerThread cameraThread_;
    private Handler cameraHandler_;
    private Handler handler_ = new Handler();
    StateCallback cameraStateCallback = new StateCallback() {
        public void onOpened(CameraDevice camera) {
            Log.d("VideoListener21", "onOpened");
            VideoListener21.this.camera2 = camera;

            try {
                ArrayList e = new ArrayList();
                e.add(VideoListener21.this.previewSurface_);
                e.add(VideoListener21.this.recordingSurface_);
                VideoListener21.this.camera2.createCaptureSession(e, VideoListener21.this.sessionStatCallback, VideoListener21.this.cameraHandler_);
            } catch (Exception var3) {
                Log.e("VideoListener21", Log.getStackTraceString(var3));
                VideoListener21.this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
            }

        }

        public void onClosed(CameraDevice camera) {
            Log.d("VideoListener21", "onClosed");
            VideoListener21.this.stopCameraThread();
        }

        public void onDisconnected(CameraDevice camera) {
            Log.d("VideoListener21", "onDisconnected");
            VideoListener21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
        }

        public void onError(CameraDevice camera, int error) {
            Log.d("VideoListener21", "onError, error=" + error);
            VideoListener21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
        }
    };
    android.hardware.camera2.CameraCaptureSession.StateCallback sessionStatCallback = new android.hardware.camera2.CameraCaptureSession.StateCallback() {
        public void onConfigured(CameraCaptureSession session) {
            Log.d("VideoListener21", "onConfigured");
            VideoListener21.this.session_ = session;

            try {
                CameraDevice var10001 = VideoListener21.this.camera2;
                Builder e = VideoListener21.this.camera2.createCaptureRequest(3);
                e.addTarget(VideoListener21.this.previewSurface_);
                e.addTarget(VideoListener21.this.recordingSurface_);
                e.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(3));
                if(null != VideoListener21.this.encoder_.getFpsRange()) {
                    Range previewRequest = new Range(Integer.valueOf((int)VideoListener21.this.encoder_.getFpsRange().fps_min), Integer.valueOf((int)VideoListener21.this.encoder_.getFpsRange().fps_max));
                    e.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, previewRequest);
                }

                CaptureRequest previewRequest1 = e.build();
                session.setRepeatingRequest(previewRequest1, (CaptureCallback)null, VideoListener21.this.cameraHandler_);
                VideoListener21.this.setVideoCaptureState(CAPTURE_STATE.STARTED);
            } catch (Exception var4) {
                Log.e("VideoListener21", Log.getStackTraceString(var4));
                VideoListener21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
            }

        }

        public void onConfigureFailed(CameraCaptureSession session) {
            Log.d("VideoListener21", "onConfigureFailed");
            VideoListener21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
        }
    };
    CameraDevice camera2;
    Surface recordingSurface_;
    private Surface previewSurface_;
    private CodecProfileLevel[] profileLevels;
    long frameId = 1L;

    public VideoListener21(StreamBuffer streamBuffer, Listener listener) {
        super(streamBuffer, listener);
    }

    public CodecProfileLevel[] getProfileLevels() {
        return this.profileLevels;
    }

    private boolean openCamera(Context context, final String cameraId) {
        try {
            final CameraManager e = (CameraManager)context.getSystemService("camera");
            this.cameraHandler_.post(new Runnable() {
                public void run() {
                    if(VideoListener21.this.camera2 != null) {
                        throw new IllegalStateException("Camera already open");
                    } else {
                        try {
                            e.openCamera(cameraId, VideoListener21.this.cameraStateCallback, VideoListener21.this.cameraHandler_);
                        } catch (CameraAccessException var2) {
                            Log.e("VideoListener21", Log.getStackTraceString(var2));
                        }

                    }
                }
            });
            return true;
        } catch (Exception var4) {
            Log.e("VideoListener21", Log.getStackTraceString(var4));
            return false;
        }
    }

    public boolean start(Context context, String cameraId, SurfaceHolder previewSurface, SurfaceTexture texture, VideoEncoder videoEncoder) {
        if(null != previewSurface) {
            this.previewSurface_ = previewSurface.getSurface();
        } else {
            if(null == texture) {
                throw new NullPointerException();
            }

            this.previewSurface_ = new Surface(texture);
        }

        this.cameraThread_ = new HandlerThread("LarixCameraOpsThread");
        this.cameraThread_.start();
        this.cameraHandler_ = new Handler(this.cameraThread_.getLooper());
        if(null == videoEncoder) {
            this.setVideoCaptureState(CAPTURE_STATE.ENCODER_FAIL);
            return false;
        } else {
            this.encoder_ = videoEncoder;
            if(!this.openEncoder()) {
                Log.e("VideoListener21", "failed to open video encoder");
                this.setVideoCaptureState(CAPTURE_STATE.ENCODER_FAIL);
                this.stop();
                return false;
            } else if(!this.openCamera(context, cameraId)) {
                Log.e("VideoListener21", "failed to open camera");
                this.setVideoCaptureState(CAPTURE_STATE.FAILED);
                this.stop();
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public boolean start(Context var1, String var2, SurfaceHolder var3, SurfaceTexture var4, VideoEncoder var5, int orientation) {
        return start(var1, var2, var3, var4, var5);
    }

    public boolean openEncoder() {
        try {
            this.encoder_.getFormat().setInteger("color-format", 2130708361);
            this.encoder_.getEncoder().setCallback(this.mediaCodecCallback);
            this.encoder_.getEncoder().configure(this.encoder_.getFormat(), (Surface)null, (MediaCrypto)null, 1);
            this.recordingSurface_ = this.encoder_.getEncoder().createInputSurface();
            this.encoder_.getEncoder().start();
            return true;
        } catch (Exception var2) {
            Log.e("VideoListener21", Log.getStackTraceString(var2));
            return false;
        }
    }

    public void stop() {
        this.streamBuffer_.setVideoFormatParams((VideoFormatParams)null);
        if(null != this.session_) {
            try {
                this.session_.abortCaptures();
            } catch (Exception var2) {
                Log.e("VideoListener21", Log.getStackTraceString(var2));
            }

            this.session_.close();
            this.session_ = null;
        }

        if(null != this.encoder_) {
            if(null != this.encoder_.getEncoder()) {
                this.encoder_.getEncoder().stop();
            }

            this.encoder_ = null;
        }

        if(null != this.recordingSurface_) {
            this.recordingSurface_.release();
            this.recordingSurface_ = null;
        }

        if(null != this.camera2 && null != this.cameraHandler_ && null != this.cameraThread_) {
            this.cameraHandler_.post(new Runnable() {
                public void run() {
                    if(VideoListener21.this.camera2 != null) {
                        VideoListener21.this.camera2.close();
                    }

                    VideoListener21.this.camera2 = null;
                }
            });
        } else {
            this.setVideoCaptureState(CAPTURE_STATE.STOPPED);
        }

    }

    private void stopCameraThread() {
        if(null != this.cameraThread_) {
            this.cameraThread_.quitSafely();

            try {
                this.cameraThread_.join();
                this.cameraThread_ = null;
                this.cameraHandler_ = null;
            } catch (InterruptedException var2) {
                Log.d("VideoListener21", Log.getStackTraceString(var2));
            }

        }
    }

    public boolean flip() {
        throw new IllegalStateException();
    }

    public Parameters getCameraParameters() {
        throw new IllegalStateException();
    }

    @Override
    public void setFilter(int filterId) {

    }

    public void setCameraParameters(Parameters param) {
        throw new IllegalStateException();
    }
}
