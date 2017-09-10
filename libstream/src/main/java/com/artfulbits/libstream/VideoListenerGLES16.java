package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.media.MediaCrypto;
import android.media.MediaCodec.BufferInfo;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.artfulbits.libstream.gles.EglCore;
import com.artfulbits.libstream.gles.FullFrameRect;
import com.artfulbits.libstream.gles.FullFrameRect2;
import com.artfulbits.libstream.gles.Texture2dProgram;
import com.artfulbits.libstream.gles.WindowSurface;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@TargetApi(18)
class VideoListenerGLES16 extends VideoListener16Base implements OnFrameAvailableListener {

   private static final String TAG = "VideoListenerGLES16";
   private CameraInfo mCameraInfo;
   private long frameId = 1L;
   private EglCore mEglCore;
   private WindowSurface mDisplaySurface;
   private WindowSurface mEncoderSurface;
   private SurfaceTexture mCameraTexture;
   private FullFrameRect2 mFullFrameBlit;
   private final float[] mTmpMatrix = new float[16];
   private int mTextureId;
   private int mFrameNum;
   private int mCurrentFilter;
   private int mNewFilter=1;


   public VideoListenerGLES16(StreamBuffer streamBuffer, Streamer.Listener listener) {
      super(streamBuffer, listener);
   }

   public boolean openEncoder() {
      try {
         this.encoder_.getFormat().setInteger("color-format", 2130708361);
         if(VERSION.SDK_INT >= 21) {
            this.encoder_.getEncoder().setCallback(this.mediaCodecCallback);
         }

         this.encoder_.getEncoder().configure(this.encoder_.getFormat(), (Surface)null, (MediaCrypto)null, 1);
         this.mEncoderSurface = new WindowSurface(this.mEglCore, this.encoder_.getEncoder().createInputSurface(), true);
         this.previewSize = new Streamer.Size(this.encoder_.getFormat().getInteger("width"), this.encoder_.getFormat().getInteger("height"));
         this.encoder_.getEncoder().start();
         return true;
      } catch (Exception var2) {
         Log.e("VideoListenerGLES16", Log.getStackTraceString(var2));
         return false;
      }
   }

   private boolean openCamera(String cameraIdStr) {
      this.cameraId = Integer.parseInt(cameraIdStr);
      Log.d("VideoListenerGLES16", "open camera#" + this.cameraId);
      this.mCamera = Camera.open(this.cameraId);
      this.mCameraInfo = new CameraInfo();
      Camera.getCameraInfo(this.cameraId, this.mCameraInfo);
      Parameters parameters = this.mCamera.getParameters();

      for(int focus_mode_set = 0; focus_mode_set < this.mFlipCameraInfo.size(); ++focus_mode_set) {
         VideoListener.FlipCameraInfo focusModes = (VideoListener.FlipCameraInfo)this.mFlipCameraInfo.get(focus_mode_set);
         if(focusModes.cameraId == this.cameraId) {
            parameters.setPreviewSize(focusModes.videoSize.width, focusModes.videoSize.height);
            this.scale = focusModes.scale;
            this.scale_letterbox = focusModes.scale_letterbox;
            this.scale_landscape_letterbox = focusModes.scale_landscape_letterbox;
            this.scale_landscape_pillarbox = focusModes.scale_landscape_pillarbox;
            break;
         }
      }

      boolean var7 = false;
      List var8 = parameters.getSupportedFocusModes();
      if(null != var8 && var8.size() > 0) {
         Iterator i$ = var8.iterator();

         while(i$.hasNext()) {
            String cursor = (String)i$.next();
            if(cursor.equals("continuous-video")) {
               parameters.setFocusMode("continuous-video");
               break;
            }
         }
      }

      Log.d("VideoListenerGLES16", "camera#" + this.cameraId + " focus_mode=" + parameters.getFocusMode());
      if(null != this.encoder_.getFpsRange()) {
         parameters.setPreviewFpsRange((int)this.encoder_.getFpsRange().fps_min, (int)this.encoder_.getFpsRange().fps_max);
      }

      this.mCamera.setParameters(parameters);
      return true;
   }

