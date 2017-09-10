package com.artfulbits.libstream;

import java.util.Arrays;

class RtspBuilder {

   private static final String TAG = "RtspBuilder";
   private byte[] buffer_;


   private RtspBuilder(byte[] buffer) {
      Arrays.fill(buffer, (byte)0);
      this.buffer_ = buffer;
   }

   public static RtspBuilder newBuilder(byte[] buffer) {
      return new RtspBuilder(buffer);
   }

   public RtspBuilder setInterleavedHeader(int channel, int length) {
      this.buffer_[0] = 36;
      this.buffer_[1] = (byte)(channel & 255);
      this.buffer_[2] = (byte)(length >> 8 & 255);
      this.buffer_[3] = (byte)(length & 255);
      return this;
   }

   public RtspBuilder setVersion(byte version) {
      this.buffer_[4] = (byte)(this.buffer_[4] | (version & 3) << 6);
      return this;
   }

   public RtspBuilder setPadding(byte padding) {
      this.buffer_[4] = (byte)(this.buffer_[4] | (padding & 1) << 5);
      return this;
   }

   public RtspBuilder setExtension(byte extension) {
      this.buffer_[4] = (byte)(this.buffer_[4] | (extension & 1) << 4);
      return this;
   }

   public RtspBuilder setCsrcCount(byte csrcCount) {
      this.buffer_[4] = (byte)(this.buffer_[4] | csrcCount & 15);
      return this;
   }

   public RtspBuilder setMarker(byte marker) {
      this.buffer_[5] = (byte)(this.buffer_[5] | (marker & 1) << 7);
      return this;
   }

   public RtspBuilder setPayloadType(byte payloadType) {
      this.buffer_[5] = (byte)(this.buffer_[5] | payloadType & 127);
      return this;
   }

   public RtspBuilder setSeqNum(int seqNum) {
      this.buffer_[6] = (byte)(seqNum >> 8 & 255);
      this.buffer_[7] = (byte)(seqNum & 255);
      return this;
   }

   public RtspBuilder setTimestamp(int timestamp) {
      this.buffer_[8] = (byte)(timestamp >> 24 & 255);
      this.buffer_[9] = (byte)(timestamp >> 16 & 255);
      this.buffer_[10] = (byte)(timestamp >> 8 & 255);
      this.buffer_[11] = (byte)(timestamp & 255);
      return this;
   }

   public RtspBuilder setSsrc(int ssrc) {
      this.buffer_[12] = (byte)(ssrc >> 24 & 255);
      this.buffer_[13] = (byte)(ssrc >> 16 & 255);
      this.buffer_[14] = (byte)(ssrc >> 8 & 255);
      this.buffer_[15] = (byte)(ssrc & 255);
      return this;
   }

   public void setAACHeader(short aac_len) {
      this.buffer_[16] = 0;
      this.buffer_[17] = 16;
      this.buffer_[18] = (byte)(aac_len >> 5 & 255);
      this.buffer_[19] = (byte)(aac_len << 3 & 248);
   }

   public void setNALHeader(byte nal_ref_idc) {
      this.buffer_[16] = 28;
      this.buffer_[16] = (byte)(this.buffer_[16] | (nal_ref_idc & 3) << 5);
   }

   public void setFUAHeader(byte nal_unit_type, boolean start, boolean end) {
      this.buffer_[17] = (byte)(nal_unit_type & 31);
      if(start) {
         this.buffer_[17] = (byte)(this.buffer_[17] | 128);
      } else if(end) {
         this.buffer_[17] = (byte)(this.buffer_[17] | 64);
      }

   }

   public void setReportCount(byte reportCount) {
      this.buffer_[4] = (byte)(this.buffer_[4] | reportCount & 31);
   }

   public void setRtcpPayloadType(byte payloadType) {
      this.buffer_[5] = (byte)(this.buffer_[5] | payloadType & 255);
   }

   public void setReportLength(int reportLength) {
      int reportLength32 = reportLength / 4 - 1;
      this.buffer_[6] = (byte)(reportLength32 >> 8 & 255);
      this.buffer_[7] = (byte)(reportLength32 & 255);
   }

   public void setSenderSSRC(int ssrc) {
      this.buffer_[8] = (byte)(ssrc >> 24 & 255);
      this.buffer_[9] = (byte)(ssrc >> 16 & 255);
      this.buffer_[10] = (byte)(ssrc >> 8 & 255);
      this.buffer_[11] = (byte)(ssrc & 255);
   }

   public void setNtpTimestamp(long ntpTimestamp) {
      this.buffer_[12] = (byte)((int)(ntpTimestamp >> 56 & 255L));
      this.buffer_[13] = (byte)((int)(ntpTimestamp >> 48 & 255L));
      this.buffer_[14] = (byte)((int)(ntpTimestamp >> 40 & 255L));
      this.buffer_[15] = (byte)((int)(ntpTimestamp >> 32 & 255L));
      this.buffer_[16] = (byte)((int)(ntpTimestamp >> 24 & 255L));
      this.buffer_[17] = (byte)((int)(ntpTimestamp >> 16 & 255L));
      this.buffer_[18] = (byte)((int)(ntpTimestamp >> 8 & 255L));
      this.buffer_[19] = (byte)((int)(ntpTimestamp & 255L));
   }

   public void setRtpTimestamp(long rtpTimestamp) {
      this.buffer_[20] = (byte)((int)(rtpTimestamp >> 24 & 255L));
      this.buffer_[21] = (byte)((int)(rtpTimestamp >> 16 & 255L));
      this.buffer_[22] = (byte)((int)(rtpTimestamp >> 8 & 255L));
      this.buffer_[23] = (byte)((int)(rtpTimestamp & 255L));
   }

   public void setSendersPacketCount(int packetCount) {
      this.buffer_[24] = (byte)(packetCount >> 24 & 255);
      this.buffer_[25] = (byte)(packetCount >> 16 & 255);
      this.buffer_[26] = (byte)(packetCount >> 8 & 255);
      this.buffer_[27] = (byte)(packetCount & 255);
   }

   public void setSendersOctetCount(int octetCount) {
      this.buffer_[28] = (byte)(octetCount >> 24 & 255);
      this.buffer_[29] = (byte)(octetCount >> 16 & 255);
      this.buffer_[30] = (byte)(octetCount >> 8 & 255);
      this.buffer_[31] = (byte)(octetCount & 255);
   }
}
