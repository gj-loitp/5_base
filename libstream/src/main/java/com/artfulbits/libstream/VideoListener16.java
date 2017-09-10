package com.artfulbits.libstream;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.media.MediaCrypto;
import android.media.MediaCodec.BufferInfo;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

class VideoListener16 extends VideoListener16Base implements PreviewCallback {

   private static final String TAG = "VideoListener16";
   private ColorConverter colorConverter = null;


   public VideoListener16(StreamBuffer streamBuffer, Streamer.Listener listener) {
      super(streamBuffer, listener);
   }

   @Override
   public boolean start(Context var1, String var2, SurfaceHolder var3, SurfaceTexture var4, VideoEncoder var5) {
      return start(var1, var2, var3, var4, var5, 0);
   }

   private boolean openCamera(String cameraIdStr, boolean planar) {
      int cameraId = Integer.parseInt(cameraIdStr);
      Log.d("VideoListener16", "open camera#" + cameraId);
      this.mCamera = Camera.open(cameraId);
      Parameters parameters = this.mCamera.getParameters();
      parameters.setPreviewSize(this.previewSize.width, this.previewSize.height);
      int pixel_format = -1;
      List previewFormats = parameters.getSupportedPreviewFormats();
      if(null != previewFormats && previewFormats.size() > 0) {
         Iterator focus_mode_set;
         int focusModes;
         if(planar) {
            focus_mode_set = previewFormats.iterator();

            while(focus_mode_set.hasNext()) {
               focusModes = ((Integer)focus_mode_set.next()).intValue();
               if(842094169 == focusModes) {
                  pixel_format = focusModes;
                  this.colorConverter = new YV12toYUV420Planar();
                  break;
               }
            }
         }

         if(-1 == pixel_format) {
            focus_mode_set = previewFormats.iterator();

            while(focus_mode_set.hasNext()) {
               focusModes = ((Integer)focus_mode_set.next()).intValue();
               if(17 == focusModes) {
                  pixel_format = focusModes;
                  if(planar) {
                     this.colorConverter = new NV21toYUV420Planar();
                  } else {
                     this.colorConverter = new NV21toYUV420();
                  }
                  break;
               }

               if(842094169 == focusModes) {
                  pixel_format = focusModes;
                  if(planar) {
                     this.colorConverter = new YV12toYUV420Planar();
                  } else {
                     this.colorConverter = new YV12toYUV420();
                  }
                  break;
               }
            }
         }
      }

      if(-1 == pixel_format) {
         Log.e("VideoListener16", "failed to set preview format, camera=" + cameraId);
         return false;
      } else {
         parameters.setPreviewFormat(pixel_format);
         Log.d("VideoListener16", "camera#" + cameraId + " preview_format=" + parameters.getPreviewFormat());
         boolean focus_mode_set1 = false;
         List focusModes1 = parameters.getSupportedFocusModes();
         if(null != focusModes1 && focusModes1.size() > 0) {
            Iterator i$ = focusModes1.iterator();

            while(i$.hasNext()) {
               String cursor = (String)i$.next();
               if(cursor.equals("continuous-video")) {
                  parameters.setFocusMode("continuous-video");
                  break;
               }
            }
         }

         Log.d("VideoListener16", "camera#" + cameraId + " focus_mode=" + parameters.getFocusMode());
         if(null != this.encoder_.getFpsRange()) {
            parameters.setPreviewFpsRange((int)this.encoder_.getFpsRange().fps_min, (int)this.encoder_.getFpsRange().fps_max);
         }

         this.mCamera.setParameters(parameters);
         return true;
      }
   }

