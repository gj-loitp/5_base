package com.artfulbits.libstream;


class BufferItem {

   private static final String TAG = "BufferItem";
   private long frameId_;
   private long message_index_;
   private byte[] data_;
   private long ts_;
   BufferItem.FRAME_TYPE type_;
   int flags_;


   private BufferItem(long frameId, BufferItem.FRAME_TYPE type, int len) {
      this.frameId_ = frameId;
      this.type_ = type;
      this.message_index_ = -1L;
      this.data_ = new byte[len];
   }

   public static BufferItem newAudioBuffer(long frameId, int len) {
      return new BufferItem(frameId, BufferItem.FRAME_TYPE.AUDIO, len);
   }

   public static BufferItem newVideoBuffer(long frameId, int len) {
      return new BufferItem(frameId, BufferItem.FRAME_TYPE.VIDEO, len);
   }

   public void setFlags(int flags) {
      this.flags_ = flags;
   }

   public int getFlags() {
      return this.flags_;
   }

   public BufferItem.FRAME_TYPE getMessageType() {
      return this.type_;
   }

   public void setTimestamp(long ts) {
      this.ts_ = ts;
   }

   public long getTimestamp() {
      return this.ts_;
   }

   public long getFrameId() {
      return this.frameId_;
   }

   public void setMessageIndex(long message_index) {
      this.message_index_ = message_index;
   }

   public long getMessageIndex() {
      return this.message_index_;
   }

   public byte[] getData() {
      return this.data_;
   }

   public static enum FRAME_TYPE {

      VIDEO("VIDEO", 0),
      AUDIO("AUDIO", 1);
      // $FF: synthetic field
      private static final BufferItem.FRAME_TYPE[] $VALUES = new BufferItem.FRAME_TYPE[]{VIDEO, AUDIO};


      private FRAME_TYPE(String var1, int var2) {}

   }
}
