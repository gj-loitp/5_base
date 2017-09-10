package com.artfulbits.libstream;

import android.util.Log;

class RtmpPublisherChunkStreamInfo {

   static final String TAG = "Rtmp";
   int timestamp = 0;
   int timestamp_delta = 0;
   int message_size = 0;
   int fmt = -1;
   byte type;
   int stream_id = 0;


   RtmpPublisherChunkStreamInfo(byte stream_type) {
      this.type = stream_type;
   }

   void setStreamId(int strm_id) {
      this.stream_id = strm_id;
   }

   int fillHeader(byte[] rtmp_header, int current_message_size, int current_timestamp) {
      if(this.fmt == -1) {
         this.fmt = 0;
         this.timestamp = this.timestamp_delta = current_timestamp;
         this.message_size = current_message_size;
      } else if(current_timestamp < this.timestamp) {
         this.fmt = 0;
         this.timestamp = this.timestamp_delta = current_timestamp;
         this.message_size = current_message_size;
      } else if(this.message_size != current_message_size) {
         this.fmt = 1;
         this.timestamp_delta = current_timestamp - this.timestamp;
         this.timestamp = current_timestamp;
         this.message_size = current_message_size;
      } else if(current_timestamp - this.timestamp == this.timestamp_delta) {
         this.fmt = 3;
         this.timestamp = current_timestamp;
      } else {
         this.fmt = 2;
         this.timestamp_delta = current_timestamp - this.timestamp;
         this.timestamp = current_timestamp;
      }

      byte cs_id = 0;
      if(this.type == 8) {
         cs_id = 6;
      } else if(this.type == 9) {
         cs_id = 5;
      } else if(this.type == 18) {
         cs_id = 4;
      }

      rtmp_header[0] = (byte)(cs_id | this.fmt << 6);
      byte rtmp_header_size = 1;
      Log.v("Rtmp", "fmt=" + this.fmt + ";cs_id=" + cs_id);
      switch(this.fmt) {
      case 0:
         if(this.timestamp >= 16777215) {
            RtmpHelper.rtmp_set_24bits(rtmp_header, 1, 16777215);
         } else {
            RtmpHelper.rtmp_set_24bits(rtmp_header, 1, this.timestamp);
         }

         RtmpHelper.rtmp_set_24bits(rtmp_header, 4, this.message_size);
         rtmp_header[7] = this.type;
         RtmpHelper.rtmp_set_le_32bits(rtmp_header, 8, this.stream_id);
         rtmp_header_size = 12;
         if(this.timestamp >= 16777215) {
            RtmpHelper.rtmp_set_be_32bits(rtmp_header, 12, this.timestamp);
            rtmp_header_size = 16;
         }
         break;
      case 1:
         if(this.timestamp_delta >= 16777215) {
            RtmpHelper.rtmp_set_24bits(rtmp_header, 1, 16777215);
         } else {
            RtmpHelper.rtmp_set_24bits(rtmp_header, 1, this.timestamp_delta);
         }

         RtmpHelper.rtmp_set_24bits(rtmp_header, 4, this.message_size);
         rtmp_header[7] = this.type;
         rtmp_header_size = 8;
         if(this.timestamp_delta >= 16777215) {
            RtmpHelper.rtmp_set_be_32bits(rtmp_header, 8, this.timestamp_delta);
            rtmp_header_size = 12;
         }
         break;
      case 2:
         if(this.timestamp_delta >= 16777215) {
            RtmpHelper.rtmp_set_24bits(rtmp_header, 1, 16777215);
         } else {
            RtmpHelper.rtmp_set_24bits(rtmp_header, 1, this.timestamp_delta);
         }

         rtmp_header_size = 4;
         if(this.timestamp_delta >= 16777215) {
            RtmpHelper.rtmp_set_be_32bits(rtmp_header, 4, this.timestamp);
            rtmp_header_size = 8;
         }
         break;
      case 3:
      default:
         if(this.timestamp_delta >= 16777215) {
            RtmpHelper.rtmp_set_be_32bits(rtmp_header, 4, this.timestamp);
            rtmp_header_size = 5;
         }
      }

      return rtmp_header_size;
   }
}
