package com.artfulbits.libstream;

import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

abstract class BaseConnection {

   private static final String TAG = "BaseConnection";
   private SocketChannel s_;
   protected Streamer.MODE mode_;
   protected ConnectionManager connectionManager_;
   protected int connectionId_;
   protected String host_;
   protected int port_;
   protected long bytesSent_;
   protected long bytesRecv_;
   protected Streamer.Listener listener_;
   private ByteBuffer outBuffer;
   private ByteBuffer inBuffer;
   protected int inactivity_count_ = 0;
   protected long audioPacketsSent_ = 0L;
   protected long audioPacketsLost_ = 0L;
   protected long videoPacketsSent_ = 0L;
   protected long videoPacketsLost_ = 0L;
   protected long lastAudioFrameId = 0L;
   protected long lastVideoFrameId = 0L;


   BaseConnection(ConnectionManager connectionManager, int connectionId, Streamer.MODE mode, String host, int port, Streamer.Listener listener) throws IOException {
      this.connectionManager_ = connectionManager;
      this.connectionId_ = connectionId;
      this.mode_ = mode;
      this.host_ = host;
      this.port_ = port;
      this.listener_ = listener;
      this.bytesSent_ = 0L;
      this.bytesRecv_ = 0L;
      this.inBuffer = ByteBuffer.allocate(4096);
      this.outBuffer = ByteBuffer.allocate(21504);
      this.outBuffer.position(0);
      this.outBuffer.limit(0);
      this.s_ = SocketChannel.open();
      this.s_.configureBlocking(false);
   }

   Streamer.MODE getMode() {
      return this.mode_;
   }

   void Connect() throws IOException {
      Log.d("BaseConnection", "Connect");

      try {
         this.notifyOnStateChange(Streamer.CONNECTION_STATE.INITIALIZED, Streamer.STATUS.SUCCESS);
         this.s_.register(this.connectionManager_.getSelector(), 8, this);
         InetSocketAddress e = new InetSocketAddress(this.host_, this.port_);
         this.s_.connect(e);
      } catch (Exception var2) {
         Log.e("BaseConnection", Log.getStackTraceString(var2));
         this.Close();
      }

   }

   public void Send(String request) throws IOException {
      byte[] buffer = request.getBytes("US-ASCII");
      this.Send(buffer, 0, buffer.length);
   }

   public void Send(byte[] buffer) throws IOException {
      this.Send(buffer, 0, buffer.length);
   }

   public void Send(byte[] buffer, int offset, int count) throws IOException {
      if(null == this.outBuffer) {
         this.Close();
      } else {
         this.outBuffer.compact();
         this.outBuffer.put(buffer, offset, count);
         this.outBuffer.flip();
         int bytesSent = this.s_.write(this.outBuffer);
         if(bytesSent > 0) {
            this.inactivity_count_ = 0;
            this.bytesSent_ += (long)bytesSent;
         }

         if(this.outBuffer.hasRemaining()) {
            this.setOps(5);
         }

      }
   }

   public int getSendBufferRemaining() {
      return null == this.outBuffer?0:this.outBuffer.remaining();
   }

   public void Append(byte[] buffer) throws IOException {
      this.Append(buffer, 0, buffer.length);
   }

   public void Append(byte[] buffer, int offset, int count) throws IOException {
      if(null == this.outBuffer) {
         this.Close();
      } else {
         this.outBuffer.compact();
         this.outBuffer.put(buffer, offset, count);
         this.outBuffer.flip();
      }
   }

   abstract void onConnect();

   abstract int onRecv(ByteBuffer var1);

   abstract void OnSend();

   public void VerifyInactivity() {
      ++this.inactivity_count_;
      if(this.inactivity_count_ > 5) {
         Log.w("BaseConnection", "inactivity timeout expired");
         this.Close();
      }

   }

   private void Skip(ByteBuffer buffer, int bytesToSkip) {
      if(buffer.position() <= bytesToSkip) {
         buffer.clear();
      } else {
         int newPosition = buffer.position() - bytesToSkip;
         int j = 0;

         for(int i = bytesToSkip; i < newPosition; ++i) {
            buffer.put(j++, buffer.get(i));
         }

         buffer.position(newPosition);
      }
   }

