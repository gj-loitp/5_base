package com.artfulbits.libstream;

import android.util.Log;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RtspParser {

   private static final String TAG = "RtspParser";
   private static final Pattern status_line_pattern = Pattern.compile("RTSP\\/1.0\\s+(\\d\\d\\d)\\s+(.+)");
   private static final Pattern hdr_line_pattern = Pattern.compile("(\\S+):\\s+(.*)");
   private int status_code;
   private String status_text;
   private RtspParser.RTSP_PARSER_STATE state_;
   private int content_length_;
   private Boolean rtsp_complete_;
   private HashMap rtsp_hdr_;


   RtspParser() {
      this.state_ = RtspParser.RTSP_PARSER_STATE.INTERLEAVED;
      this.content_length_ = 0;
      this.rtsp_complete_ = Boolean.valueOf(false);
      this.rtsp_hdr_ = new HashMap();
   }

   Boolean get_complete() {
      return this.rtsp_complete_;
   }

   int get_status_code() {
      return this.status_code;
   }

   String get_header(String name) {
      return (String)this.rtsp_hdr_.get(name.toUpperCase());
   }

   Boolean parse_status_line(String s) {
      Matcher m = status_line_pattern.matcher(s);
      if(!m.find()) {
         return Boolean.valueOf(false);
      } else {
         this.status_code = Integer.parseInt(m.group(1));
         Log.d("RtspParser", "status_code=" + this.status_code);
         this.status_text = m.group(2);
         Log.d("RtspParser", "status_text=" + this.status_text);
         return Boolean.valueOf(true);
      }
   }

   Boolean parse_hdr_line(String s) {
      Matcher m = hdr_line_pattern.matcher(s);
      if(!m.find()) {
         return Boolean.valueOf(false);
      } else {
         String name = m.group(1).trim();
         String value = m.group(2).trim();
         if(name.equalsIgnoreCase("Content-length")) {
            this.content_length_ = Integer.parseInt(value);
            Log.d("RtspParser", "content_length_=" + this.content_length_);
         } else if(name.equalsIgnoreCase("WWW-Authenticate")) {
            int sp_pos = value.indexOf(" ");
            if(-1 != sp_pos) {
               String authScheme = value.substring(0, sp_pos);
               String authParams = value.substring(sp_pos + 1).trim();
               if(authScheme.equalsIgnoreCase("Digest")) {
                  this.parseAuth("WWW-Authenticate-Digest", authParams);
               } else {
                  if(!authScheme.equalsIgnoreCase("Basic")) {
                     Log.d("RtspParser", "unsupported auth scheme=" + authScheme);
                     return Boolean.valueOf(true);
                  }

                  this.parseAuth("WWW-Authenticate-Basic", authParams);
               }
            }
         } else {
            Log.d("RtspParser", name + ": " + value);
            this.rtsp_hdr_.put(name.toUpperCase(), value);
         }

         return Boolean.valueOf(true);
      }
   }

   private void parseAuth(String key, String value) {
      String[] paramList = value.split(",");
      String[] arr$ = paramList;
      int len$ = paramList.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String s = arr$[i$];
         int pos = s.indexOf("=");
         if(pos != -1) {
            String paramName = s.substring(0, pos).trim();
            if(paramName.length() != 0) {
               paramName = key + "-" + paramName;
               paramName = paramName.toUpperCase();
               String paramValue = s.substring(pos + 1).replace("\"", "").trim();
               this.rtsp_hdr_.put(paramName.toUpperCase(), paramValue);
            }
         }
      }

   }

   int get_line(byte[] buffer, int offset, int len, StringBuilder line) {
      Boolean cr = Boolean.valueOf(false);

      for(int i = offset; i < len; ++i) {
         if(cr.booleanValue() && buffer[i] == 10) {
            return line.length() + 2;
         }

         cr = Boolean.valueOf(false);
         if(buffer[i] == 13) {
            cr = Boolean.valueOf(true);
         } else {
            line.append((char)buffer[i]);
         }
      }

      return -1;
   }

   public int parse(byte[] buffer, int len) {
      int offset = 0;

      while(len > 0) {
         boolean parsed = false;
         int parsed1;
         switch(RtspParser.NamelessClass879878201.$SwitchMap$com$wmspanel$libstream$RtspParser$RTSP_PARSER_STATE[this.state_.ordinal()]) {
         case 1:
            this.status_code = -1;
            this.status_text = "";
            this.rtsp_hdr_.clear();
            this.content_length_ = 0;
            if(len < 4) {
               return 0;
            }

            if(buffer[offset] == 82 && buffer[offset + 1] == 84 && buffer[offset + 2] == 83 && buffer[offset + 3] == 80) {
               this.state_ = RtspParser.RTSP_PARSER_STATE.STATUS_LINE;
               break;
            }

            return offset;
         case 2:
            StringBuilder status_line = new StringBuilder();
            parsed1 = this.get_line(buffer, offset, len, status_line);
            if(-1 == parsed1) {
               return offset;
            }

            offset += parsed1;
            if(!this.parse_status_line(status_line.toString()).booleanValue()) {
               Log.e("RtspParser", "unable to parse status line: " + status_line);
               this.state_ = RtspParser.RTSP_PARSER_STATE.INTERLEAVED;
               return -1;
            }

            this.state_ = RtspParser.RTSP_PARSER_STATE.HDR_LINE;
            break;
         case 3:
            StringBuilder hdr_line = new StringBuilder();
            parsed1 = this.get_line(buffer, offset, len, hdr_line);
            if(-1 == parsed1) {
               return offset;
            }

            offset += parsed1;
            if(hdr_line.length() > 0) {
               if(!this.parse_hdr_line(hdr_line.toString()).booleanValue()) {
                  Log.e("RtspParser", "unable to parse header line: " + hdr_line);
                  this.state_ = RtspParser.RTSP_PARSER_STATE.INTERLEAVED;
                  return -1;
               }
            } else {
               if(this.content_length_ <= 0) {
                  this.rtsp_complete_ = Boolean.valueOf(true);
                  this.state_ = RtspParser.RTSP_PARSER_STATE.INTERLEAVED;
                  return offset;
               }

               this.state_ = RtspParser.RTSP_PARSER_STATE.BODY;
            }
            break;
         case 4:
            if(len < this.content_length_) {
               return offset;
            }

            offset += this.content_length_;
            this.rtsp_complete_ = Boolean.valueOf(true);
            this.state_ = RtspParser.RTSP_PARSER_STATE.INTERLEAVED;
            return offset;
         }
      }

      return 0;
   }


   // $FF: synthetic class
   static class NamelessClass879878201 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$RtspParser$RTSP_PARSER_STATE = new int[RtspParser.RTSP_PARSER_STATE.values().length];


      static {
         try {
            $SwitchMap$com$wmspanel$libstream$RtspParser$RTSP_PARSER_STATE[RtspParser.RTSP_PARSER_STATE.INTERLEAVED.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspParser$RTSP_PARSER_STATE[RtspParser.RTSP_PARSER_STATE.STATUS_LINE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspParser$RTSP_PARSER_STATE[RtspParser.RTSP_PARSER_STATE.HDR_LINE.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspParser$RTSP_PARSER_STATE[RtspParser.RTSP_PARSER_STATE.BODY.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }

   static enum RTSP_PARSER_STATE {

      INTERLEAVED("INTERLEAVED", 0),
      STATUS_LINE("STATUS_LINE", 1),
      HDR_LINE("HDR_LINE", 2),
      BODY("BODY", 3);
      // $FF: synthetic field
      private static final RtspParser.RTSP_PARSER_STATE[] $VALUES = new RtspParser.RTSP_PARSER_STATE[]{INTERLEAVED, STATUS_LINE, HDR_LINE, BODY};


      private RTSP_PARSER_STATE(String var1, int var2) {}

   }
}
