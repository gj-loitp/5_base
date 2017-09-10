package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.util.Log;

public class VideoEncoder {

   private static final String TAG = "VideoEncoder";
   private MediaCodec encoder_;
   private MediaFormat format_;
   private CodecCapabilities cap_;
   private Streamer.FpsRange fpsRange;


   VideoEncoder(MediaCodec encoder, MediaFormat format, CodecCapabilities cap) {
      this.encoder_ = encoder;
      this.format_ = format;
      this.cap_ = cap;
   }

   public static VideoEncoder createVideoEncoder(Streamer.Size res) {
      VideoEncoder videoEncoder = null;
      if(Streamer.getVersion() < 18) {
         videoEncoder = createVideoEncoder16(res);
      } else {
         videoEncoder = createVideoEncoder18(res);
      }

      return videoEncoder;
   }

   @TargetApi(18)
   private static VideoEncoder createVideoEncoder18(Streamer.Size res) {
      MediaCodec encoder = null;
      VideoEncoder videoEncoder = null;

      try {
         encoder = MediaCodec.createEncoderByType("video/avc");
         if(null == encoder) {
            Log.e("VideoEncoder", "VideoEncoder18: failed to create video/avc encoder");
            return null;
         } else {
            MediaCodecInfo e = encoder.getCodecInfo();
            if(null == e) {
               Log.e("VideoEncoder", "VideoEncoder18: failed to get codec info");
               encoder.release();
               return null;
            } else {
               CodecCapabilities cap = e.getCapabilitiesForType("video/avc");
               if(null == cap) {
                  Log.e("VideoEncoder", "VideoEncoder18: failed to get codec capabilities");
                  encoder.release();
                  return null;
               } else {
                  MediaFormat format = MediaFormat.createVideoFormat("video/avc", res.width, res.height);
                  if(null == format) {
                     Log.e("VideoEncoder", "VideoEncoder18: failed to create video format");
                     encoder.release();
                     return null;
                  } else {
                     videoEncoder = new VideoEncoder(encoder, format, cap);
                     return videoEncoder;
                  }
               }
            }
         }
      } catch (Exception var6) {
         Log.e("VideoEncoder", Log.getStackTraceString(var6));
         if(null != encoder) {
            encoder.release();
         }

         return null;
      }
   }

   private static VideoEncoder createVideoEncoder16(Streamer.Size res) {
      VideoEncoder videoEncoder = null;

      try {
         int e = MediaCodecList.getCodecCount();

         for(int i = 0; i < e; ++i) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if(codecInfo.isEncoder()) {
               String[] arr$ = codecInfo.getSupportedTypes();
               int len$ = arr$.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  String supportedMime = arr$[i$];
                  if(supportedMime.equals("video/avc")) {
                     CodecCapabilities cap = codecInfo.getCapabilitiesForType("video/avc");
                     if(null == cap) {
                        Log.e("VideoEncoder", "VideoEncoder16: failed to get codec capabilities");
                        return null;
                     }

                     MediaCodec encoder = MediaCodec.createEncoderByType("video/avc");
                     if(null == encoder) {
                        Log.e("VideoEncoder", "VideoEncoder16: failed to create video/avc encoder");
                        return null;
                     }

                     MediaFormat format = MediaFormat.createVideoFormat("video/avc", res.width, res.height);
                     if(null == format) {
                        Log.e("VideoEncoder", "VideoEncoder16: failed to create video format");
                        encoder.release();
                        return null;
                     }

                     videoEncoder = new VideoEncoder(encoder, format, cap);
                     break;
                  }
               }

               if(null != videoEncoder) {
                  break;
               }
            }
         }

         return videoEncoder;
      } catch (Exception var12) {
         Log.e("VideoEncoder", Log.getStackTraceString(var12));
         return null;
      }
   }

   public void setFrameRate(Streamer.FpsRange fpsRange) {
      this.fpsRange = fpsRange;
      float encoderFps = fpsRange.fps_max;
      if(encoderFps >= 1000.0F) {
         encoderFps /= 1000.0F;
      }

      this.format_.setFloat("capture-rate", encoderFps);
      this.format_.setFloat("frame-rate", encoderFps);
   }

   private static int getCompression(int profile) {
      boolean AVC_H264_Base_DIV = true;
      boolean AVC_H264_Main_DIV = true;
      boolean AVC_H264_High_DIV = true;
      boolean compression = true;
      short compression1;
      switch(profile) {
      case 1:
      default:
         compression1 = 6500;
         break;
      case 2:
         compression1 = 8000;
         break;
      case 8:
         compression1 = 8250;
      }

      return compression1;
   }

   public static int calcBitRate(Streamer.Size resolution, int frameRate, int profile) {
      int compression = getCompression(profile);
      int bps = resolution.width * resolution.height * frameRate / compression * 1000;
      return bps;
   }

   public void setBitRate(int bitRate) {
      this.format_.setInteger("bitrate", bitRate);
   }

   public void setKeyFrameInterval(int keyFrameInterval) {
      this.format_.setInteger("i-frame-interval", keyFrameInterval);
   }

   public CodecProfileLevel[] getSupportedProfiles() {
      return this.cap_.profileLevels;
   }

   public void setProfile(CodecProfileLevel profileLevel) {
      this.format_.setInteger("profile", profileLevel.profile);
      this.format_.setInteger("level", profileLevel.level);
   }

   public void release() {
      if(null != this.encoder_) {
         this.encoder_.release();
         this.encoder_ = null;
      }

      this.format_ = null;
   }

   int[] getSupportedColorFormats() {
      return this.cap_.colorFormats;
   }

   MediaFormat getFormat() {
      return this.format_;
   }

   MediaCodec getEncoder() {
      return this.encoder_;
   }

   Streamer.FpsRange getFpsRange() {
      return this.fpsRange;
   }
}
