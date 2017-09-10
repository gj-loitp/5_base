package com.artfulbits.libstream;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map.Entry;

class ConnectionManager {

   private static final String TAG = "ConnectionManager";
   StreamBuffer streamBuffer_;
   HashMap connectionMap;
   Selector selector;
   private Thread connectionThread;
   private long lastWaitingConnectsCheckTime = -1L;
   private long lastWaitingSendersCheckTime = -1L;
   private long last_ts_ = 0L;
   public int connectionId_ = 0;
   Queue connectionQueue_;
   private String userAgent_ = "SoftvelumLarixBroadcaster/1.0.12";


   ConnectionManager(StreamBuffer streamBuffer) {
      this.streamBuffer_ = streamBuffer;
      this.connectionMap = new HashMap();
      this.connectionQueue_ = new LinkedList();

      try {
         this.selector = Selector.open();
      } catch (IOException var3) {
         Log.e("ConnectionManager", Log.getStackTraceString(var3));
      }

   }

   void startConnectionThread() throws InterruptedException {
      if(null == this.connectionThread) {
         this.connectionThread = new Thread() {
            public void run() {
               Log.d("ConnectionManager", "connectionThread started");
               ConnectionManager.this.lastWaitingConnectsCheckTime = ConnectionManager.this.lastWaitingConnectsCheckTime = SystemClock.uptimeMillis();

               try {
                  while(!this.isInterrupted()) {
                     int e = ConnectionManager.this.selector.select(250L);
                     Iterator i$ = ConnectionManager.this.selector.selectedKeys().iterator();

                     while(i$.hasNext()) {
                        SelectionKey selectionKey = (SelectionKey)i$.next();
                        if(selectionKey.isValid()) {
                           int readyOps = selectionKey.readyOps();
                           BaseConnection connection = (BaseConnection)selectionKey.attachment();
                           if(null == connection) {
                              Log.e("ConnectionManager", "connection is null");
                              break;
                           }

                           connection.ProcessEvent(selectionKey);
                        }
                     }

                     ConnectionManager.this.selector.selectedKeys().clear();
                     ConnectionManager.this.ProcessWaitingConnects();
                     ConnectionManager.this.ProcessWaitingSenders();
                     ConnectionManager.this.ProcessTimeout();
                  }
               } catch (IOException var6) {
                  Log.e("ConnectionManager", "connectionThread failed");
                  Log.e("ConnectionManager", Log.getStackTraceString(var6));
               }

               Log.d("ConnectionManager", "connectionThread stopped");
            }
         };
         this.connectionThread.start();
      }
   }

   void ProcessWaitingConnects() throws IOException {
      long timeNow = SystemClock.uptimeMillis();
      if(timeNow - this.lastWaitingConnectsCheckTime >= 500L) {
         this.lastWaitingConnectsCheckTime = timeNow;

         while(true) {
            BaseConnection connection = (BaseConnection)this.connectionQueue_.poll();
            if(null == connection) {
               return;
            }

            connection.Connect();
         }
      }
   }

   void ProcessWaitingSenders() {
      long timeNow = SystemClock.uptimeMillis();
      if(timeNow - this.lastWaitingSendersCheckTime >= 200L) {
         this.lastWaitingSendersCheckTime = timeNow;
         Iterator i$ = this.selector.keys().iterator();

         while(i$.hasNext()) {
            SelectionKey key = (SelectionKey)i$.next();
            if(key.isValid()) {
               BaseConnection connection = (BaseConnection)key.attachment();
               if(null == connection) {
                  Log.e("ConnectionManager", "null connection");
               } else if(0 == connection.getSendBufferRemaining()) {
                  connection.OnSend();
               }
            }
         }

      }
   }

   private void ProcessTimeout() {
      if(this.last_ts_ == 0L) {
         this.last_ts_ = System.currentTimeMillis();
      } else {
         long cur_ts = System.currentTimeMillis();
         if(cur_ts - this.last_ts_ > 2000L) {
            Iterator i$ = this.selector.keys().iterator();

            while(i$.hasNext()) {
               SelectionKey key = (SelectionKey)i$.next();
               BaseConnection connnection = (BaseConnection)key.attachment();
               if(null == connnection) {
                  Log.e("ConnectionManager", "null connection");
               } else {
                  connnection.VerifyInactivity();
               }
            }

            this.last_ts_ = cur_ts;
         }

      }
   }

