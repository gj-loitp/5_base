//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera.Parameters;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CaptureRequest.Builder;
import android.media.MediaCrypto;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Range;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.artfulbits.libstream.StreamBuffer.VideoFormatParams;
import com.artfulbits.libstream.Streamer.CAPTURE_STATE;
import com.artfulbits.libstream.Streamer.Listener;
import com.artfulbits.libstream.Streamer.Size;
import com.artfulbits.libstream.gles.EglCore;
import com.artfulbits.libstream.gles.FullFrameRect;
import com.artfulbits.libstream.gles.FullFrameRect2;
import com.artfulbits.libstream.gles.Texture2dProgram;
import com.artfulbits.libstream.gles.WindowSurface;
import com.artfulbits.libstream.gles.Texture2dProgram.ProgramType;
import java.util.ArrayList;

@TargetApi(21)
class VideoListenerGLES21 extends VideoListener implements OnFrameAvailableListener {
    private static final String TAG = "VideoListenerGLES21";
    private HandlerThread mCameraThread;
    private Handler mCameraHandler;
    private CameraDevice mCamera2;
    private StateCallback mCameraStateCallback = new StateCallback() {
        public void onOpened(CameraDevice camera) {
            Log.d("VideoListenerGLES21", "onOpened");
            VideoListenerGLES21.this.mCamera2 = camera;

            try {
                ArrayList e = new ArrayList();
                e.add(VideoListenerGLES21.this.mPreviewSurface);
                VideoListenerGLES21.this.mCamera2.createCaptureSession(e, VideoListenerGLES21.this.mSessionStateCallback, VideoListenerGLES21.this.mCameraHandler);
            } catch (Exception var3) {
                Log.e("VideoListenerGLES21", Log.getStackTraceString(var3));
                VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
            }

        }

        public void onClosed(CameraDevice camera) {
            Log.d("VideoListenerGLES21", "onClosed");
            if(VideoListenerGLES21.this.isCameraClosing) {
                VideoListenerGLES21.this.stopCameraThread();
                VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.STOPPED);
            } else {
                String cameraIdStr = null;

                for(int i = 0; i < VideoListenerGLES21.this.mFlipCameraInfo.size(); ++i) {
                    FlipCameraInfo info = (FlipCameraInfo)VideoListenerGLES21.this.mFlipCameraInfo.get(i);
                    if(info.cameraId == VideoListenerGLES21.this.cameraId) {
                        cameraIdStr = info.cameraIdStr;
                        break;
                    }
                }

                if(!VideoListenerGLES21.this.openCamera(VideoListenerGLES21.this.mContext, cameraIdStr == null?Integer.toString(VideoListenerGLES21.this.cameraId):cameraIdStr)) {
                    Log.e("VideoListenerGLES21", "failed to open camera");
                    VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
                    VideoListenerGLES21.this.stop();
                }
            }

        }

        public void onDisconnected(CameraDevice camera) {
            Log.d("VideoListenerGLES21", "onDisconnected");
            VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
        }

