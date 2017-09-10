package com.artfulbits.libstream;

import android.util.Base64;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Random;

class RtspConnection extends BaseConnection {

   private static final String TAG = "RtspConnection";
   private RtspConnection.RTSP_CONNECTION_STATE rtsp_state_;
   private Streamer.STATUS rtsp_status_;
   private RtspParser parser_;
   private String uri_;
   private String user_;
   private String pass_;
   private byte[] rtspBuffer_ = new byte[32];
   StreamBuffer.VideoFormatParams videoFormatParams_;
   StreamBuffer.AudioFormatParams audioFormatParams_;
   private long message_index_ = 0L;
   final long NTP_SCALE_FRAC = 4294967296L;
   final int AVC_CLOCK_RATE = 90000;
   private BufferItem currentItem;
   private int avc_part_offset = -1;
   private int avc_ssrc_ = (new Random()).nextInt();
   private long avc_ntp_ts_ = -1L;
   private long avc_rtp_ts_ = -1L;
   private int aac_ssrc_ = (new Random()).nextInt();
   private long aac_ntp_ts_ = -1L;
   private long aac_rtp_ts_ = -1L;
   private int seqNum = 0;
   private final int MAX_NAL_UNIT_LEN = 20000;
   private int audioSeqNum = 0;
   private String digestNonce_;
   private String digestRealm_;
   private String basicRealm_;
   int cseq_ = 1;


   RtspConnection(ConnectionManager connectionManager, int connectionId, Streamer.MODE mode, String uri, String host, int port, String user, String pass, Streamer.Listener listener) throws IOException {
      super(connectionManager, connectionId, mode, host, port, listener);
      this.uri_ = uri;
      this.user_ = user;
      this.pass_ = pass;
      this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.INITIAL;
      this.rtsp_status_ = Streamer.STATUS.CONN_FAIL;
      this.parser_ = new RtspParser();
   }

   void onConnect() {
      this.rtsp_status_ = Streamer.STATUS.UNKNOWN_FAIL;
      this.notifyOnStateChange(Streamer.CONNECTION_STATE.CONNECTED, Streamer.STATUS.SUCCESS);
      this.sendOptions();
   }

   protected synchronized void Close() {
      if(this.rtsp_state_ != RtspConnection.RTSP_CONNECTION_STATE.CLOSED) {
         this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.CLOSED;
         super.Close();
         this.notifyOnStateChange(Streamer.CONNECTION_STATE.DISCONNECTED, this.rtsp_status_);
      }

   }

   int onRecv(ByteBuffer byteBuffer) {
      int bytesParsed = this.parser_.parse(byteBuffer.array(), byteBuffer.position());
      if(bytesParsed < 0) {
         Log.e("RtspConnection", "failed to parse rtsp");
         this.Close();
         return byteBuffer.position();
      } else {
         if(this.parser_.get_complete().booleanValue()) {
            this.ProcessRtspResponse();
         }

         return bytesParsed;
      }
   }

   void sendCodecParams() {
      if(this.mode_ == Streamer.MODE.VIDEO_ONLY || this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
         this.videoFormatParams_ = this.connectionManager_.getStreamBuffer().getVideoFormatParams();
         if(null == this.videoFormatParams_) {
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE_WAIT;
            return;
         }
      }

      if(this.mode_ == Streamer.MODE.AUDIO_ONLY || this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
         this.audioFormatParams_ = this.connectionManager_.getStreamBuffer().getAudioFormatParams();
         if(null == this.audioFormatParams_) {
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE_WAIT;
            return;
         }
      }

      this.sendAnnounce();
      this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE;
   }

   void OnSend() {
      switch(RtspConnection.NamelessClass792357330.$SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[this.rtsp_state_.ordinal()]) {
      case 1:
         this.sendCodecParams();
         break;
      case 2:
         this.sendNewItem();
         break;
      case 3:
         while(this.avc_part_offset < this.currentItem.getData().length) {
            int part_len = this.sendVideoPart(this.currentItem, this.avc_part_offset);
            if(part_len <= 0) {
               Log.e("RtspConnection", "failed to send video part");
               return;
            }

            this.avc_part_offset += part_len;
            if(this.getSendBufferRemaining() > 0) {
               this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_VIDEO_PART;
               return;
            }
         }

         this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_NEW_ITEM;
         this.sendNewItem();
      }

   }

