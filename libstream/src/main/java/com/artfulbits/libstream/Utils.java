package com.artfulbits.libstream;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Utils {

   private static final String TAG = "Utils";


   public static String byteArrayToHex(byte[] byteArray, int offset, int len) {
      if(offset >= byteArray.length) {
         return "";
      } else {
         if(len > byteArray.length - offset) {
            len = byteArray.length - offset;
         }

         StringBuilder sb = new StringBuilder(len * 2);

         for(int i = offset; i < offset + len; ++i) {
            sb.append(String.format("%02x", new Object[]{Byte.valueOf(byteArray[i])}));
         }

         return sb.toString();
      }
   }

   public static String hexmd5(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest digester = MessageDigest.getInstance("MD5");
      digester.update(s.getBytes("US-ASCII"));
      byte[] md5 = digester.digest();
      return byteArrayToHex(md5, 0, md5.length);
   }

   public static int indexOf(ByteBuffer buffer, byte[] pattern) {
      int i = buffer.position();

      while(i < buffer.limit()) {
         boolean match = true;
         int j = 0;

         while(true) {
            if(j < pattern.length && i + j < buffer.limit()) {
               if(buffer.get(i + j) == pattern[j]) {
                  ++j;
                  continue;
               }

               match = false;
            }

            if(match) {
               return i;
            }

            ++i;
            break;
         }
      }

      return -1;
   }

   public static boolean LTrim(ByteBuffer buffer, byte[] pattern) {
      if(buffer.limit() - buffer.position() < pattern.length) {
         return false;
      } else {
         boolean match = true;

         for(int i = 0; i < pattern.length; ++i) {
            if(buffer.get(buffer.position() + i) != pattern[i]) {
               match = false;
               break;
            }
         }

         if(match) {
            buffer.position(buffer.position() + pattern.length);
            return true;
         } else {
            return false;
         }
      }
   }
}