   void stopConnectionThread() {
      if(null != this.connectionThread) {
         try {
            this.connectionThread.interrupt();
            this.connectionThread.join();
         } catch (InterruptedException var2) {
            Log.e("ConnectionManager", "failed to stop connectionThread loop");
            Log.e("ConnectionManager", Log.getStackTraceString(var2));
         }

         this.connectionThread = null;
      }
   }

   Selector getSelector() {
      return this.selector;
   }

   StreamBuffer getStreamBuffer() {
      return this.streamBuffer_;
   }

   int createRtspConnection(String originalUri, Streamer.MODE mode, Streamer.Listener listener) {
      try {
         String e = originalUri;
         Uri uri = Uri.parse(originalUri);
         String scheme = uri.getScheme();
         if(null == scheme) {
            Log.e("ConnectionManager", "failed to parse scheme, uri=" + uri);
            return -1;
         }

         String user = null;
         String pass = null;
         String userInfo = uri.getUserInfo();
         int port;
         if(userInfo != null) {
            String[] host = userInfo.split(":");
            if(host.length == 2) {
               user = host[0];
               pass = host[1];
            }

            port = originalUri.indexOf("@");
            if(port != -1) {
               e = scheme + "://" + originalUri.substring(port + 1);
            }
         }

         String var17 = uri.getHost();
         if(null == var17) {
            Log.e("ConnectionManager", "failed to parse host, uri=" + uri);
            return -1;
         }

         port = uri.getPort();
         Object connection = null;
         if(scheme.equalsIgnoreCase("rtsp")) {
            if(-1 == port) {
               port = 554;
            }

            connection = new RtspConnection(this, ++this.connectionId_, mode, e, var17, port, user, pass, listener);
         } else {
            if(!scheme.equalsIgnoreCase("rtmp")) {
               Log.e("ConnectionManager", "unsupported scheme=" + scheme);
               return -1;
            }

            if(-1 == port) {
               port = 1935;
            }

            String[] splittedPath = originalUri.split("/");
            if(splittedPath.length < 5) {
               Log.e("ConnectionManager", "failed to get rtmp app & stream, uri=" + uri);
               return -1;
            }

            String app = splittedPath[3];

            for(int stream = 4; stream < splittedPath.length - 1; ++stream) {
               app = app + "/" + splittedPath[stream];
            }

            String var18 = splittedPath[splittedPath.length - 1];
            connection = new RtmpConnection(this, ++this.connectionId_, mode, e, var17, port, app, var18, listener);
         }

         if(null == connection) {
            Log.e("ConnectionManager", "failed to create connection, uri=" + uri);
            return -1;
         }

         this.connectionMap.put(Integer.valueOf(this.connectionId_), connection);
         this.startConnectionThread();
         this.connectionQueue_.add(connection);
         this.selector.wakeup();
      } catch (Exception var16) {
         Log.e("ConnectionManager", Log.getStackTraceString(var16));
         return -1;
      }

      return this.connectionId_;
   }

   public void releaseRtspConnection(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.remove(Integer.valueOf(connectionId));
      if(null != connection) {
         connection.release();
         if(this.connectionMap.isEmpty()) {
            this.stopConnectionThread();
         }

      }
   }

   public void release() {
      Iterator i$ = this.connectionMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry cursor = (Entry)i$.next();
         BaseConnection connection = (BaseConnection)cursor.getValue();
         connection.Close();
      }

      this.connectionMap.clear();
      this.stopConnectionThread();
   }

   public long getBytesSent(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.get(Integer.valueOf(connectionId));
      return null == connection?0L:connection.getBytesSent();
   }

   public long getBytesRecv(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.get(Integer.valueOf(connectionId));
      return null == connection?0L:connection.getBytesRecv();
   }

   long getAudioPacketsSent(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.get(Integer.valueOf(connectionId));
      return null == connection?0L:connection.getAudioPacketsSent();
   }

   long getAudioPacketsLost(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.get(Integer.valueOf(connectionId));
      return null == connection?0L:connection.getAudioPacketsLost();
   }

   long getVideoPacketsSent(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.get(Integer.valueOf(connectionId));
      return null == connection?0L:connection.getVideoPacketsSent();
   }

   long getVideoPacketsLost(int connectionId) {
      BaseConnection connection = (BaseConnection)this.connectionMap.get(Integer.valueOf(connectionId));
      return null == connection?0L:connection.getVideoPacketsLost();
   }

   public void setUserAgent(String userAgent) {
      this.userAgent_ = userAgent;
   }

   public String getUserAgent() {
      return this.userAgent_;
   }
}
