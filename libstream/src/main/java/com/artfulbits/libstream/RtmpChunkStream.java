package com.artfulbits.libstream;

import android.util.Log;
import java.nio.ByteBuffer;

public class RtmpChunkStream {

   private static final String TAG = "RtmpChunkStream";
   static final int WAITING_FOR_MORE_DATA = 1;
   static final int SHUTDOWN = 2;
   static final int WANT_TO_REPLY_CLIENT = 4;
   static final int NEW_CHUNK_FLAG = 8;
   static final int SHUTDOWN_AFTER_REPLY_FLAG = 16;
   RtmpConnection receiver_;
   boolean init = false;
   boolean new_chunk = true;
   static int RTMP_CHUNK_MAX_SIZE = 1048576;
   ByteBuffer message;
   int current_timestamp;
   int timestamp_delta;
   int message_length;
   byte message_type;
   int message_stream_id;
   int cur_chunk_size;
   int cur_chunk_used;
   int cs_id_;
   boolean new_message_state_;
   int new_message_index_;
   boolean shutdown_after_reply;


   RtmpChunkStream(RtmpConnection receiver, int cs_id) {
      this.message = ByteBuffer.allocate(RTMP_CHUNK_MAX_SIZE);
      this.new_message_state_ = true;
      this.new_message_index_ = 0;
      this.shutdown_after_reply = false;
      this.receiver_ = receiver;
      this.cs_id_ = cs_id;
   }

   int get_message_header_size(byte fmt) {
      switch(fmt) {
      case 0:
         return 11;
      case 1:
         return 7;
      case 2:
         return 3;
      case 3:
      default:
         return 0;
      }
   }

   void Reset() {
      RtmpHelper.reset(this.message);
   }

   int reminder(int offset, int buffer_initial, int buffer_used_size) {
      return buffer_initial + buffer_used_size - offset;
   }