   void sendNewItem() {
      label76:
      while(this.getSendBufferRemaining() <= 0) {
         this.currentItem = this.connectionManager_.getStreamBuffer().getItem(this.message_index_);
         if(null == this.currentItem) {
            return;
         }

         this.message_index_ = this.currentItem.getMessageIndex() + 1L;
         switch(RtspConnection.NamelessClass792357330.$SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE[this.currentItem.getMessageType().ordinal()]) {
         case 1:
            if(this.mode_ != Streamer.MODE.AUDIO_VIDEO && this.mode_ != Streamer.MODE.VIDEO_ONLY || (this.currentItem.getFlags() & 1) == 0 && (this.videoPacketsSent_ == 0L || this.currentItem.getFrameId() - this.lastVideoFrameId > 1L)) {
               continue;
            }

            this.updateVideoPacketsLost(this.currentItem);
            byte[] outData = this.currentItem.getData();
            if((outData[0] >> 7 & 1) != 0) {
               Log.e("RtspConnection", "skip frame if fzb is set");
            } else {
               if(!this.appendVideoReport(this.currentItem)) {
                  Log.e("RtspConnection", "failed to send video report");
                  return;
               }

               if(outData.length + 16 < 20000) {
                  if(!this.sendVideoFrame(this.currentItem)) {
                     Log.e("RtspConnection", "failed to send video frame");
                     return;
                  }

                  this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_NEW_ITEM;
               } else {
                  this.avc_part_offset = 1;

                  do {
                     if(this.avc_part_offset >= this.currentItem.getData().length) {
                        this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_NEW_ITEM;
                        continue label76;
                     }

                     int part_len = this.sendVideoPart(this.currentItem, this.avc_part_offset);
                     if(part_len <= 0) {
                        Log.e("RtspConnection", "failed to send video part");
                        return;
                     }

                     this.avc_part_offset += part_len;
                  } while(this.getSendBufferRemaining() <= 0);

                  this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_VIDEO_PART;
                  return;
               }
            }
            break;
         case 2:
            if(this.mode_ != Streamer.MODE.AUDIO_VIDEO && this.mode_ != Streamer.MODE.AUDIO_ONLY) {
               break;
            }

            if(!this.appendAudioReport(this.currentItem)) {
               Log.e("RtspConnection", "failed to send audio report");
               return;
            }

            if(!this.sendAudioFrame(this.currentItem)) {
               Log.e("RtspConnection", "failed to send audio frame");
               return;
            }

            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_NEW_ITEM;
            break;
         default:
            Log.e("RtspConnection", "unsupported frame type " + this.currentItem.getMessageType());
         }
      }

   }

   private boolean appendVideoReport(BufferItem item) {
      if(this.avc_ntp_ts_ == -1L && this.aac_ntp_ts_ == -1L) {
         this.avc_ntp_ts_ = this.getNtpTime();
         this.avc_rtp_ts_ = this.getRtpTimestamp(item, 90000);
         if(!this.appendReport(1, this.avc_ssrc_, this.avc_ntp_ts_, this.avc_rtp_ts_)) {
            Log.e("RtspConnection", "failed to send report");
            return false;
         }

         if(this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
            this.aac_rtp_ts_ = this.avc_rtp_ts_ * (long)this.audioFormatParams_.sampleRate / 90000L;
            if(!this.appendReport(3, this.aac_ssrc_, this.avc_ntp_ts_, this.aac_rtp_ts_)) {
               Log.e("RtspConnection", "failed to send report");
               return false;
            }
         }
      } else {
         long avc_ts;
         long rtp_diff;
         if(this.avc_ntp_ts_ == -1L) {
            avc_ts = this.getRtpTimestamp(item, 90000);
            rtp_diff = avc_ts - this.avc_rtp_ts_;
            this.avc_ntp_ts_ = this.aac_ntp_ts_ + rtp_diff * 4294967296L / 90000L;
            this.avc_rtp_ts_ = avc_ts;
            if(!this.appendReport(1, this.avc_ssrc_, this.avc_ntp_ts_, this.avc_rtp_ts_)) {
               Log.e("RtspConnection", "failed to send report");
               return false;
            }
         } else if((item.getFlags() & 1) != 0) {
            avc_ts = this.getRtpTimestamp(item, 90000);
            rtp_diff = avc_ts - this.avc_rtp_ts_;
            if(rtp_diff > 450000L) {
               this.avc_ntp_ts_ += rtp_diff * 4294967296L / 90000L;
               this.avc_rtp_ts_ = avc_ts;
               if(!this.appendReport(1, this.avc_ssrc_, this.avc_ntp_ts_, this.avc_rtp_ts_)) {
                  Log.e("RtspConnection", "failed to send report");
                  return false;
               }
            }
         }
      }

      return true;
   }