   public void ProcessEvent(SelectionKey selectionKey) {
      if(null != selectionKey) {
         try {
            if(selectionKey.isConnectable() && this.s_.finishConnect()) {
               this.inactivity_count_ = 0;
               this.setOps(1);
               this.onConnect();
            }

            if(selectionKey.isReadable()) {
               int e = this.s_.read(this.inBuffer);
               if(e <= 0) {
                  this.Close();
                  return;
               }

               this.inactivity_count_ = 0;
               this.bytesRecv_ += (long)e;
               int bytesProcessed = this.onRecv(this.inBuffer);
               this.Skip(this.inBuffer, bytesProcessed);
            }

            if(selectionKey.isWritable()) {
               this.sendBuffer();
            }
         } catch (Exception var4) {
            Log.e("BaseConnection", Log.getStackTraceString(var4));
            this.Close();
         }

      }
   }

   private void setOps(int operations) {
      if(null != this.s_) {
         SelectionKey selectionKey = this.s_.keyFor(this.connectionManager_.getSelector());
         if(null == selectionKey) {
            this.Close();
         } else {
            selectionKey.interestOps(operations);
         }
      }
   }

   private void sendBuffer() {
      try {
         int e = this.s_.write(this.outBuffer);
         if(e > 0) {
            this.inactivity_count_ = 0;
            this.bytesSent_ += (long)e;
         }

         if(!this.outBuffer.hasRemaining()) {
            this.setOps(1);
            this.OnSend();
         }
      } catch (IOException var2) {
         Log.e("BaseConnection", Log.getStackTraceString(var2));
         this.Close();
      }

   }

   protected synchronized void Close() {
      if(null != this.s_) {
         try {
            this.s_.close();
            SelectionKey e = this.s_.keyFor(this.connectionManager_.getSelector());
            if(null != e) {
               e.cancel();
            }

            this.s_ = null;
         } catch (IOException var2) {
            Log.e("BaseConnection", Log.getStackTraceString(var2));
         }

      }
   }

   public void release() {
      this.Close();
   }

   public long getBytesSent() {
      return this.bytesSent_;
   }

   public long getBytesRecv() {
      return this.bytesRecv_;
   }

   void updateAudioPacketsLost(BufferItem item) {
      long packetsLost = item.getFrameId() - this.lastAudioFrameId - 1L;
      if(this.audioPacketsSent_ != 0L && packetsLost != 0L) {
         Log.w("BaseConnection", "audio frames lost " + packetsLost);
         this.audioPacketsLost_ += packetsLost;
      }

      this.lastAudioFrameId = item.getFrameId();
   }

   void updateVideoPacketsLost(BufferItem item) {
      long packetsLost = item.getFrameId() - this.lastVideoFrameId - 1L;
      if(this.videoPacketsSent_ != 0L && packetsLost != 0L) {
         Log.w("BaseConnection", "video frames lost " + packetsLost);
         this.videoPacketsLost_ += packetsLost;
      }

      this.lastVideoFrameId = item.getFrameId();
   }

   long getAudioPacketsSent() {
      return this.audioPacketsSent_;
   }

   long getAudioPacketsLost() {
      return this.audioPacketsLost_;
   }

   long getVideoPacketsSent() {
      return this.videoPacketsSent_;
   }

   long getVideoPacketsLost() {
      return this.videoPacketsLost_;
   }

   void notifyOnStateChange(final Streamer.CONNECTION_STATE state, final Streamer.STATUS status) {
      Log.d("BaseConnection", "notifyOnStateChange: conectionId=" + this.connectionId_ + ";state=" + state + ";status=" + status);
      if(null != this.listener_) {
         Handler handler = this.listener_.getHandler();
         if(null != handler) {
            handler.post(new Runnable() {
               public void run() {
                  BaseConnection.this.listener_.OnConnectionStateChanged(BaseConnection.this.connectionId_, state, status);
               }
            });
         }
      }
   }
}