   public boolean start(Context context, String cameraIdStr, SurfaceHolder previewSurfaceHolder, SurfaceTexture texture, VideoEncoder encoder, int orientation) {
      try {
         if(encoder == null) {
            Log.e("VideoListener16", "encoder is null");
            this.setVideoCaptureState(Streamer.CAPTURE_STATE.ENCODER_FAIL);
            this.stop();
            return false;
         } else {
            this.encoder_ = encoder;
            boolean e = false;
            int selected_format = -1;
            int[] arr$ = this.encoder_.getSupportedColorFormats();
            int len$ = arr$.length;

            int i$;
            int format;
            for(i$ = 0; i$ < len$; ++i$) {
               format = arr$[i$];
               if(19 == format || 20 == format) {
                  e = true;
                  selected_format = format;
                  break;
               }
            }

            if(selected_format == -1) {
               arr$ = this.encoder_.getSupportedColorFormats();
               len$ = arr$.length;

               for(i$ = 0; i$ < len$; ++i$) {
                  format = arr$[i$];
                  if(21 == format || 39 == format || 2130706688 == format) {
                     selected_format = format;
                     break;
                  }
               }
            }

            if(selected_format == -1) {
               Log.e("VideoListener16", "can\'t find supported color format");
               this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
               this.stop();
               return false;
            } else if(!this.openEncoder(selected_format)) {
               this.setVideoCaptureState(Streamer.CAPTURE_STATE.ENCODER_FAIL);
               this.stop();
               return false;
            } else {
               this.bufferInfo = new BufferInfo();
               if(!this.openCamera(cameraIdStr, e)) {
                  this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
                  this.stop();
                  return false;
               } else if(null != previewSurfaceHolder) {
                  this.mCamera.setPreviewDisplay(previewSurfaceHolder);
                  this.mCamera.setDisplayOrientation(orientation);
                  this.mCamera.setPreviewCallback(this);
                  this.mCamera.startPreview();
                  this.setVideoCaptureState(Streamer.CAPTURE_STATE.STARTED);
                  return true;
               } else {
                  throw new NullPointerException();
               }
            }
         }
      } catch (Exception var12) {
         Log.e("VideoListener16", Log.getStackTraceString(var12));
         this.setVideoCaptureState(Streamer.CAPTURE_STATE.FAILED);
         this.stop();
         return false;
      }
   }

   protected boolean openEncoder(int colorFormat) {
      try {
         this.encoder_.getFormat().setInteger("color-format", colorFormat);
         // added to inform player about video rotation
         this.encoder_.getFormat().setInteger("rotation-degrees", 270);
         this.encoder_.getFormat().setInteger("video-param-rotation-angle-degrees", 270);
         this.encoder_.getEncoder().configure(this.encoder_.getFormat(), (Surface)null, (MediaCrypto)null, 1);
         this.previewSize = new Streamer.Size(this.encoder_.getFormat().getInteger("width"), this.encoder_.getFormat().getInteger("height"));
         this.encoder_.getEncoder().start();
         return true;
      } catch (Exception var3) {
         Log.e("VideoListener16", Log.getStackTraceString(var3));
         return false;
      }
   }

   public void stop() {
      this.streamBuffer_.setVideoFormatParams((StreamBuffer.VideoFormatParams)null);

      try {
         if(null != this.mCamera) {
            this.mCamera.stopPreview();
            this.mCamera.setPreviewCallback((PreviewCallback)null);
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
         Log.e("VideoListener16", Log.getStackTraceString(var2));
      }

      this.setVideoCaptureState(Streamer.CAPTURE_STATE.STOPPED);
   }

   public void onPreviewFrame(byte[] data, Camera camera) {
      try {
         if(null == data) {
            Log.e("VideoListener16", "data is null");
            return;
         }

         if(null == this.encoder_.getEncoder()) {
            Log.e("VideoListener16", "mMediaCodec is null");
            return;
         }

         int e = this.encoder_.getEncoder().dequeueInputBuffer(-1L);
         if(e < 0) {
            return;
         }

         ByteBuffer inputBuffer = this.encoder_.getEncoder().getInputBuffers()[e];
         inputBuffer.clear();
         int data_len = data.length;
         if(data_len > inputBuffer.remaining()) {
            data_len = inputBuffer.remaining();
         }

         byte[] conv_data = this.colorConverter.convert(data, this.previewSize.width, this.previewSize.height);
         inputBuffer.put(conv_data, 0, data_len);
         this.encoder_.getEncoder().queueInputBuffer(e, 0, data_len, SystemClock.uptimeMillis() * 1000L, 0);
         this.getVideoFrame();
      } catch (Exception var7) {
         Log.e("VideoListener16", "failed to add video data into encoder buffer");
         Log.e("VideoListener16", Log.getStackTraceString(var7));
      }

   }

   public void setCameraParameters(Parameters param) {
      if(this.mCamera != null) {
         this.mCamera.setParameters(param);
      }

   }

   public Parameters getCameraParameters() {
      return this.mCamera != null && this.state_ == Streamer.CAPTURE_STATE.STARTED?this.mCamera.getParameters():null;
   }

   @Override
   public void setFilter(int filterId) {

   }

   public boolean flip() {
      throw new IllegalStateException();
   }
}