   private boolean appendAudioReport(BufferItem item) {
      if(this.aac_ntp_ts_ == -1L && this.avc_ntp_ts_ == -1L) {
         this.aac_ntp_ts_ = this.getNtpTime();
         this.aac_rtp_ts_ = this.getRtpTimestamp(item, this.audioFormatParams_.sampleRate);
         if(!this.appendReport(3, this.aac_ssrc_, this.aac_ntp_ts_, this.aac_rtp_ts_)) {
            Log.e("RtspConnection", "failed to send report");
            return false;
         }

         if(this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
            this.avc_rtp_ts_ = this.aac_rtp_ts_ * 90000L / (long)this.audioFormatParams_.sampleRate;
            if(!this.appendReport(1, this.avc_ssrc_, this.aac_ntp_ts_, this.avc_rtp_ts_)) {
               Log.e("RtspConnection", "failed to send report");
               return false;
            }
         }
      } else {
         long aac_ts;
         long rtp_diff;
         if(this.aac_ntp_ts_ == -1L) {
            aac_ts = this.getRtpTimestamp(item, this.audioFormatParams_.sampleRate);
            rtp_diff = aac_ts - this.aac_rtp_ts_;
            this.aac_ntp_ts_ = this.avc_ntp_ts_ + rtp_diff * (long)this.audioFormatParams_.sampleRate;
            this.aac_rtp_ts_ = aac_ts;
            if(!this.appendReport(3, this.aac_ssrc_, this.aac_ntp_ts_, this.aac_rtp_ts_)) {
               Log.e("RtspConnection", "failed to send report");
               return false;
            }
         } else {
            aac_ts = this.getRtpTimestamp(item, this.audioFormatParams_.sampleRate);
            rtp_diff = aac_ts - this.aac_rtp_ts_;
            if(rtp_diff > (long)(5 * this.audioFormatParams_.sampleRate)) {
               this.aac_ntp_ts_ += rtp_diff * 4294967296L / (long)this.audioFormatParams_.sampleRate;
               this.aac_rtp_ts_ += rtp_diff;
               if(!this.appendReport(3, this.aac_ssrc_, this.aac_ntp_ts_, this.aac_rtp_ts_)) {
                  Log.e("RtspConnection", "failed to send report");
                  return false;
               }
            }
         }
      }

      return true;
   }

   private boolean sendVideoFrame(BufferItem item) {
      RtspBuilder builder = RtspBuilder.newBuilder(this.rtspBuffer_);
      builder.setInterleavedHeader(0, 12 + this.currentItem.getData().length);
      builder.setVersion((byte)2);
      builder.setPayloadType((byte)96);
      builder.setSeqNum(this.seqNum++);
      builder.setMarker((byte)1);
      long ts = this.getRtpTimestamp(item, 90000);
      builder.setTimestamp((int)ts);
      builder.setSsrc(this.avc_ssrc_);

      try {
         this.Append(this.rtspBuffer_, 0, 16);
         this.Send(this.currentItem.getData());
         ++this.videoPacketsSent_;
         return true;
      } catch (Exception var6) {
         Log.e("RtspConnection", Log.getStackTraceString(var6));
         return false;
      }
   }

   private int sendVideoPart(BufferItem item, int offset) {
      int nal_unit_len = this.currentItem.getData().length;
      int part_len = nal_unit_len - offset;
      if(part_len > 20000) {
         part_len = 20000;
      }

      RtspBuilder builder = RtspBuilder.newBuilder(this.rtspBuffer_);
      builder.setInterleavedHeader(0, 14 + part_len);
      builder.setVersion((byte)2);
      builder.setPayloadType((byte)96);
      builder.setSeqNum(this.seqNum++);
      builder.setMarker((byte)1);
      long ts = this.getRtpTimestamp(item, 90000);
      builder.setTimestamp((int)ts);
      builder.setSsrc(this.avc_ssrc_);
      byte[] outData = item.getData();
      byte nal_ref_idc = (byte)(outData[0] >> 5 & 3);
      byte nal_unit_type = (byte)(outData[0] & 31);
      builder.setNALHeader(nal_ref_idc);
      if(offset == 1) {
         builder.setFUAHeader(nal_unit_type, true, false);
      } else if(offset + part_len >= nal_unit_len) {
         builder.setFUAHeader(nal_unit_type, false, true);
      } else {
         builder.setFUAHeader(nal_unit_type, false, false);
      }

      try {
         this.Append(this.rtspBuffer_, 0, 18);
         this.Send(outData, offset, part_len);
         ++this.videoPacketsSent_;
         return part_len;
      } catch (Exception var12) {
         Log.e("RtspConnection", Log.getStackTraceString(var12));
         return -1;
      }
   }

