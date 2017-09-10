package com.artfulbits.libstream;

import android.util.Log;
import java.nio.ByteBuffer;

public class RtmpHelper {

   private static final String TAG = "RtmpHelper";
   static final byte DATA_TYPE_NUMBER = 0;
   static final byte DATA_TYPE_BOOLEAN = 1;
   static final byte DATA_TYPE_STRING = 2;
   static final byte DATA_TYPE_OBJECT = 3;
   static final byte AMF_DATA_TYPE_NULL = 5;
   static final byte DATA_TYPE_MIXED_ARRAY = 8;
   static final byte DATA_TYPE_OBJECT_END = 9;
   static final byte DATA_TYPE_STRICT_ARRAY = 10;
   static final byte RTMP_NETWORK_CHANNEL = 2;
   static final byte RTMP_SYSTEM_CHANNEL = 3;
   static final byte RTMP_SOURCE_CHANNEL = 8;
   static final byte RTMP_CHUNK_SIZE_TYPE = 1;
   static final byte RTMP_ABORT_TYPE = 2;
   static final byte RTMP_ACKNOWLEDGEMENT_TYPE = 3;
   static final byte RTMP_CHUNK_WINDOW_ACK_SIZE_TYPE = 5;
   static final byte RTMP_CHUNK_SET_PEER_BANDWIDTH_TYPE = 6;
   static final byte RTMP_PING_TYPE = 4;
   static final byte RTMP_CHUNK_USER_CONTROL_MESSAGE_TYPE = 4;
   static final byte RTMP_PING_REQUEST = 6;
   static final byte RTMP_PING_RESPONSE = 7;
   static final byte RTMP_CHUNK_AUDIO = 8;
   static final byte RTMP_CHUNK_VIDEO = 9;
   static final byte RTMP_DATA_MESSAGE_TYPE = 18;
   static final byte RTMP_INVOKE_TYPE = 20;


   static void rtmp_write_field_name(ByteBuffer dst, String value) {
      dst.put((byte)(value.length() >> 8));
      dst.put((byte)(value.length() & 255));
      dst.put(value.getBytes());
   }

   static void rtmp_write_string(ByteBuffer dst, String value) {
      dst.put((byte)2);
      dst.put((byte)(value.length() >> 8));
      dst.put((byte)(value.length() & 255));
      dst.put(value.getBytes());
   }

   static void rtmp_write_number(ByteBuffer dst, double value) {
      dst.put((byte)0);
      dst.putDouble(value);
   }

   static void rtmp_write_null(ByteBuffer dst) {
      dst.put((byte)5);
   }

   static void rtmp_write_object_start(ByteBuffer dst) {
      dst.put((byte)3);
   }

   static void rtmp_write_object_end(ByteBuffer dst) {
      dst.put((byte)0);
      dst.put((byte)0);
      dst.put((byte)9);
   }

   static void rtmp_write_bool(ByteBuffer dst, byte value) {
      dst.put((byte)1);
      dst.put(value);
   }

   static int rtmp_get_24bits(ByteBuffer buffer, int offset) {
      return (255 & buffer.get(offset)) << 16 | (255 & buffer.get(offset + 1)) << 8 | 255 & buffer.get(offset + 2);
   }

   static void rtmp_set_24bits(byte[] buffer, int offset, int value) {
      buffer[offset] = (byte)(value >> 16);
      buffer[offset + 1] = (byte)(value >> 8);
      buffer[offset + 2] = (byte)value;
   }

   static void rtmp_set_le_32bits(byte[] buffer, int offset, int value) {
      buffer[offset] = (byte)value;
      buffer[offset + 1] = (byte)(value >> 8);
      buffer[offset + 2] = (byte)(value >> 16);
      buffer[offset + 3] = (byte)(value >> 24);
   }

   static void rtmp_set_be_32bits(byte[] buffer, int offset, int value) {
      buffer[offset + 3] = (byte)value;
      buffer[offset + 2] = (byte)(value >> 8);
      buffer[offset + 1] = (byte)(value >> 16);
      buffer[offset] = (byte)(value >> 24);
   }

   static int rtmp_get_be_32bits(ByteBuffer buffer, int offset) {
      return (255 & buffer.get(offset)) << 24 | (255 & buffer.get(offset + 1)) << 16 | (255 & buffer.get(offset + 2)) << 8 | 255 & buffer.get(offset + 3);
   }

   static int rtmp_get_le_32bits(ByteBuffer buffer, int offset) {
      return (255 & buffer.get(offset + 3)) << 24 | (255 & buffer.get(offset + 2)) << 16 | (255 & buffer.get(offset + 1)) << 8 | 255 & buffer.get(offset);
   }

   static void rtmp_read_null(ByteBuffer buffer, int offset) throws RtmpHelper.RtmpHelperExcpetion {
      if(buffer.get(offset) != 5) {
         Log.e("RtmpHelper", "rtmp_read_string buffer not null[" + buffer.get(offset) + "]");
         throw new RtmpHelper.RtmpHelperExcpetion();
      }
   }

   static String rtmp_read_string(ByteBuffer buffer, int offset) throws RtmpHelper.RtmpHelperExcpetion {
      if(buffer.position() - offset < 3) {
         Log.e("RtmpHelper", "rtmp_read_string buffer too small[" + (buffer.position() - offset) + "]");
         throw new RtmpHelper.RtmpHelperExcpetion();
      } else if(buffer.get(offset) != 2) {
         Log.e("RtmpHelper", "rtmp_read_string buffer not string[" + buffer.get(offset) + "]");
         throw new RtmpHelper.RtmpHelperExcpetion();
      } else {
         int source_buffer_length = buffer.get(offset + 1) << 8 | buffer.get(offset + 2);
         if(3 + source_buffer_length > buffer.limit()) {
            throw new RtmpHelper.RtmpHelperExcpetion();
         } else {
            String rtmp_string = new String(buffer.array(), offset + 3, source_buffer_length);
            return rtmp_string;
         }
      }
   }

   static double rtmp_read_number(ByteBuffer buffer, int offset) throws RtmpHelper.RtmpHelperExcpetion {
      if(buffer.position() - offset < 9) {
         Log.e("RtmpHelper", "rtmp_read_number buffer too small[" + (buffer.position() - offset) + "]");
         throw new RtmpHelper.RtmpHelperExcpetion();
      } else if(buffer.get(offset) != 0) {
         Log.e("RtmpHelper", "rtmp_read_number buffer not number[" + buffer.get(offset) + "]");
         throw new RtmpHelper.RtmpHelperExcpetion();
      } else {
         double value = buffer.getDouble(offset + 1);
         return value;
      }
   }

   static void skip(ByteBuffer buffer, int bytesToSkip) {
      if(bytesToSkip >= buffer.position()) {
         buffer.position(0);
      } else {
         int newLen = buffer.position() - bytesToSkip;

         for(int i = 0; i < newLen; ++i) {
            buffer.put(i, buffer.get(bytesToSkip + i));
         }

         buffer.position(newLen);
      }
   }

   static void reset(ByteBuffer buffer) {
      buffer.position(0);
   }

   public static class RtmpHelperExcpetion extends Exception {

   }
}
