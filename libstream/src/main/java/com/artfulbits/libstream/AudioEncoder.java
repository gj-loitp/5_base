package com.artfulbits.libstream;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.IOException;

public class AudioEncoder {

   private static final String TAG = "AudioEncoder";
   private MediaCodec encoder_;
   private MediaFormat format_;
   private CodecCapabilities cap_;


   private AudioEncoder(MediaCodec encoder, MediaFormat format, CodecCapabilities cap) {
      this.encoder_ = encoder;
      this.format_ = format;
      this.cap_ = cap;
   }

   private static AudioEncoder createAudioEncoder16() {
      AudioEncoder audioEncoder = null;

      try {
         int e = MediaCodecList.getCodecCount();

         for(int i = 0; i < e; ++i) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if(codecInfo.isEncoder()) {
               String[] arr$ = codecInfo.getSupportedTypes();
               int len$ = arr$.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  String supportedMime = arr$[i$];
                  if(supportedMime.equals("audio/mp4a-latm")) {
                     CodecCapabilities cap = codecInfo.getCapabilitiesForType("audio/mp4a-latm");
                     if(null == cap) {
                        Log.e("AudioEncoder", "AudioEncoder16: failed to get aac codec capabilities");
                        return null;
                     }

                     MediaCodec encoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
                     if(null == encoder) {
                        Log.e("AudioEncoder", "AudioEncoder16: failed to create aac encoder");
                        return null;
                     }

                     MediaFormat format = MediaFormat.createAudioFormat("audio/mp4a-latm", '\uac44', 1);
                     if(null == format) {
                        Log.e("AudioEncoder", "AudioEncoder16: failed to create audio format");
                        encoder.release();
                        return null;
                     }

                     audioEncoder = new AudioEncoder(encoder, format, cap);
                     break;
                  }
               }

               if(null != audioEncoder) {
                  break;
               }
            }
         }

         return audioEncoder;
      } catch (Exception var11) {
         Log.e("AudioEncoder", Log.getStackTraceString(var11));
         return null;
      }
   }

   @TargetApi(18)
   private static AudioEncoder createAudioEncoder18() {
      AudioEncoder audioEncoder = null;

      try {
         MediaCodec e = MediaCodec.createEncoderByType("audio/mp4a-latm");
         if(null == e) {
            Log.e("AudioEncoder", "AudioEncoder18: failed to create aac encoder");
            return null;
         }

         MediaCodecInfo info = e.getCodecInfo();
         if(null == info) {
            Log.e("AudioEncoder", "AudioEncoder18: failed to get aac codec info");
            e.release();
            return null;
         }

         CodecCapabilities cap = info.getCapabilitiesForType("audio/mp4a-latm");
         if(null == cap) {
            Log.e("AudioEncoder", "AudioEncoder18: failed to get aac codec capabilities");
            e.release();
            return null;
         }

         MediaFormat audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", '\uac44', 1);
         if(null == audioFormat) {
            Log.e("AudioEncoder", "AudioEncoder18: failed to create audio format");
            e.release();
            return null;
         }

         audioEncoder = new AudioEncoder(e, audioFormat, cap);
      } catch (IOException var5) {
         Log.e("AudioEncoder", Log.getStackTraceString(var5));
      }

      return audioEncoder;
   }

   public static AudioEncoder createAudioEncoder() {
      AudioEncoder audioEncoder = null;
      if(Streamer.getVersion() < 18) {
         audioEncoder = createAudioEncoder16();
      } else {
         audioEncoder = createAudioEncoder18();
      }

      return audioEncoder;
   }

   public int[] getSupportedSampleRates() {
      return VERSION.SDK_INT < 21?new int[]{'\uac44'}:this.cap_.getAudioCapabilities().getSupportedSampleRates();
   }

   public void setSampleRate(int sampleRate) {
      this.format_.setInteger("sample-rate", sampleRate);
   }

   public int getSampleRate() {
      return this.format_.getInteger("sample-rate");
   }

   public int getMaxInputChannelCount() {
      return VERSION.SDK_INT < 21?1:this.cap_.getAudioCapabilities().getMaxInputChannelCount();
   }

   public void setChannelCount(int channelCount) {
      this.format_.setInteger("channel-count", channelCount);
   }

   public int getChannelCount() {
      return this.format_.getInteger("channel-count");
   }

   private static int getCompression(int aacProfile) {
      boolean AAC_LC_DIV = true;
      boolean AAC_Main_DIV = true;
      boolean HE_AAC_v2_DIV = true;
      boolean compression = true;
      byte compression1;
      switch(aacProfile) {
      case 1:
      case 3:
      case 4:
      default:
         compression1 = 16;
         break;
      case 2:
         compression1 = 15;
         break;
      case 5:
         compression1 = 17;
      }

      return compression1;
   }

   private static int getNearestBitrate(int bitrate) {
      int[] aacBitrates = new int[]{8000, 16000, 18000, 20000, 24000, 32000, '\u9c40', '\ubb80', '\udac0', '\ufa00', 80000, 96000, 112000, 128000, 160000, 192000, 256000, 320000};
      int bitrateIndex = 0;

      for(int i = 1; i < aacBitrates.length; ++i) {
         if(Math.abs(aacBitrates[i] - bitrate) <= Math.abs(aacBitrates[bitrateIndex] - bitrate)) {
            bitrateIndex = i;
         }
      }

      return aacBitrates[bitrateIndex];
   }

   public static int calcBitRate(int sampleRate, int channelCount, int aacProfile) {
      int compression = getCompression(aacProfile);
      int bitrate = sampleRate * 16 * channelCount / compression;
      return getNearestBitrate(bitrate);
   }

   public void setBitRate(int bitRate) {
      this.format_.setInteger("bitrate", bitRate);
   }

   public void setAACProfile(int aacProfile) {
      this.format_.setInteger("aac-profile", aacProfile);
   }

   MediaFormat getFormat() {
      return this.format_;
   }

   MediaCodec getEncoder() {
      return this.encoder_;
   }

   public void release() {
      if(null != this.encoder_) {
         this.encoder_.release();
         this.encoder_ = null;
      }

      this.format_ = null;
   }
}
