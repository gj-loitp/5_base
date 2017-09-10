package com.artfulbits.libstream;

import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class RtmpConnection extends BaseConnection {

   private static final String TAG = "RtmpConnection";
   byte[] rtmp_header = new byte[16];
   double clientid_ = -1.0D;
   double streamid_ = -1.0D;
   boolean reply_received_ = false;
   RtmpConnection.RTMP_CONNECTION_STATE state_;
   Streamer.STATUS status_;
   String app_;
   String stream_;
   final int RTMP_HANDSHAKE_PACKET_SIZE;
   final byte RTMP_CLIENT_VER1;
   final byte RTMP_CLIENT_VER2;
   final byte RTMP_CLIENT_VER3;
   final byte RTMP_CLIENT_VER4;
   final byte RTMP_PROTOCOL_VERSION;
   final byte RTMP_SYSTEM_CHANNEL;
   final byte[] RTMP_HANDSHARE_HEADER;
   final int CONNECT_COMMAND_MAX_SIZE;
   final byte RTMP_NETWORK_CHANNEL;
   final byte RTMP_CHUNK_SIZE_TYPE;
   final int RTMP_PUBLISH_CHUNK_SIZE;
   final byte RTMP_INVOKE_TYPE;
   static final int PUBLISH_COMMAND_MAX_SIZE = 3072;
   int incomming_chunk_size_;
   int acknowledgement_size_;
   boolean new_chunk;
   RtmpChunkStream chunk_stream;
   HashMap chunk_streams;
   boolean wanted_to_reply_client;
   boolean shutdown_after_reply_;
   int pending_chunk_streams;
   long total_received_size_;
   long last_acknoledged_size_;
   StreamBuffer.VideoFormatParams videoFormatParams_;
   StreamBuffer.AudioFormatParams audioFormatParams_;
   static final byte KEY_FRAME = 1;
   static final byte AVC_SEQUENCE_HEADER = 0;
   static final byte audioTagHeaderFirstByte = -81;
   static final byte AAC_SEQUENCE_HEADER = 0;
   RtmpPublisherChunkStreamInfo rtmp_metadata_chunk_stream_;
   RtmpPublisherChunkStreamInfo rtmp_audio_chunk_stream_;
   RtmpPublisherChunkStreamInfo rtmp_video_chunk_stream_;
   static final byte AVC = 7;
   static final byte AVC_NALU = 1;
   int avc_part_offset;
   final byte AAC;
   final byte AAC_RAW;
   BufferItem currentItem_;
   long message_index_;
   boolean send_headers;
   long initial_timestamp;


   RtmpConnection.RTMPCommandResponse get_command_response(double transactionId) {
      if((double)((int)transactionId) != transactionId) {
         return RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_UNKNOWN;
      } else {
         switch((int)transactionId) {
         case -1:
            if(this.state_ == RtmpConnection.RTMP_CONNECTION_STATE.PUBLISH) {
               return RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_PUBLISH;
            }

            return RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_UNKNOWN;
         case 0:
         default:
            return RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_UNKNOWN;
         case 1:
            if(this.state_ == RtmpConnection.RTMP_CONNECTION_STATE.CONNECT) {
               return RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_CONNECT;
            }

            return RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_UNKNOWN;
         case 2:
            return this.state_ == RtmpConnection.RTMP_CONNECTION_STATE.CREATE_STREAM?RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_CREATE_STREAM:RtmpConnection.RTMPCommandResponse.RTMP_COMMAND_RESPONSE_UNKNOWN;
         }
      }
   }

   void success_command_response(double transactionId, double aux) {
      Log.d("RtmpConnection", "success_command_response");
      switch((int)transactionId) {
      case -1:
         if(this.state_ != RtmpConnection.RTMP_CONNECTION_STATE.PUBLISH || aux != this.streamid_) {
            this.Close();
            return;
         }

         this.reply_received_ = true;
      case 0:
      default:
         break;
      case 1:
         if(this.state_ != RtmpConnection.RTMP_CONNECTION_STATE.CONNECT) {
            this.Close();
            return;
         }

         this.clientid_ = aux;
         this.reply_received_ = true;
         break;
      case 2:
         if(this.state_ != RtmpConnection.RTMP_CONNECTION_STATE.CREATE_STREAM) {
            this.Close();
            return;
         }

         this.streamid_ = aux;
         this.reply_received_ = true;
      }

   }

   RtmpConnection(ConnectionManager connectionManager, int connectionId, Streamer.MODE mode, String uri, String host, int port, String app, String stream, Streamer.Listener listener) throws IOException {
      super(connectionManager, connectionId, mode, host, port, listener);
      this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.INITIAL;
      this.status_ = Streamer.STATUS.CONN_FAIL;
      this.RTMP_HANDSHAKE_PACKET_SIZE = 1536;
      this.RTMP_CLIENT_VER1 = 10;
      this.RTMP_CLIENT_VER2 = 0;
      this.RTMP_CLIENT_VER3 = 0;
      this.RTMP_CLIENT_VER4 = 1;
      this.RTMP_PROTOCOL_VERSION = 3;
      this.RTMP_SYSTEM_CHANNEL = 3;
      this.RTMP_HANDSHARE_HEADER = new byte[]{(byte)3, (byte)0, (byte)0, (byte)0, (byte)0, (byte)10, (byte)0, (byte)0, (byte)1};
      this.CONNECT_COMMAND_MAX_SIZE = 3072;
      this.RTMP_NETWORK_CHANNEL = 2;
      this.RTMP_CHUNK_SIZE_TYPE = 1;
      this.RTMP_PUBLISH_CHUNK_SIZE = 1048576;
      this.RTMP_INVOKE_TYPE = 20;
      this.incomming_chunk_size_ = 128;
      this.new_chunk = true;
      this.chunk_streams = new HashMap();
      this.pending_chunk_streams = 0;
      this.total_received_size_ = 0L;
      this.last_acknoledged_size_ = 0L;
      this.rtmp_metadata_chunk_stream_ = new RtmpPublisherChunkStreamInfo((byte)18);
      this.rtmp_audio_chunk_stream_ = new RtmpPublisherChunkStreamInfo((byte)8);
      this.rtmp_video_chunk_stream_ = new RtmpPublisherChunkStreamInfo((byte)9);
      this.avc_part_offset = 0;
      this.AAC = 10;
      this.AAC_RAW = 1;
      this.message_index_ = 0L;
      this.send_headers = true;
      this.initial_timestamp = 0L;
      Log.d("RtmpConnection", "RtmpConnection");
      this.connectionId_ = connectionId;
      this.app_ = app;
      this.stream_ = stream;
   }

   void sendHandshakeC0C1() {
      Log.d("RtmpConnection", "sendHandshakeC0C1");

      try {
         ByteBuffer e = ByteBuffer.allocate(1537);
         e.put(this.RTMP_HANDSHARE_HEADER);
         long seed = System.currentTimeMillis() / 1000L;
         byte[] t = new byte[]{(byte)((int)seed), (byte)((int)(seed >> 8)), (byte)((int)(seed >> 16)), (byte)((int)(seed >> 24))};

         for(int i_4 = 0; e.position() < e.limit(); i_4 = (i_4 + 1) % 4) {
            switch(i_4 % 4) {
            case 0:
               t[0] = (byte)(t[1] + t[2]);
               break;
            case 1:
               t[1] = (byte)(t[2] + t[3]);
               break;
            case 2:
               t[2] = (byte)(t[0] + t[1]);
               break;
            case 3:
               t[3] = (byte)(t[0] + t[2]);
            }

            e.put(t);
         }

         this.Send(e.array());
      } catch (IOException var6) {
         Log.e("RtmpConnection", Log.getStackTraceString(var6));
         this.Close();
      }

   }

   void sendHandshakeC2(byte[] handshakeS0) {
      try {
         this.Send(handshakeS0, 1, 1536);
      } catch (IOException var3) {
         Log.e("RtmpConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   void sendSetChunkSize(int chunk_size) {
      Log.d("RtmpConnection", "sendSetChunkSize");

      try {
         byte[] e = new byte[12];
         e[0] = 2;
         e[6] = 4;
         e[7] = 1;
         byte[] size = new byte[]{(byte)(chunk_size >> 24), (byte)(chunk_size >> 16), (byte)(chunk_size >> 8), (byte)chunk_size};
         this.Append(e);
         this.Send(size);
      } catch (Exception var4) {
         Log.e("RtmpConnection", Log.getStackTraceString(var4));
         this.Close();
      }

   }

   void sendConnect() {
      Log.d("RtmpConnection", "sendConnect");

      try {
         ByteBuffer e = ByteBuffer.allocate(3072);
         RtmpHelper.rtmp_write_string(e, "connect");
         RtmpHelper.rtmp_write_number(e, 1.0D);
         RtmpHelper.rtmp_write_object_start(e);
         RtmpHelper.rtmp_write_field_name(e, "app");
         RtmpHelper.rtmp_write_string(e, this.app_);
         RtmpHelper.rtmp_write_field_name(e, "tcUrl");
         String tcUrl = "rtmp://" + this.host_ + ":" + this.port_ + "/" + this.app_;
         RtmpHelper.rtmp_write_string(e, tcUrl);
         RtmpHelper.rtmp_write_field_name(e, "flashVer");
         RtmpHelper.rtmp_write_string(e, "FMLE/3.0 (compatible; " + this.connectionManager_.getUserAgent() + ")");
         RtmpHelper.rtmp_write_field_name(e, "fpad");
         RtmpHelper.rtmp_write_bool(e, (byte)0);
         RtmpHelper.rtmp_write_field_name(e, "capabilities");
         RtmpHelper.rtmp_write_number(e, 15.0D);
         RtmpHelper.rtmp_write_field_name(e, "audioCodecs");
         RtmpHelper.rtmp_write_number(e, 1028.0D);
         RtmpHelper.rtmp_write_field_name(e, "videoCodecs");
         RtmpHelper.rtmp_write_number(e, 128.0D);
         RtmpHelper.rtmp_write_field_name(e, "videoFunction");
         RtmpHelper.rtmp_write_number(e, 1.0D);
         RtmpHelper.rtmp_write_object_end(e);
         byte[] header = new byte[12];
         header[0] = 3;
         int packet_size = e.position() & '\uffff';
         header[5] = (byte)(packet_size >> 8 & 255);
         header[6] = (byte)(packet_size & 255);
         header[7] = 20;
         this.Send(header);
         this.Send(e.array(), 0, e.position());
      } catch (Exception var5) {
         Log.e("RtmpConnection", Log.getStackTraceString(var5));
         this.Close();
      }

   }

   void sendCreateStream() {
      Log.d("RtmpConnection", "sendCreateStream");

      try {
         ByteBuffer e = ByteBuffer.allocate(128);
         RtmpHelper.rtmp_write_string(e, "createStream");
         RtmpHelper.rtmp_write_number(e, 2.0D);
         RtmpHelper.rtmp_write_null(e);
         byte[] header = new byte[12];
         header[0] = 3;
         int packet_size = e.position();
         header[4] = 0;
         header[5] = 0;
         header[6] = (byte)packet_size;
         header[7] = 20;
         this.Append(header);
         this.Send(e.array(), 0, e.position());
      } catch (Exception var4) {
         Log.e("RtmpConnection", Log.getStackTraceString(var4));
         this.Close();
      }

   }

   void sendPublish() {
      Log.d("RtmpConnection", "sendPublish");

      try {
         ByteBuffer e = ByteBuffer.allocate(3072);
         e.put(new byte[12]);
         RtmpHelper.rtmp_write_string(e, "publish");
         RtmpHelper.rtmp_write_number(e, 0.0D);
         RtmpHelper.rtmp_write_null(e);
         RtmpHelper.rtmp_write_string(e, this.stream_);
         RtmpHelper.rtmp_write_string(e, "live");
         e.put(0, (byte)8);
         int packet_size = e.position() - 12;
         e.put(4, (byte)0);
         e.put(5, (byte)(packet_size >> 8 & 255));
         e.put(6, (byte)(packet_size & 255));
         e.put(7, (byte)20);
         int stream_id = (int)this.streamid_;
         this.rtmp_metadata_chunk_stream_.setStreamId(stream_id);
         this.rtmp_video_chunk_stream_.setStreamId(stream_id);
         this.rtmp_audio_chunk_stream_.setStreamId(stream_id);
         e.put(8, (byte)stream_id);
         e.put(9, (byte)(stream_id >> 8));
         e.put(10, (byte)(stream_id >> 16));
         e.put(11, (byte)(stream_id >> 24));
         this.Send(e.array(), 0, e.position());
      } catch (Exception var4) {
         Log.e("RtmpConnection", Log.getStackTraceString(var4));
         this.Close();
      }

   }

   void onConnect() {
      Log.d("RtmpConnection", "onConnect");
      this.status_ = Streamer.STATUS.UNKNOWN_FAIL;
      this.notifyOnStateChange(Streamer.CONNECTION_STATE.CONNECTED, Streamer.STATUS.SUCCESS);
      this.sendHandshakeC0C1();
      this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.C0C1;
   }

   protected synchronized void Close() {
      if(this.state_ != RtmpConnection.RTMP_CONNECTION_STATE.CLOSED) {
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.CLOSED;
         super.Close();
         this.notifyOnStateChange(Streamer.CONNECTION_STATE.DISCONNECTED, this.status_);
      }

   }

   int onRecv(ByteBuffer byteBuffer) {
      Log.d("RtmpConnection", "onRecv");
      switch(RtmpConnection.NamelessClass1594666098.$SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[this.state_.ordinal()]) {
      case 1:
         if(byteBuffer.position() < 3073) {
            return 0;
         }

         if(byteBuffer.array()[0] != 3) {
            Log.e("RtmpConnection", "Invalid protocol version: " + byteBuffer.array()[0]);
            this.Close();
            return 0;
         }

         this.sendHandshakeC2(byteBuffer.array());
         RtmpHelper.skip(byteBuffer, byteBuffer.position());
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.C2;
         this.sendSetChunkSize(1048576);
         this.sendConnect();
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.CONNECT;
         break;
      case 2:
         this.processChunks(byteBuffer);
         if(!this.reply_received_) {
            return 0;
         }

         this.sendCreateStream();
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.CREATE_STREAM;
         break;
      case 3:
         this.processChunks(byteBuffer);
         if(!this.reply_received_) {
            return 0;
         }

         this.notifyOnStateChange(Streamer.CONNECTION_STATE.SETUP, Streamer.STATUS.SUCCESS);
         this.sendPublish();
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.PUBLISH;
         break;
      case 4:
         this.processChunks(byteBuffer);
         if(!this.reply_received_) {
            return 0;
         }

         this.sendMetaData();
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.SEND_NEXT_ITEM;
         this.notifyOnStateChange(Streamer.CONNECTION_STATE.RECORD, Streamer.STATUS.SUCCESS);
         this.sendNextItem();
         break;
      case 5:
      case 6:
         this.processChunks(byteBuffer);
         break;
      default:
         this.Close();
      }

      return 0;
   }

   void OnSend() {
      Log.v("RtmpConnection", "OnSend");
      switch(RtmpConnection.NamelessClass1594666098.$SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[this.state_.ordinal()]) {
      case 5:
         this.sendNextItem();
         break;
      case 6:
         while(this.avc_part_offset < this.currentItem_.getData().length) {
            int part_len = this.sendVideoPart(this.currentItem_, this.avc_part_offset);
            if(part_len <= 0) {
               Log.e("RtmpConnection", "failed to send video part");
               return;
            }

            this.avc_part_offset += part_len;
            if(this.getSendBufferRemaining() > 0) {
               this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.SEND_VIDEO_PART;
               return;
            }
         }

         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.SEND_NEXT_ITEM;
         this.sendNextItem();
         break;
      case 7:
         this.sendSetChunkSize(1048576);
         this.sendConnect();
         this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.CONNECT;
      }

   }

   void set_incomming_chunk_size(int incomming_chunk_size) {
      this.incomming_chunk_size_ = incomming_chunk_size;
   }

   int get_incomming_chunk_size() {
      return this.incomming_chunk_size_;
   }

   void set_acknowledgement_size(int acknowledgement_size) {
      this.acknowledgement_size_ = acknowledgement_size;
   }

   void processChunks(ByteBuffer buffer) {
      while(true) {
         try {
            if(buffer.position() > 0) {
               int e1;
               if(this.new_chunk) {
                  e1 = buffer.get(0) & 63;
                  if(e1 == 0) {
                     if(buffer.position() < 2) {
                        return;
                     }

                     e1 = buffer.get(1) + 64;
                  } else if(e1 == 1) {
                     if(buffer.position() < 3) {
                        return;
                     }

                     e1 = (buffer.get(1) << 8) + buffer.get(1) + 64;
                  }

                  if(e1 < 2) {
                     this.Close();
                     return;
                  }

                  this.chunk_stream = (RtmpChunkStream)this.chunk_streams.get(Integer.valueOf(e1));
                  if(null == this.chunk_stream) {
                     this.chunk_stream = new RtmpChunkStream(this, e1);
                     this.chunk_streams.put(Integer.valueOf(e1), this.chunk_stream);
                  }
               }

               e1 = this.chunk_stream.processChunk(buffer);
               if((e1 & 2) != 0) {
                  this.Close();
                  return;
               }

               if((e1 & 4) != 0) {
                  this.wanted_to_reply_client = true;
               }

               if((e1 & 16) != 0) {
                  this.shutdown_after_reply_ = true;
                  this.wanted_to_reply_client = true;
               }

               if((e1 & 8) != 0) {
                  this.new_chunk = true;
               } else {
                  this.new_chunk = false;
               }

               if((e1 & 1) != 0) {
                  return;
               }

               this.pending_chunk_streams += this.chunk_stream.get_message_complete_index();
               continue;
            }

            RtmpHelper.reset(buffer);
            if(this.pending_chunk_streams > 0) {
               return;
            }

            if(this.pending_chunk_streams < 0) {
               this.Close();
               return;
            }

            if(this.acknowledgement_size_ > 0) {
               if(this.total_received_size_ >= -268435456L) {
                  this.total_received_size_ -= this.last_acknoledged_size_;
                  this.last_acknoledged_size_ = 0L;
               }

               long e = this.total_received_size_ - this.last_acknoledged_size_;
               if(e >= (long)this.acknowledgement_size_) {
                  if(!this.sendAcknowledgement((int)this.total_received_size_)) {
                     this.Close();
                     return;
                  }

                  this.last_acknoledged_size_ = this.total_received_size_;
                  this.wanted_to_reply_client = true;
               }
            }
         } catch (Exception var4) {
            Log.e("RtmpConnection", Log.getStackTraceString(var4));
            this.Close();
         }

         return;
      }
   }

   boolean sendAcknowledgement(int acknoledgement) {
      Log.d("RtmpConnection", "sendAcknowledgement");
      byte[] header = new byte[12];
      header[0] = 2;
      header[6] = 4;
      header[7] = 3;
      byte[] size = new byte[]{(byte)(acknoledgement >> 24), (byte)(acknoledgement >> 16), (byte)(acknoledgement >> 8), (byte)acknoledgement};

      try {
         this.Append(header);
         this.Send(size);
         return true;
      } catch (Exception var5) {
         Log.e("RtmpConnection", Log.getStackTraceString(var5));
         return false;
      }
   }

   ByteBuffer createH264ConfigRecord(byte[] sps, byte[] pps) {
      if(sps.length < 4) {
         Log.e("RtmpConnection", "sps must be at least 4 bytes long");
         return null;
      } else {
         ByteBuffer h264_config_record = ByteBuffer.allocate(11 + sps.length + pps.length);
         h264_config_record.put((byte)1);
         h264_config_record.put(sps, 1, 3);
         h264_config_record.put((byte)-1);
         h264_config_record.put((byte)-31);
         h264_config_record.put((byte)(255 & sps.length >> 8));
         h264_config_record.put((byte)(255 & sps.length));
         h264_config_record.put(sps);
         h264_config_record.put((byte)1);
         h264_config_record.put((byte)(255 & pps.length >> 8));
         h264_config_record.put((byte)(255 & pps.length));
         h264_config_record.put(pps);
         return h264_config_record;
      }
   }

   void sendMetaData() {
      Log.d("RtmpConnection", "sendMetaData");

      try {
         ByteBuffer e = ByteBuffer.allocate(1500);
         RtmpHelper.rtmp_write_string(e, "onMetaData");
         RtmpHelper.rtmp_write_object_start(e);
         if(this.mode_ == Streamer.MODE.VIDEO_ONLY || this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
            this.videoFormatParams_ = this.connectionManager_.getStreamBuffer().getVideoFormatParams();
            if(null == this.videoFormatParams_) {
               Log.e("RtmpConnection", "failed to get avc params");
               this.Close();
               return;
            }

            H264SpsInfo rtmp_header_len = H264SpsInfo.getSpsInfo(this.videoFormatParams_.sps_buf, this.videoFormatParams_.sps_len);
            if(null == rtmp_header_len) {
               Log.e("RtmpConnection", "failed to get sps info");
               this.Close();
               return;
            }

            Log.d("RtmpConnection", "from sps: width=" + rtmp_header_len.width + ";height=" + rtmp_header_len.height);
            RtmpHelper.rtmp_write_field_name(e, "width");
            RtmpHelper.rtmp_write_number(e, (double)rtmp_header_len.width);
            RtmpHelper.rtmp_write_field_name(e, "height");
            RtmpHelper.rtmp_write_number(e, (double)rtmp_header_len.height);
            RtmpHelper.rtmp_write_field_name(e, "videocodecid");
            RtmpHelper.rtmp_write_string(e, "avc1");
         }

         if(this.mode_ == Streamer.MODE.AUDIO_ONLY || this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
            RtmpHelper.rtmp_write_field_name(e, "audiocodecid");
            RtmpHelper.rtmp_write_string(e, "mp4a");
         }

         RtmpHelper.rtmp_write_object_end(e);
         int rtmp_header_len1 = this.rtmp_metadata_chunk_stream_.fillHeader(this.rtmp_header, e.position(), 0);
         this.Append(this.rtmp_header, 0, rtmp_header_len1);
         this.Send(e.array(), 0, e.position());
      } catch (Exception var3) {
         Log.e("RtmpConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   void sendAvcHeader() {
      Log.d("RtmpConnection", "sendAvcHeader");

      try {
         byte[] e = new byte[]{(byte)23, (byte)0, (byte)0, (byte)0, (byte)0};
         StreamBuffer.VideoFormatParams videoFormatParams = this.connectionManager_.getStreamBuffer().getVideoFormatParams();
         ByteBuffer h264_config_record = this.createH264ConfigRecord(videoFormatParams.sps_buf, videoFormatParams.pps_buf);
         int rtmp_header_len = this.rtmp_video_chunk_stream_.fillHeader(this.rtmp_header, e.length + h264_config_record.position(), 0);
         this.Append(this.rtmp_header, 0, rtmp_header_len);
         this.Append(e);
         this.Send(h264_config_record.array(), 0, h264_config_record.position());
      } catch (Exception var5) {
         Log.e("RtmpConnection", Log.getStackTraceString(var5));
         this.Close();
      }

   }

   void sendAacHeader() {
      Log.d("RtmpConnection", "sendAacHeader");

      try {
         byte[] e = new byte[]{(byte)-81, (byte)0};
         this.audioFormatParams_ = this.connectionManager_.getStreamBuffer().getAudioFormatParams();
         if(null == this.audioFormatParams_) {
            Log.e("RtmpConnection", "failed to get aac params");
            this.Close();
            return;
         }

         int rtmp_header_len = this.rtmp_audio_chunk_stream_.fillHeader(this.rtmp_header, e.length + this.audioFormatParams_.config_len, 0);
         this.Append(this.rtmp_header, 0, rtmp_header_len);
         this.Append(e);
         this.Send(this.audioFormatParams_.config_buf, 0, this.audioFormatParams_.config_len);
      } catch (Exception var3) {
         Log.e("RtmpConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   static byte[] htonl(int x) {
      byte[] res = new byte[4];

      for(int i = 0; i < 4; ++i) {
         res[i] = (byte)(x >> 24);
         x <<= 8;
      }

      return res;
   }

   void sendVideoFrameHeader(BufferItem item) {
      Log.v("RtmpConnection", "sendVideoFrameHeader id=" + item.getMessageIndex());

      try {
         long e = this.getRtmpTimestamp(item, 1000);
         byte rtmp_composition_time_offset = 0;
         byte[] buffer = item.getData();
         int nal_ref_idc = buffer[0] >> 5 & 3;
         int nal_unit_type = buffer[0] & 31;
         byte frame_type = 2;
         if(nal_ref_idc != 0 && nal_unit_type == 5) {
            frame_type = 1;
         }

         byte[] rtmp_message_header = new byte[]{(byte)(frame_type << 4 | 7), (byte)1, (byte)(rtmp_composition_time_offset >> 16 & 255), (byte)(rtmp_composition_time_offset >> 8 & 255), (byte)(rtmp_composition_time_offset & 255)};
         int rtmp_header_len = this.rtmp_video_chunk_stream_.fillHeader(this.rtmp_header, rtmp_message_header.length + 4 + buffer.length, (int)e);
         this.Append(this.rtmp_header, 0, rtmp_header_len);
         this.Append(rtmp_message_header);
         byte[] nal_hdr = htonl(buffer.length);
         this.Append(nal_hdr);
      } catch (Exception var12) {
         Log.e("RtmpConnection", Log.getStackTraceString(var12));
         this.Close();
      }

   }

   private int sendVideoPart(BufferItem item, int offset) {
      Log.v("RtmpConnection", "sendVideoPart id=" + item.getMessageIndex() + ";offset=" + offset + "; len=" + item.getData().length);

      try {
         int e = item.getData().length - offset;
         if(e > 20000) {
            e = 20000;
         }

         this.Send(this.currentItem_.getData(), offset, e);
         return e;
      } catch (Exception var4) {
         Log.e("RtmpConnection", Log.getStackTraceString(var4));
         this.Close();
         return -1;
      }
   }

   long getRtmpTimestamp(BufferItem bufferItem, int clockRate) {
      return (long)clockRate * (bufferItem.getTimestamp() - this.initial_timestamp) / 1000000L;
   }

   void sendAudioFrame(BufferItem item) {
      Log.v("RtmpConnection", "sendAudioFrame id=" + item.getMessageIndex());

      try {
         byte[] e = new byte[]{(byte)-81, (byte)1};
         byte[] buffer = item.getData();
         long timestamp = this.getRtmpTimestamp(item, 1000);
         int rtmp_header_len = this.rtmp_audio_chunk_stream_.fillHeader(this.rtmp_header, e.length + buffer.length, (int)timestamp);
         this.Append(this.rtmp_header, 0, rtmp_header_len);
         this.Append(e);
         this.Send(buffer);
         ++this.audioPacketsSent_;
      } catch (Exception var7) {
         Log.e("RtmpConnection", Log.getStackTraceString(var7));
         this.Close();
      }

   }

   void sendNextItem() {
      label71:
      while(this.getSendBufferRemaining() <= 0) {
         this.currentItem_ = this.connectionManager_.getStreamBuffer().getItem(this.message_index_);
         if(null == this.currentItem_) {
            return;
         }

         this.message_index_ = this.currentItem_.getMessageIndex() + 1L;
         switch(RtmpConnection.NamelessClass1594666098.$SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE[this.currentItem_.getMessageType().ordinal()]) {
         case 1:
            if(this.mode_ != Streamer.MODE.AUDIO_VIDEO && this.mode_ != Streamer.MODE.VIDEO_ONLY || (this.currentItem_.getFlags() & 1) == 0 && (this.videoPacketsSent_ == 0L || this.currentItem_.getFrameId() - this.lastVideoFrameId > 1L)) {
               break;
            }

            this.updateVideoPacketsLost(this.currentItem_);
            if(this.send_headers) {
               this.send_headers = false;
               this.initial_timestamp = this.currentItem_.getTimestamp();
               this.sendAvcHeader();
               if(this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
                  this.sendAacHeader();
               }
            }

            if(this.currentItem_.getTimestamp() - this.initial_timestamp < 0L) {
               break;
            }

            this.sendVideoFrameHeader(this.currentItem_);
            this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.SEND_VIDEO_PART;
            this.avc_part_offset = 0;

            do {
               if(this.avc_part_offset >= this.currentItem_.getData().length) {
                  ++this.videoPacketsSent_;
                  this.state_ = RtmpConnection.RTMP_CONNECTION_STATE.SEND_NEXT_ITEM;
                  continue label71;
               }

               int part_len = this.sendVideoPart(this.currentItem_, this.avc_part_offset);
               if(part_len <= 0) {
                  Log.e("RtmpConnection", "failed to send video part");
                  return;
               }

               this.avc_part_offset += part_len;
            } while(this.getSendBufferRemaining() <= 0);

            return;
         case 2:
            if(this.mode_ != Streamer.MODE.AUDIO_VIDEO && this.mode_ != Streamer.MODE.AUDIO_ONLY) {
               break;
            }

            this.updateAudioPacketsLost(this.currentItem_);
            if(this.send_headers) {
               this.send_headers = false;
               this.initial_timestamp = this.currentItem_.getTimestamp();
               this.sendAacHeader();
               if(this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
                  this.sendAvcHeader();
               }
            }

            if(this.currentItem_.getTimestamp() - this.initial_timestamp >= 0L) {
               this.sendAudioFrame(this.currentItem_);
            }
            break;
         default:
            Log.e("RtmpConnection", "unsupported frame type " + this.currentItem_.getMessageType());
         }
      }

   }

   static enum RTMP_CONNECTION_STATE {

      INITIAL("INITIAL", 0),
      C0C1("C0C1", 1),
      C2("C2", 2),
      CONNECT("CONNECT", 3),
      CREATE_STREAM("CREATE_STREAM", 4),
      PUBLISH("PUBLISH", 5),
      SEND_NEXT_ITEM("SEND_NEXT_ITEM", 6),
      SEND_VIDEO_PART("SEND_VIDEO_PART", 7),
      CLOSED("CLOSED", 8);
      // $FF: synthetic field
      private static final RtmpConnection.RTMP_CONNECTION_STATE[] $VALUES = new RtmpConnection.RTMP_CONNECTION_STATE[]{INITIAL, C0C1, C2, CONNECT, CREATE_STREAM, PUBLISH, SEND_NEXT_ITEM, SEND_VIDEO_PART, CLOSED};


      private RTMP_CONNECTION_STATE(String var1, int var2) {}

   }

   static enum RTMPCommandResponse {

      RTMP_COMMAND_RESPONSE_UNKNOWN("RTMP_COMMAND_RESPONSE_UNKNOWN", 0),
      RTMP_COMMAND_RESPONSE_CONNECT("RTMP_COMMAND_RESPONSE_CONNECT", 1),
      RTMP_COMMAND_RESPONSE_CREATE_STREAM("RTMP_COMMAND_RESPONSE_CREATE_STREAM", 2),
      RTMP_COMMAND_RESPONSE_PUBLISH("RTMP_COMMAND_RESPONSE_PUBLISH", 3);
      // $FF: synthetic field
      private static final RtmpConnection.RTMPCommandResponse[] $VALUES = new RtmpConnection.RTMPCommandResponse[]{RTMP_COMMAND_RESPONSE_UNKNOWN, RTMP_COMMAND_RESPONSE_CONNECT, RTMP_COMMAND_RESPONSE_CREATE_STREAM, RTMP_COMMAND_RESPONSE_PUBLISH};


      private RTMPCommandResponse(String var1, int var2) {}

   }

   // $FF: synthetic class
   static class NamelessClass1594666098 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE;
      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE = new int[BufferItem.FRAME_TYPE.values().length];


      static {
         try {
            $SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE[BufferItem.FRAME_TYPE.VIDEO.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE[BufferItem.FRAME_TYPE.AUDIO.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
            ;
         }

         $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE = new int[RtmpConnection.RTMP_CONNECTION_STATE.values().length];

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.C0C1.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.CONNECT.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.CREATE_STREAM.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.PUBLISH.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.SEND_NEXT_ITEM.ordinal()] = 5;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.SEND_VIDEO_PART.ordinal()] = 6;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtmpConnection$RTMP_CONNECTION_STATE[RtmpConnection.RTMP_CONNECTION_STATE.C2.ordinal()] = 7;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }
}