        public void onError(CameraDevice camera, int error) {
            Log.d("VideoListenerGLES21", "onError, error=" + error);
            VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
        }
    };
    private CameraCaptureSession mCaptureSession;
    private android.hardware.camera2.CameraCaptureSession.StateCallback mSessionStateCallback = new android.hardware.camera2.CameraCaptureSession.StateCallback() {
        public void onConfigured(CameraCaptureSession session) {
            Log.d("VideoListenerGLES21", "onConfigured");
            VideoListenerGLES21.this.mCaptureSession = session;

            try {
                CameraDevice var10000 = VideoListenerGLES21.this.mCamera2;
                //VideoListenerGLES21.this.mCamera2;
                Builder e = var10000.createCaptureRequest(3);
                e.addTarget(VideoListenerGLES21.this.mPreviewSurface);
                e.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(3));
                if(null != VideoListenerGLES21.this.encoder_.getFpsRange()) {
                    Range previewRequest = new Range(Integer.valueOf((int)VideoListenerGLES21.this.encoder_.getFpsRange().fps_min), Integer.valueOf((int)VideoListenerGLES21.this.encoder_.getFpsRange().fps_max));
                    e.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, previewRequest);
                }

                CaptureRequest previewRequest1 = e.build();
                session.setRepeatingRequest(previewRequest1, (CaptureCallback)null, VideoListenerGLES21.this.mCameraHandler);
                VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.STARTED);
            } catch (Exception var4) {
                Log.e("VideoListenerGLES21", Log.getStackTraceString(var4));
                VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
            }

        }

        public void onConfigureFailed(CameraCaptureSession session) {
            Log.d("VideoListenerGLES21", "onConfigureFailed");
            VideoListenerGLES21.this.setVideoCaptureState(CAPTURE_STATE.FAILED);
        }
    };
    private Surface mPreviewSurface;
    private CodecProfileLevel[] profileLevels;
    boolean lensFacingBack;
    private Context mContext;
    private boolean isCameraClosing = false;
    private EglCore mEglCore;
    private WindowSurface mDisplaySurface;
    private WindowSurface mEncoderSurface;
    private SurfaceTexture mCameraTexture;
    private FullFrameRect2 mFullFrameBlit;
    private final float[] mTmpMatrix = new float[16];
    private int mTextureId;
    private int mFrameNum;
    private Runnable closeCameraRunnable_ = new Runnable() {
        public void run() {
            if(VideoListenerGLES21.this.mCamera2 != null) {
                VideoListenerGLES21.this.mCamera2.close();
            }

            VideoListenerGLES21.this.mCamera2 = null;
        }
    };
    private int mNewFilter;
    private int mCurrentFilter;

    public CodecProfileLevel[] getProfileLevels() {
        return this.profileLevels;
    }

    public VideoListenerGLES21(StreamBuffer streamBuffer, Listener listener) {
        super(streamBuffer, listener);
    }

    private boolean openCamera(Context context, final String cameraIdstr) {
        try {
            this.cameraId = Integer.parseInt(cameraIdstr);
            Log.d("VideoListenerGLES21", "open camera#" + this.cameraId);

            for(int e = 0; e < this.mFlipCameraInfo.size(); ++e) {
                FlipCameraInfo characteristics = (FlipCameraInfo)this.mFlipCameraInfo.get(e);
                if(characteristics.cameraId == this.cameraId) {
                    this.mCameraTexture.setDefaultBufferSize(characteristics.videoSize.width, characteristics.videoSize.height);
                    this.scale = characteristics.scale;
                    this.scale_letterbox = characteristics.scale_letterbox;
                    this.scale_landscape_letterbox = characteristics.scale_landscape_letterbox;
                    this.scale_landscape_pillarbox = characteristics.scale_landscape_pillarbox;
                    break;
                }
            }

            this.mPreviewSurface = new Surface(this.mCameraTexture);
            final CameraManager var6 = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
            CameraCharacteristics var7 = var6.getCameraCharacteristics(cameraIdstr);
            if(((Integer)var7.get(CameraCharacteristics.LENS_FACING)).intValue() == 1) {
                this.lensFacingBack = true;
            } else {
                this.lensFacingBack = false;
            }

            this.mCameraHandler.post(new Runnable() {
                public void run() {
                    if(VideoListenerGLES21.this.mCamera2 != null) {
                        throw new IllegalStateException("Camera already open");
                    } else {
                        try {
                            var6.openCamera(cameraIdstr, VideoListenerGLES21.this.mCameraStateCallback, VideoListenerGLES21.this.mCameraHandler);
                        } catch (CameraAccessException var2) {
                            Log.e("VideoListenerGLES21", Log.getStackTraceString(var2));
                        }

                    }
                }
            });
            return true;
        } catch (Exception var5) {
            Log.e("VideoListenerGLES21", Log.getStackTraceString(var5));
            return false;
        }
    }

    public boolean start(Context context, String cameraId, SurfaceHolder previewSurface, SurfaceTexture texture, VideoEncoder videoEncoder) {
        this.mEglCore = new EglCore((EGLContext)null, 1);
        if(null == videoEncoder) {
            this.setVideoCaptureState(CAPTURE_STATE.ENCODER_FAIL);
            return false;
        } else {
            this.encoder_ = videoEncoder;
            if(!this.openEncoder()) {
                Log.e("VideoListenerGLES21", "failed to open video encoder");
                this.setVideoCaptureState(CAPTURE_STATE.ENCODER_FAIL);
                this.stop();
                return false;
            } else {
                this.mDisplaySurface = new WindowSurface(this.mEglCore, this.mSurface, false);
                this.mDisplaySurface.makeCurrent();
                this.mFullFrameBlit = new FullFrameRect2(new Texture2dProgram(ProgramType.TEXTURE_EXT));
                this.mTextureId = this.mFullFrameBlit.createTextureObject();
                this.mCameraTexture = new SurfaceTexture(this.mTextureId);
                this.mCameraTexture.setOnFrameAvailableListener(this);
                this.mCameraTexture.setDefaultBufferSize(this.previewSize.width, this.previewSize.height);
                this.mPreviewSurface = new Surface(this.mCameraTexture);
                this.mCameraThread = new HandlerThread("LarixCameraOpsThread");
                this.mCameraThread.start();
                this.mCameraHandler = new Handler(this.mCameraThread.getLooper());
                if(!this.openCamera(context, cameraId)) {
                    Log.e("VideoListenerGLES21", "failed to open camera");
                    this.setVideoCaptureState(CAPTURE_STATE.FAILED);
                    this.stop();
                    return false;
                } else {
                    this.mContext = context;
                    return true;
                }
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
            this.mEncoderSurface = new WindowSurface(this.mEglCore, this.encoder_.getEncoder().createInputSurface(), true);
            this.previewSize = new Size(this.encoder_.getFormat().getInteger("width"), this.encoder_.getFormat().getInteger("height"));
            this.encoder_.getEncoder().start();
            return true;
        } catch (Exception var2) {
            Log.e("VideoListenerGLES21", Log.getStackTraceString(var2));
            return false;
        }
    }

    public void stop() {
        this.isCameraClosing = true;
        this.streamBuffer_.setVideoFormatParams((VideoFormatParams)null);
        if(null != this.mCaptureSession) {
            try {
                this.mCaptureSession.abortCaptures();
            } catch (Exception var2) {
                Log.e("VideoListenerGLES21", Log.getStackTraceString(var2));
            }

            this.mCaptureSession.close();
            this.mCaptureSession = null;
        }

        if(null != this.encoder_) {
            if(null != this.encoder_.getEncoder()) {
                this.encoder_.getEncoder().stop();
            }

            this.encoder_ = null;
        }

        this.releaseEgl();
        if(null != this.mCamera2 && null != this.mCameraHandler && null != this.mCameraThread) {
            this.mCameraHandler.post(this.closeCameraRunnable_);
        } else {
            this.setVideoCaptureState(CAPTURE_STATE.STOPPED);
        }

    }

    private void stopCameraThread() {
        if(null != this.mCameraThread) {
            this.mCameraThread.quitSafely();

            try {
                this.mCameraThread.join();
                this.mCameraThread = null;
                this.mCameraHandler = null;
            } catch (InterruptedException var2) {
                Log.d("VideoListenerGLES21", Log.getStackTraceString(var2));
            }

        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.drawFrame();
    }

    public void setFilter(int filterId) {
        mNewFilter = filterId;
    }

    private void drawFrame() {
        if(this.mEglCore == null) {
            Log.d("VideoListenerGLES21", "Skipping drawFrame after shutdown");
        } else {
            this.mDisplaySurface.makeCurrent();
            this.mCameraTexture.updateTexImage();
            this.mCameraTexture.getTransformMatrix(this.mTmpMatrix);
            GLES20.glViewport(0, 0, this.surfaceSize.width, this.surfaceSize.height);
            this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, this.displayOrientation, 1.0F);
            this.mDisplaySurface.swapBuffers();
            this.mEncoderSurface.makeCurrent();

            // Update the filter, if necessary.
            if (mCurrentFilter != mNewFilter) {
                Filters.updateFilter((FullFrameRect) mFullFrameBlit, mNewFilter);
                mCurrentFilter = mNewFilter;
            }

            GLES20.glViewport(0, 0, this.previewSize.width, this.previewSize.height);
            boolean angle = false;
            int angle1;
            if(this.lensFacingBack) {
                if(this.videoRotation == 0) {
                    angle1 = this.displayOrientation < 180?90:270;
                    if(this.scale_landscape_letterbox != 1.0F) {
                        this.mFullFrameBlit.drawFrameX(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_letterbox);
                    } else if(this.scale_landscape_pillarbox != 1.0F) {
                        this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_pillarbox);
                    } else {
                        this.mFullFrameBlit.drawFrameX(this.mTextureId, this.mTmpMatrix, angle1, 1.0F);
                    }
                } else {
                    angle1 = this.displayOrientation < 180?0:180;
                    if(this.scale_letterbox != 1.0F) {
                        this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, angle1, this.scale_letterbox);
                    } else {
                        this.mFullFrameBlit.drawFrameX(this.mTextureId, this.mTmpMatrix, angle1, this.scale);
                    }
                }
            } else if(this.videoRotation == 0) {
                angle1 = this.displayOrientation < 180?90:270;
                if(this.scale_landscape_letterbox != 1.0F) {
                    this.mFullFrameBlit.drawFrameMirrorX(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_letterbox);
                } else if(this.scale_landscape_pillarbox != 1.0F) {
                    this.mFullFrameBlit.drawFrameMirrorY(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_pillarbox);
                } else {
                    this.mFullFrameBlit.drawFrameMirrorX(this.mTextureId, this.mTmpMatrix, angle1, 1.0F);
                }
            } else {
                angle1 = this.displayOrientation < 180?0:180;
                if(this.scale_letterbox != 1.0F) {
                    this.mFullFrameBlit.drawFrameMirrorY(this.mTextureId, this.mTmpMatrix, angle1, this.scale_letterbox);
                } else {
                    this.mFullFrameBlit.drawFrameMirrorX(this.mTextureId, this.mTmpMatrix, angle1, this.scale);
                }
            }

            this.mEncoderSurface.setPresentationTime(this.mCameraTexture.getTimestamp());
            this.mEncoderSurface.swapBuffers();
            ++this.mFrameNum;
        }
    }

    private void releaseEgl() {
        if(this.mCameraTexture != null) {
            this.mCameraTexture.release();
            this.mCameraTexture = null;
        }

        if(this.mDisplaySurface != null) {
            this.mDisplaySurface.release();
            this.mDisplaySurface = null;
        }

        if(this.mEncoderSurface != null) {
            this.mEncoderSurface.release();
            this.mEncoderSurface = null;
        }

        if(this.mFullFrameBlit != null) {
            this.mFullFrameBlit.release(false);
            this.mFullFrameBlit = null;
        }

        if(this.mEglCore != null) {
            this.mEglCore.release();
            this.mEglCore = null;
        }

    }

    public boolean flip() {
        if(this.state_ != CAPTURE_STATE.STARTED) {
            throw new IllegalStateException();
        } else {
            this.isCameraClosing = false;

            for(int i = 0; i < this.mFlipCameraInfo.size(); ++i) {
                FlipCameraInfo info = (FlipCameraInfo)this.mFlipCameraInfo.get(i);
                if(info.cameraId != this.cameraId) {
                    this.cameraId = info.cameraId;
                    break;
                }
            }

            this.mCameraHandler.post(this.closeCameraRunnable_);
            return true;
        }
    }

    public Parameters getCameraParameters() {
        throw new IllegalStateException();
    }

    public void setCameraParameters(Parameters param) {
        throw new IllegalStateException();
    }
}
