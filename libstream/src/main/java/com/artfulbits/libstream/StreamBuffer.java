package com.artfulbits.libstream;

class StreamBuffer {

   private static final String TAG = "StreamBuffer";
   private BufferItem[] ringBuffer;
   private int rtmp_buffer_initial_offset_;
   private int max_rtmp_buffer_items_;
   private long message_index_ = 0L;
   long prevts = 0L;
   private volatile StreamBuffer.VideoFormatParams videoFormatParams_;
   private volatile StreamBuffer.AudioFormatParams audioFormatParams_;
   private final int FPS_COUNT = 10;
   private int ts_num = 10;
   private long ts_sum = 0L;
   private long last_ts = -1L;
   private volatile double fps = -1.0D;


   public StreamBuffer(int max_rtmp_buffer_items, int rtmp_buffer_initial_offset) {
      this.rtmp_buffer_initial_offset_ = rtmp_buffer_initial_offset;
      this.max_rtmp_buffer_items_ = Math.max(max_rtmp_buffer_items, this.rtmp_buffer_initial_offset_);
      this.ringBuffer = new BufferItem[this.max_rtmp_buffer_items_];
   }

   public synchronized void putItem(BufferItem item) {
      if(null != item) {
         if(item.getMessageType() == BufferItem.FRAME_TYPE.VIDEO) {
            this.updateFps(item.getTimestamp() / 1000L);
         }

         item.setMessageIndex(this.message_index_);
         this.ringBuffer[(int)(this.message_index_ % (long)this.max_rtmp_buffer_items_)] = item;
         ++this.message_index_;
      }
   }

   public synchronized BufferItem getItem(long read_index) {
      if(read_index >= this.message_index_) {
         return null;
      } else {
         if(this.message_index_ - read_index > (long)this.rtmp_buffer_initial_offset_) {
            if(this.message_index_ < (long)this.rtmp_buffer_initial_offset_) {
               read_index = 0L;
            } else {
               read_index = this.message_index_ - (long)this.rtmp_buffer_initial_offset_;
            }
         }

         return this.ringBuffer[(int)(read_index % (long)this.max_rtmp_buffer_items_)];
      }
   }

   public void setVideoFormatParams(StreamBuffer.VideoFormatParams videoFormatParams) {
      this.videoFormatParams_ = videoFormatParams;
   }

   public StreamBuffer.VideoFormatParams getVideoFormatParams() {
      return this.videoFormatParams_;
   }

   public void setAudioFormatParams(StreamBuffer.AudioFormatParams audioFormatParams) {
      this.audioFormatParams_ = audioFormatParams;
   }

   public StreamBuffer.AudioFormatParams getAudioFormatParams() {
      return this.audioFormatParams_;
   }

   public double getFps() {
      return this.fps;
   }

   protected void updateFps(long ts) {
      if(this.last_ts != -1L && ts > this.last_ts) {
         if(this.ts_num > 0) {
            --this.ts_num;
         } else {
            this.ts_sum -= this.ts_sum / 10L;
         }

         this.ts_sum += ts - this.last_ts;
         if(this.ts_num == 0) {
            this.fps = 10000.0D / (double)this.ts_sum;
         }
      }

      this.last_ts = ts;
   }

   public static class VideoFormatParams {

      public byte[] sps_buf;
      public int sps_len = 0;
      public byte[] pps_buf;
      public int pps_len = 0;


   }

   public static class AudioFormatParams {

      public byte[] config_buf;
      public int config_len = 0;
      int sampleRate = -1;
      int channelCount = -1;


   }
}