   private long getNtpTime() {
      long EPOCH = 2208988800L;
      long ts1970 = System.currentTimeMillis();
      long ntpTime = (ts1970 / 1000L + 2208988800L << 32) + ts1970 % 1000L * 4294967296L / 1000L;
      return ntpTime;
   }

   private boolean appendReport(int channel, int ssrc, long ntp_ts, long rtp_ts) {
      RtspBuilder builder = RtspBuilder.newBuilder(this.rtspBuffer_);
      builder.setInterleavedHeader(channel, 28);
      builder.setVersion((byte)2);
      builder.setRtcpPayloadType((byte)-56);
      builder.setReportLength(28);
      builder.setSenderSSRC(ssrc);
      builder.setNtpTimestamp(ntp_ts);
      builder.setRtpTimestamp(rtp_ts);

      try {
         this.Append(this.rtspBuffer_, 0, 32);
         return true;
      } catch (Exception var9) {
         Log.e("RtspConnection", Log.getStackTraceString(var9));
         return false;
      }
   }

   long getRtpTimestamp(BufferItem bufferItem, int clockRate) {
      return (long)clockRate * bufferItem.getTimestamp() / 1000000L;
   }

   boolean sendAudioFrame(BufferItem item) {
      this.updateAudioPacketsLost(item);
      int data_len = item.getData().length;
      if(data_len <= 2) {
         return false;
      } else {
         RtspBuilder builder = RtspBuilder.newBuilder(this.rtspBuffer_);
         builder.setInterleavedHeader(2, 16 + data_len);
         builder.setVersion((byte)2);
         builder.setPayloadType((byte)97);
         builder.setSeqNum(this.audioSeqNum++);
         builder.setMarker((byte)1);
         long ts = this.getRtpTimestamp(item, this.audioFormatParams_.sampleRate);
         builder.setTimestamp((int)ts);
         builder.setSsrc(this.aac_ssrc_);
         builder.setAACHeader((short)data_len);

         try {
            this.Append(this.rtspBuffer_, 0, 20);
            this.Send(item.getData());
            ++this.audioPacketsSent_;
            return true;
         } catch (Exception var7) {
            Log.e("RtspConnection", Log.getStackTraceString(var7));
            return false;
         }
      }
   }