   int processChunk(ByteBuffer buffer) {
      this.new_message_index_ = 0;
      byte initial_buffer = 0;
      int offset = 0;
      if(this.new_chunk) {
         byte available_payload = buffer.get(0);
         byte reply_to_client = (byte)(available_payload >> 6);
         byte basic_header_size = 1;
         int cs_id = available_payload & 63;
         if(0 == cs_id) {
            basic_header_size = 2;
         } else if(cs_id == 1) {
            basic_header_size = 3;
         }

         if(!this.init && reply_to_client != 0) {
            return 2;
         }

         int message_header = this.get_message_header_size(reply_to_client);
         if(basic_header_size + message_header > buffer.position()) {
            return 1;
         }

         offset += basic_header_size;
         Log.d("RtmpChunkStream", "fmt=" + reply_to_client);
         switch(reply_to_client) {
         case 0:
            this.timestamp_delta = this.current_timestamp = RtmpHelper.rtmp_get_24bits(buffer, offset);
            this.message_length = RtmpHelper.rtmp_get_24bits(buffer, offset + 3);
            this.message_type = buffer.get(offset + 6);
            this.message_stream_id = RtmpHelper.rtmp_get_le_32bits(buffer, offset + 7);
            if(!this.init) {
               this.init = true;
            }

            offset += 11;
            if(this.timestamp_delta == 16777215) {
               if(this.reminder(offset, initial_buffer, buffer.limit()) < 4) {
                  return 1;
               }

               this.timestamp_delta = this.current_timestamp = RtmpHelper.rtmp_get_be_32bits(buffer, offset);
               offset += 4;
            }

            RtmpHelper.reset(this.message);
            this.Reset();
            break;
         case 1:
            this.timestamp_delta = RtmpHelper.rtmp_get_24bits(buffer, offset);
            this.message_length = RtmpHelper.rtmp_get_24bits(buffer, offset + 3);
            this.message_type = buffer.get(offset + 6);
            offset += 7;
            if(this.timestamp_delta == 16777215) {
               if(this.reminder(offset, initial_buffer, buffer.limit()) < 4) {
                  return 1;
               }

               this.timestamp_delta = RtmpHelper.rtmp_get_be_32bits(buffer, offset);
               this.current_timestamp += this.timestamp_delta;
               offset += 4;
            } else {
               this.current_timestamp += this.timestamp_delta;
            }

            this.Reset();
            break;
         case 2:
            this.timestamp_delta = RtmpHelper.rtmp_get_24bits(buffer, offset);
            offset += 3;
            if(this.timestamp_delta == 16777215) {
               if(this.reminder(offset, initial_buffer, buffer.limit()) < 4) {
                  return 1;
               }

               this.timestamp_delta = RtmpHelper.rtmp_get_be_32bits(buffer, offset);
               offset += 4;
               this.current_timestamp += this.timestamp_delta;
            } else {
               this.current_timestamp += this.timestamp_delta;
            }
            break;
         case 3:
         default:
            if(this.timestamp_delta >= 16777215) {
               if(this.reminder(offset, initial_buffer, buffer.limit()) < 4) {
                  return 1;
               }

               this.timestamp_delta = RtmpHelper.rtmp_get_be_32bits(buffer, offset);
               offset += 4;
            }

            if(this.message.position() == 0) {
               this.current_timestamp += this.timestamp_delta;
            }
         }

         Log.d("RtmpChunkStream", "message_length=" + this.message_length);
         this.cur_chunk_size = Math.min(this.message_length - this.message.position(), this.receiver_.get_incomming_chunk_size());
         if(this.cur_chunk_size == 0) {
            this.new_chunk = true;
            RtmpHelper.skip(buffer, offset);
            return 8;
         }

         this.cur_chunk_used = 0;
         this.new_chunk = false;
      }

      int available_payload1 = Math.min(this.reminder(offset, initial_buffer, buffer.position()), this.cur_chunk_size - this.cur_chunk_used);
      if(available_payload1 == 0) {
         RtmpHelper.skip(buffer, offset - initial_buffer);
         return 1;
      } else {
         try {
            this.message.put(buffer.array(), offset, available_payload1);
         } catch (Exception var9) {
            Log.e("RtmpChunkStream", Log.getStackTraceString(var9));
            return 2;
         }

         offset += available_payload1;
         this.cur_chunk_used += available_payload1;
         RtmpHelper.skip(buffer, offset - initial_buffer);
         if(this.cur_chunk_size == this.cur_chunk_used) {
            this.new_chunk = true;
            Boolean reply_to_client1 = Boolean.valueOf(false);
            if(this.message.position() == this.message_length) {
               if(!this.processMessage(reply_to_client1)) {
                  return 2;
               }

               if(!this.new_message_state_) {
                  this.new_message_index_ = -1;
                  this.new_message_state_ = true;
               }

               if(reply_to_client1.booleanValue()) {
                  return 12 | (this.shutdown_after_reply?16:0);
               }
            } else if(this.new_message_state_) {
               this.new_message_index_ = 1;
               this.new_message_state_ = false;
            }

            return 8;
         } else {
            return 1;
         }
      }
   }

   boolean processMessage(Boolean reply_to_client) {
      reply_to_client = Boolean.valueOf(false);
      boolean result = false;
      if(this.cs_id_ == 2) {
         if(this.message_stream_id == 0) {
            switch(this.message_type) {
            case 1:
               result = this.processSetChunkSize();
               break;
            case 2:
               result = false;
               break;
            case 3:
               result = true;
               break;
            case 4:
               result = this.processUserControlMessageType(reply_to_client);
               break;
            case 5:
               result = this.processAcknowledgementSize();
               break;
            case 6:
               result = true;
               break;
            default:
               result = true;
            }
         } else {
            result = true;
         }
      } else {
         switch(this.message_type) {
         case 20:
            result = this.processInvokeAMF0(this.message);
            break;
         default:
            result = true;
         }
      }

      this.Reset();
      return result;
   }

   boolean processSetChunkSize() {
      if(this.message_length != 4) {
         return false;
      } else {
         int client_chunk_size = RtmpHelper.rtmp_get_be_32bits(this.message, 0);
         if(client_chunk_size > 16777215) {
            client_chunk_size = 16777215;
         }

         if(client_chunk_size < 1) {
            client_chunk_size = 1;
         }

         this.receiver_.set_incomming_chunk_size(client_chunk_size);
         return true;
      }
   }

