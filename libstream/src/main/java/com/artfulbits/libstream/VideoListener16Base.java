package com.artfulbits.libstream;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaFormat;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.util.Log;
import java.nio.ByteBuffer;

abstract class VideoListener16Base extends VideoListener {

   private static final String TAG = "VideoListener16Base";
   protected Camera mCamera;
   protected BufferInfo bufferInfo;
   CodecProfileLevel[] profileLevels;


   public CodecProfileLevel[] getProfileLevels() {
      return this.profileLevels;
   }

   public VideoListener16Base(StreamBuffer streamBuffer, Streamer.Listener listener) {
      super(streamBuffer, listener);
   }

   protected void getVideoFrame() {
      try {
         if(null == this.encoder_.getEncoder()) {
            Log.e("VideoListener16Base", "video codec is null");
         } else if(null == this.bufferInfo) {
            Log.e("VideoListener16Base", "bufferInfo is null");
         } else {
            while(true) {
               while(true) {
                  int e = this.encoder_.getEncoder().dequeueOutputBuffer(this.bufferInfo, 0L);
                  if(-2 != e) {
                     if(e < 0) {
                        return;
                     }

                     ByteBuffer outBuffer1 = this.encoder_.getEncoder().getOutputBuffers()[e];
                     outBuffer1.limit(this.bufferInfo.offset + this.bufferInfo.size);
                     if(Utils.LTrim(outBuffer1, NAL_SEPARATOR)) {
                        this.bufferInfo.size -= NAL_SEPARATOR.length;
                     }

                     if((2 & this.bufferInfo.flags) == 2) {
                        if(this.streamBuffer_.getVideoFormatParams() == null) {
                           this.processCodecConfig(outBuffer1);
                        }
                     } else {
                        BufferItem bufferItem = BufferItem.newVideoBuffer((long)(this.frameId++), this.bufferInfo.size);
                        bufferItem.setTimestamp(this.bufferInfo.presentationTimeUs);
                        bufferItem.setFlags(this.bufferInfo.flags);
                        outBuffer1.get(bufferItem.getData(), 0, this.bufferInfo.size);
                        this.streamBuffer_.putItem(bufferItem);
                     }

                     this.encoder_.getEncoder().releaseOutputBuffer(e, false);
                  } else {
                     MediaFormat outBuffer = this.encoder_.getEncoder().getOutputFormat();
                     this.processOutputFormatChanged(outBuffer);
                  }
               }
            }
         }
      } catch (Exception var4) {
         Log.e("VideoListener16Base", "failed to get video frame from encoder");
         Log.e("VideoListener16Base", Log.getStackTraceString(var4));
      }
   }

   protected void processCodecConfig(ByteBuffer outBuffer) {
      StreamBuffer.VideoFormatParams videoFormatParams = new StreamBuffer.VideoFormatParams();
      videoFormatParams.sps_buf = new byte[this.bufferInfo.size];
      videoFormatParams.sps_len = 0;
      videoFormatParams.pps_buf = new byte[this.bufferInfo.size];
      videoFormatParams.pps_len = 0;

      while(true) {
         int pos = Utils.indexOf(outBuffer, NAL_SEPARATOR);
         if(pos == -1) {
            pos = outBuffer.limit();
         }

         int unit_len = pos - outBuffer.position();
         if((outBuffer.get(outBuffer.position()) & 31) == 7) {
            outBuffer.get(videoFormatParams.sps_buf, videoFormatParams.sps_len, unit_len);
            videoFormatParams.sps_len += unit_len;
         } else if((outBuffer.get(outBuffer.position()) & 31) == 8) {
            outBuffer.get(videoFormatParams.pps_buf, videoFormatParams.pps_len, unit_len);
            videoFormatParams.pps_len += unit_len;
         }

         if(outBuffer.limit() - outBuffer.position() < 4) {
            this.streamBuffer_.setVideoFormatParams(videoFormatParams);
            return;
         }

         outBuffer.position(pos + 4);
      }
   }

   public void setCameraParameters(Parameters param) {
      if(this.mCamera != null) {
         this.mCamera.setParameters(param);
      }

   }

   public Parameters getCameraParameters() {
      if(this.mCamera == null) {
         Log.d("VideoListener16Base", "mCamera is null");
         return null;
      } else {
         return this.mCamera.getParameters();
      }
   }
}