   public boolean start(Context context, String cameraIdStr, SurfaceHolder previewSurfaceHolder, SurfaceTexture texture, VideoEncoder encoder) {
      try {
         if(null == encoder) {
            this.setVideoCaptureState(Streamer.CAPTURE_STATE.ENCODER_FAIL);
            return false;
         } else {
            this.encoder_ = encoder;
            this.mEglCore = new EglCore((EGLContext)null, 1);
            if(!this.openEncoder()) {
               Log.e("VideoListenerGLES16", "failed to open video encoder");
               this.setVideoCaptureState(Streamer.CAPTURE_STATE.ENCODER_FAIL);
               this.stop();
               return false;
            } else {
               this.bufferInfo = new BufferInfo();
               if(!this.openCamera(cameraIdStr)) {
                  this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
                  this.stop();
                  return false;
               } else {
                  this.mDisplaySurface = new WindowSurface(this.mEglCore, this.mSurface, false);
                  this.mDisplaySurface.makeCurrent();
                  this.mFullFrameBlit = new FullFrameRect2(new Texture2dProgram(Texture2dProgram.ProgramType.TEXTURE_EXT));
                  this.mTextureId = this.mFullFrameBlit.createTextureObject();
                  this.mCameraTexture = new SurfaceTexture(this.mTextureId);
                  this.mCameraTexture.setOnFrameAvailableListener(this);
                  this.mCamera.setPreviewTexture(this.mCameraTexture);
                  this.mCamera.startPreview();
                  this.setVideoCaptureState(Streamer.CAPTURE_STATE.STARTED);
                  return true;
               }
            }
         }
      } catch (Exception var7) {
         Log.e("VideoListenerGLES16", "start failed");
         Log.e("VideoListenerGLES16", Log.getStackTraceString(var7));
         this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
         this.stop();
         return false;
      }
   }

   @Override
   public boolean start(Context var1, String var2, SurfaceHolder var3, SurfaceTexture var4, VideoEncoder var5, int orientation) {
      return start(var1, var2, var3, var4, var5);
   }

   public void onFrameAvailable(SurfaceTexture surfaceTexture) {
      this.drawFrame();
   }


   public void setFilter(int filterId) {
      mNewFilter = filterId;
   }