   boolean processAcknowledgementSize() {
      if(this.message_length != 4) {
         return false;
      } else {
         int acknowledgement_size = RtmpHelper.rtmp_get_be_32bits(this.message, 0);
         this.receiver_.set_acknowledgement_size(acknowledgement_size);
         return true;
      }
   }

   boolean processUserControlMessageType(Boolean reply_to_client) {
      reply_to_client = Boolean.valueOf(false);
      if(this.message_length != 6) {
         return true;
      } else {
         int type = (this.message.get(0) << 16) + this.message.get(1);
         if(type == 6) {
            byte[] header = new byte[12];
            header[0] = 2;
            header[6] = 6;
            header[7] = 4;
            byte[] ping_response = new byte[]{(byte)0, (byte)7};

            try {
               this.receiver_.Send(header);
               this.receiver_.Send(ping_response);
               this.receiver_.Send(this.message.array(), 2, 4);
            } catch (Exception var6) {
               Log.e("RtmpChunkStream", Log.getStackTraceString(var6));
               return false;
            }

            reply_to_client = Boolean.valueOf(true);
         }

         return true;
      }
   }

   boolean processInvokeAMF0(ByteBuffer msg_buffer) {
      boolean processed = false;
      byte offset = 0;

      String command;
      try {
         command = RtmpHelper.rtmp_read_string(msg_buffer, 0);
      } catch (Exception var11) {
         Log.e("RtmpChunkStream", Log.getStackTraceString(var11));
         return false;
      }

      int offset1 = offset + 3 + command.length();
      double transactionId;
      RtmpConnection.RTMPCommandResponse cmd_response;
      if(command.equals("_result")) {
         transactionId = 0.0D;

         try {
            transactionId = RtmpHelper.rtmp_read_number(msg_buffer, offset1);
         } catch (Exception var9) {
            Log.e("RtmpChunkStream", "failed to process transaction id for _result command");
            Log.e("RtmpChunkStream", Log.getStackTraceString(var9));
            return false;
         }

         offset1 += 9;
         cmd_response = this.receiver_.get_command_response(transactionId);
         switch(RtmpChunkStream.NamelessClass2029750886.$SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMPCommandResponse[cmd_response.ordinal()]) {
         case 1:
            Log.e("RtmpChunkStream", "unknown command _result response");
            return false;
         case 2:
            this.receiver_.success_command_response(transactionId, (double)this.message_stream_id);
            return true;
         case 3:
            this.processCreateStreamResult(msg_buffer, offset1, transactionId);
            return true;
         default:
            return true;
         }
      } else {
         if(command.equals("onStatus")) {
            transactionId = 0.0D;

            try {
               RtmpHelper.rtmp_read_number(msg_buffer, offset1);
            } catch (Exception var10) {
               Log.e("RtmpChunkStream", "failed to process transaction id for _result command");
               Log.e("RtmpChunkStream", Log.getStackTraceString(var10));
               return false;
            }

            offset1 += 9;
            cmd_response = this.receiver_.get_command_response(-1.0D);
            if(cmd_response == RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_PUBLISH) {
               this.receiver_.success_command_response(-1.0D, (double)this.message_stream_id);
               return true;
            }
         } else if(command.equals("_error")) {
            Log.e("RtmpChunkStream", "_error response received");
            return false;
         }

         return true;
      }
   }

   boolean processCreateStreamResult(ByteBuffer msg_buffer, int offset, double transactionId) {
      try {
         RtmpHelper.rtmp_read_null(msg_buffer, offset);
         ++offset;
         double e = RtmpHelper.rtmp_read_number(msg_buffer, offset);
         offset += 9;
         this.receiver_.success_command_response(transactionId, e);
         return true;
      } catch (Exception var7) {
         Log.e("RtmpChunkStream", Log.getStackTraceString(var7));
         return false;
      }
   }

   int get_message_complete_index() {
      return this.new_message_index_;
   }


   // $FF: synthetic class
   static class NamelessClass2029750886 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMPCommandResponse = new int[RtmpConnection.RTMPCommandResponse.values().length];


      static {
         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMPCommandResponse[RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_UNKNOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMPCommandResponse[RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_CONNECT.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMPCommandResponse[RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_CREATE_STREAM.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }
}