   private void ProcessRtspResponse() {
      String digestRealm = this.parser_.get_header("WWW-Authenticate-Digest-realm");
      String digestNonce = this.parser_.get_header("WWW-Authenticate-Digest-nonce");
      if(digestRealm != null && digestNonce != null) {
         this.digestRealm_ = digestRealm;
         this.digestNonce_ = digestNonce;
      }

      String basicRealm = this.parser_.get_header("WWW-Authenticate-Basic-realm");
      if(basicRealm != null) {
         this.basicRealm_ = basicRealm;
      }

      switch(RtspConnection.NamelessClass792357330.$SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[this.rtsp_state_.ordinal()]) {
      case 2:
      case 3:
         if(200 != this.parser_.get_status_code()) {
            if(401 == this.parser_.get_status_code()) {
               this.rtsp_status_ = Streamer.STATUS.AUTH_FAIL;
            }

            this.Close();
            return;
         }
         break;
      case 4:
         if(401 == this.parser_.get_status_code()) {
            this.sendOptions();
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.OPTIONS_AUTH;
            break;
         }
      case 5:
         if(200 != this.parser_.get_status_code()) {
            if(401 == this.parser_.get_status_code()) {
               this.rtsp_status_ = Streamer.STATUS.AUTH_FAIL;
            }

            this.Close();
            return;
         }

         this.notifyOnStateChange(Streamer.CONNECTION_STATE.SETUP, Streamer.STATUS.SUCCESS);
         this.sendCodecParams();
         break;
      case 6:
         if(401 == this.parser_.get_status_code()) {
            this.sendAnnounce();
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE_AUTH;
            break;
         }
      case 7:
         if(200 != this.parser_.get_status_code()) {
            if(401 == this.parser_.get_status_code()) {
               this.rtsp_status_ = Streamer.STATUS.AUTH_FAIL;
            }

            this.Close();
            return;
         }

         if(this.mode_ == Streamer.MODE.AUDIO_ONLY) {
            this.sendSetupAudio();
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SETUP_AUDIO;
         } else {
            this.sendVideoSetup();
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SETUP_VIDEO;
         }
         break;
      case 8:
         if(200 != this.parser_.get_status_code()) {
            if(401 == this.parser_.get_status_code()) {
               this.rtsp_status_ = Streamer.STATUS.AUTH_FAIL;
            }

            this.Close();
            return;
         }

         if(this.mode_ == Streamer.MODE.VIDEO_ONLY) {
            this.sendRecord();
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.RECORD;
         } else {
            this.sendSetupAudio();
            this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SETUP_AUDIO;
         }
         break;
      case 9:
         if(200 != this.parser_.get_status_code()) {
            if(401 == this.parser_.get_status_code()) {
               this.rtsp_status_ = Streamer.STATUS.AUTH_FAIL;
            }

            this.Close();
            return;
         }

         this.sendRecord();
         this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.RECORD;
         break;
      case 10:
         if(200 != this.parser_.get_status_code()) {
            if(401 == this.parser_.get_status_code()) {
               this.rtsp_status_ = Streamer.STATUS.AUTH_FAIL;
            }

            this.Close();
            return;
         }

         this.notifyOnStateChange(Streamer.CONNECTION_STATE.RECORD, Streamer.STATUS.SUCCESS);
         this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.SEND_NEW_ITEM;
         break;
      default:
         this.Close();
         return;
      }

   }

   private String getSession() {
      String res = "";
      String session = this.parser_.get_header("Session");
      if(session != null && !session.isEmpty()) {
         res = res + "Session: " + session + "\r\n";
      }

      return res;
   }

   private String getAuthorization(String method) {
      String e;
      String auth;
      if(this.digestRealm_ != null && this.digestNonce_ != null) {
         try {
            e = Utils.hexmd5(Utils.hexmd5(this.user_ + ":" + this.digestRealm_ + ":" + this.pass_) + ":" + this.digestNonce_ + ":" + Utils.hexmd5(method + ":" + this.uri_));
            auth = "Authorization: Digest ";
            auth = auth + "username=\"" + this.user_ + "\"";
            auth = auth + ",realm=\"" + this.digestRealm_ + "\"";
            auth = auth + ",nonce=\"" + this.digestNonce_ + "\"";
            auth = auth + ",uri=\"" + this.uri_ + "\"";
            auth = auth + ",response=\"" + e + "\"";
            auth = auth + "\r\n";
            return auth;
         } catch (Exception var5) {
            Log.e("RtspConnection", Log.getStackTraceString(var5));
         }
      } else if(this.basicRealm_ != null) {
         try {
            e = this.user_ + ":" + this.pass_;
            auth = "Authorization: Basic " + Base64.encodeToString(e.getBytes("US-ASCII"), 2) + "\r\n";
            return auth;
         } catch (UnsupportedEncodingException var4) {
            Log.e("RtspConnection", Log.getStackTraceString(var4));
         }
      }

      return "";
   }