   private void drawFrame() {
      if(this.mEglCore == null) {
         Log.d("VideoListenerGLES16", "Skipping drawFrame after shutdown");
      } else {
         this.mDisplaySurface.makeCurrent();
         this.mCameraTexture.updateTexImage();
         this.mCameraTexture.getTransformMatrix(this.mTmpMatrix);
         GLES20.glViewport(0, 0, this.surfaceSize.width, this.surfaceSize.height);
         int orientation;
         if(this.mCameraInfo.facing == 1) {
            orientation = (this.mCameraInfo.orientation + this.displayOrientation) % 360;
            orientation = (360 - orientation) % 360;
         } else {
            orientation = (this.mCameraInfo.orientation - this.displayOrientation + 360) % 360;
         }

         if(orientation == 0) {
            this.mFullFrameBlit.drawFrame(this.mTextureId, this.mTmpMatrix);
         } else {
            this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, 360 - orientation, 1.0F);
         }

         this.mDisplaySurface.swapBuffers();
         if(VERSION.SDK_INT < 21) {
            this.getVideoFrame();
         }

         this.mEncoderSurface.makeCurrent();


         // Update the filter, if necessary.
         if (mCurrentFilter != mNewFilter) {
            Filters.updateFilter((FullFrameRect) mFullFrameBlit, mNewFilter);
            mCurrentFilter = mNewFilter;
         }

         GLES20.glViewport(0, 0, this.previewSize.width, this.previewSize.height);
         boolean angle = false;
         int angle1;
         if(this.mCameraInfo.facing == 0) {
            if(this.videoRotation == 0) {
               angle1 = orientation < 180?0:180;
               if(this.scale_landscape_letterbox != 1.0F) {
                  this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_letterbox);
               } else if(this.scale_landscape_pillarbox != 1.0F) {
                  this.mFullFrameBlit.drawFrameX(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_pillarbox);
               } else {
                  this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, angle1, 1.0F);
               }
            } else {
               angle1 = orientation < 180?270:90;
               if(this.scale_letterbox != 1.0F) {
                  this.mFullFrameBlit.drawFrameX(this.mTextureId, this.mTmpMatrix, angle1, this.scale_letterbox);
               } else {
                  this.mFullFrameBlit.drawFrameY(this.mTextureId, this.mTmpMatrix, angle1, this.scale);
               }
            }
         } else if(this.videoRotation == 0) {
            angle1 = orientation < 180?0:180;
            if(this.scale_landscape_letterbox != 1.0F) {
               this.mFullFrameBlit.drawFrameMirrorY(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_letterbox);
            } else if(this.scale_landscape_pillarbox != 1.0F) {
               this.mFullFrameBlit.drawFrameMirrorX(this.mTextureId, this.mTmpMatrix, angle1, this.scale_landscape_pillarbox);
            } else {
               this.mFullFrameBlit.drawFrameMirrorY(this.mTextureId, this.mTmpMatrix, angle1, 1.0F);
            }
         } else {
            angle1 = orientation < 180?270:90;
            if(this.scale_letterbox != 1.0F) {
               this.mFullFrameBlit.drawFrameMirrorX(this.mTextureId, this.mTmpMatrix, angle1, this.scale_letterbox);
            } else {
               this.mFullFrameBlit.drawFrameMirrorY(this.mTextureId, this.mTmpMatrix, angle1, this.scale);
            }
         }

         this.mEncoderSurface.setPresentationTime(this.mCameraTexture.getTimestamp());
         this.mEncoderSurface.swapBuffers();
         ++this.mFrameNum;
      }
   }

   public void stop() {
      this.streamBuffer_.setVideoFormatParams((StreamBuffer.VideoFormatParams)null);

      try {
         if(null != this.mCamera) {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
         }

         if(null != this.encoder_) {
            if(null != this.encoder_.getEncoder()) {
               this.encoder_.getEncoder().stop();
            }

            this.encoder_ = null;
         }
      } catch (Exception var2) {
         Log.e("VideoListenerGLES16", "stop failed");
         Log.e("VideoListenerGLES16", Log.getStackTraceString(var2));
      }

      this.releaseEgl();
      this.setVideoCaptureState(Streamer.CAPTURE_STATE.STOPPED);
   }

   public boolean flip() {
      if(this.state_ != Streamer.CAPTURE_STATE.STARTED) {
         throw new IllegalStateException();
      } else {
         try {
            if(null != this.mCamera) {
               this.mCamera.stopPreview();
               this.mCamera.release();
               this.mCamera = null;
            }
         } catch (Exception var5) {
            Log.e("VideoListenerGLES16", Log.getStackTraceString(var5));
            this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
            this.stop();
            return false;
         }

         String cameraIdStr = null;

         for(int e = 0; e < this.mFlipCameraInfo.size(); ++e) {
            VideoListener.FlipCameraInfo info = (VideoListener.FlipCameraInfo)this.mFlipCameraInfo.get(e);
            if(info.cameraId != this.cameraId) {
               cameraIdStr = info.cameraIdStr;
               break;
            }
         }

         if(!this.openCamera(cameraIdStr == null?Integer.toString(this.cameraId):cameraIdStr)) {
            this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
            this.stop();
            return false;
         } else {
            try {
               this.mCamera.setPreviewTexture(this.mCameraTexture);
            } catch (IOException var4) {
               Log.e("VideoListenerGLES16", Log.getStackTraceString(var4));
               this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
               this.stop();
               return false;
            }

            this.mCamera.startPreview();
            return true;
         }
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
}