   private void sendOptions() {
      String optionsRequest = "OPTIONS " + this.uri_ + " RTSP/1.0\r\n";
      optionsRequest = optionsRequest + "CSeq: " + this.cseq_++ + "\r\n";
      optionsRequest = optionsRequest + "User-Agent: " + this.connectionManager_.getUserAgent() + "\r\n";
      optionsRequest = optionsRequest + this.getAuthorization("OPTIONS");
      optionsRequest = optionsRequest + "\r\n";

      try {
         this.Send(optionsRequest);
         this.rtsp_state_ = RtspConnection.RTSP_CONNECTION_STATE.OPTIONS;
      } catch (IOException var3) {
         Log.e("RtspConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   private void sendAnnounce() {
      String sdp = "v=0\r\n";
      String annonceRequest;
      if(this.mode_ == Streamer.MODE.VIDEO_ONLY || this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
         if(null == this.videoFormatParams_) {
            this.Close();
            return;
         }

         annonceRequest = Utils.byteArrayToHex(this.videoFormatParams_.sps_buf, 1, 3);
         String e = Base64.encodeToString(this.videoFormatParams_.sps_buf, 0, this.videoFormatParams_.sps_len, 2);
         String pps_str = Base64.encodeToString(this.videoFormatParams_.pps_buf, 0, this.videoFormatParams_.pps_len, 2);
         sdp = sdp + "m=video 0 RTP/AVP/TCP 96\r\n";
         sdp = sdp + "a=rtpmap:96 H264/90000\r\n";
         sdp = sdp + "a=fmtp:96 packetization-mode=1; sprop-parameter-sets=" + e + "," + pps_str + "; profile-level-id=" + annonceRequest + "\r\n";
         sdp = sdp + "a=control:streamid=0\r\n";
      }

      if(this.mode_ == Streamer.MODE.AUDIO_ONLY || this.mode_ == Streamer.MODE.AUDIO_VIDEO) {
         if(null == this.audioFormatParams_) {
            this.Close();
            return;
         }

         annonceRequest = Utils.byteArrayToHex(this.audioFormatParams_.config_buf, 0, this.audioFormatParams_.config_len);
         int[] e1;
         if(this.audioFormatParams_.sampleRate == -1) {
            e1 = new int[]{96000, 88200, '\ufa00', '\ubb80', '\uac44', 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350, -1, -1, -1};
            if(this.audioFormatParams_.config_len < 1) {
               Log.e("RtspConnection", "failed to parse audio format params");
               this.Close();
               return;
            }

            this.audioFormatParams_.sampleRate = e1[(this.audioFormatParams_.config_buf[0] & 7) << 1 | this.audioFormatParams_.config_buf[1] >> 7 & 1];
            if(this.audioFormatParams_.sampleRate == -1) {
               Log.e("RtspConnection", "failed to get sample rate, config=" + annonceRequest);
               this.Close();
               return;
            }
         }

         if(this.audioFormatParams_.channelCount == -1) {
            e1 = new int[]{-1, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, -1, -1, -1, -1, -1};
            this.audioFormatParams_.channelCount = e1[this.audioFormatParams_.config_buf[1] >> 3 & 15];
            if(this.audioFormatParams_.channelCount == -1) {
               Log.e("RtspConnection", "failed to get channel count, config=" + annonceRequest);
               this.Close();
               return;
            }
         }

         sdp = sdp + "m=audio 0 RTP/AVP/TCP 97\r\n";
         sdp = sdp + "a=rtpmap:97 mpeg4-generic/" + this.audioFormatParams_.sampleRate + "/" + this.audioFormatParams_.channelCount + "\r\n";
         sdp = sdp + "a=fmtp:97 profile-level-id=1;mode=AAC-hbr;sizelength=13;indexlength=3;indexdeltalength=3;config=" + annonceRequest + "\r\n";
         sdp = sdp + "a=control:streamid=1\r\n";
      }

      annonceRequest = "ANNOUNCE " + this.uri_ + " RTSP/1.0\r\n" + "Content-Type: application/sdp\r\n" + "CSeq: " + this.cseq_++ + "\r\n" + "User-Agent: " + this.connectionManager_.getUserAgent() + "\r\n";
      annonceRequest = annonceRequest + "Content-Length: " + sdp.length() + "\r\n";
      annonceRequest = annonceRequest + this.getSession();
      annonceRequest = annonceRequest + this.getAuthorization("ANNOUNCE");
      annonceRequest = annonceRequest + "\r\n";
      annonceRequest = annonceRequest + sdp;

      try {
         this.Send(annonceRequest);
      } catch (IOException var5) {
         Log.e("RtspConnection", Log.getStackTraceString(var5));
         this.Close();
      }

   }

   private void sendVideoSetup() {
      String videoSetupRequest = "SETUP " + this.uri_ + "/streamid=0" + " RTSP/1.0\r\n";
      videoSetupRequest = videoSetupRequest + "Transport: RTP/AVP/TCP;unicast;interleaved=0-1;mode=record\r\n";
      videoSetupRequest = videoSetupRequest + "CSeq: " + this.cseq_++ + "\r\n";
      videoSetupRequest = videoSetupRequest + "User-Agent: " + this.connectionManager_.getUserAgent() + "\r\n";
      videoSetupRequest = videoSetupRequest + this.getSession();
      videoSetupRequest = videoSetupRequest + this.getAuthorization("SETUP");
      videoSetupRequest = videoSetupRequest + "\r\n";

      try {
         this.Send(videoSetupRequest);
      } catch (IOException var3) {
         Log.e("RtspConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   private void sendSetupAudio() {
      String audioSetupRequest = "SETUP " + this.uri_ + "/streamid=1" + " RTSP/1.0\r\n";
      audioSetupRequest = audioSetupRequest + "Transport: RTP/AVP/TCP;unicast;interleaved=2-3;mode=record\r\n";
      audioSetupRequest = audioSetupRequest + "CSeq: " + this.cseq_++ + "\r\n";
      audioSetupRequest = audioSetupRequest + "User-Agent: " + this.connectionManager_.getUserAgent() + "\r\n";
      audioSetupRequest = audioSetupRequest + this.getSession();
      audioSetupRequest = audioSetupRequest + this.getAuthorization("SETUP");
      audioSetupRequest = audioSetupRequest + "\r\n";

      try {
         this.Send(audioSetupRequest);
      } catch (IOException var3) {
         Log.e("RtspConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   private void sendRecord() {
      String record = "RECORD " + this.uri_ + " RTSP/1.0\r\n";
      record = record + "Range: npt=0.000-\r\n";
      record = record + "CSeq: " + this.cseq_++ + "\r\n";
      record = record + "User-Agent: " + this.connectionManager_.getUserAgent() + "\r\n";
      record = record + this.getSession();
      record = record + this.getAuthorization("RECORD");
      record = record + "\r\n";

      try {
         this.Send(record);
      } catch (IOException var3) {
         Log.e("RtspConnection", Log.getStackTraceString(var3));
         this.Close();
      }

   }

   // $FF: synthetic class
   static class NamelessClass792357330 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE;
      // $FF: synthetic field
      static final int[] $SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE = new int[BufferItem.FRAME_TYPE.values().length];


      static {
         try {
            $SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE[BufferItem.FRAME_TYPE.VIDEO.ordinal()] = 1;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$BufferItem$FRAME_TYPE[BufferItem.FRAME_TYPE.AUDIO.ordinal()] = 2;
         } catch (NoSuchFieldError var11) {
            ;
         }

         $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE = new int[RtspConnection.RTSP_CONNECTION_STATE.values().length];

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE_WAIT.ordinal()] = 1;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.SEND_NEW_ITEM.ordinal()] = 2;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.SEND_VIDEO_PART.ordinal()] = 3;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.OPTIONS.ordinal()] = 4;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.OPTIONS_AUTH.ordinal()] = 5;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.ANNOUNCE_AUTH.ordinal()] = 7;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.SETUP_VIDEO.ordinal()] = 8;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.SETUP_AUDIO.ordinal()] = 9;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            $SwitchMap$com$wmspanel$libstream$RtspConnection$RTSP_CONNECTION_STATE[RtspConnection.RTSP_CONNECTION_STATE.RECORD.ordinal()] = 10;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }

   static enum RTSP_CONNECTION_STATE {

      INITIAL("INITIAL", 0),
      OPTIONS("OPTIONS", 1),
      OPTIONS_AUTH("OPTIONS_AUTH", 2),
      ANNOUNCE_WAIT("ANNOUNCE_WAIT", 3),
      ANNOUNCE("ANNOUNCE", 4),
      ANNOUNCE_AUTH("ANNOUNCE_AUTH", 5),
      SETUP_VIDEO("SETUP_VIDEO", 6),
      SETUP_AUDIO("SETUP_AUDIO", 7),
      RECORD("RECORD", 8),
      SEND_NEW_ITEM("SEND_NEW_ITEM", 9),
      SEND_VIDEO_PART("SEND_VIDEO_PART", 10),
      CLOSED("CLOSED", 11);
      // $FF: synthetic field
      private static final RtspConnection.RTSP_CONNECTION_STATE[] $VALUES = new RtspConnection.RTSP_CONNECTION_STATE[]{INITIAL, OPTIONS, OPTIONS_AUTH, ANNOUNCE_WAIT, ANNOUNCE, ANNOUNCE_AUTH, SETUP_VIDEO, SETUP_AUDIO, RECORD, SEND_NEW_ITEM, SEND_VIDEO_PART, CLOSED};


      private RTSP_CONNECTION_STATE(String var1, int var2) {}

   }
}
