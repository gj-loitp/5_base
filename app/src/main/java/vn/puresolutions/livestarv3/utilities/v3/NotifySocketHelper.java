package vn.puresolutions.livestarv3.utilities.v3;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import vn.puresolutions.livestar.custom.socket.Listener;
import vn.puresolutions.livestarv3.app.LSApplication;

/**
 * File created on 9/7/2017.
 *
 * @author Muammil
 */

public class NotifySocketHelper {
    private Socket socket;
    private static NotifySocketHelper instance;

    public static NotifySocketHelper getInstance() {
        NotifySocketHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (RoomSocketHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new NotifySocketHelper();
                }
            }
        }
        return localInstance;
    }

    public NotifySocketHelper() {
        instance = this;
    }

    private Listener<Object> recivedNotifyListener = new Listener<Object>(Object.class) {
        @Override
        public void call(Object message) {
            LLog.d("NotifySocketHelper", "onNotification: " + LSApplication.getInstance().getGson().toJson(message));
            /*if (callback != null) {
                callback.onNewComment(message);
            }*/
        }
    };

    public void open(String token, Emitter.Listener listener) {
        close();

        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = true;
            options.query = String.format("Authorization=%s",
                    token);
            socket = IO.socket("http://dev.livestar.vn:8050/", options);
            //socket = IO.socket("http://192.168.0.113:3000/", options);

            // clear all events
            socket.off();
            socket.on("notification", recivedNotifyListener);
            // register events here
            //socket.on("isConnected", listener);
            //socket.on("message", onMessageListener);
            /*socket.on("newComment", onNewCommentListener);
            socket.on("connectUser", onConnectUserListener);
            socket.on("sendGift", onSendGiftListener);
            socket.on("sendHeart", onSendHeartListener);
            socket.on("disconnectUser", onDisconnectListener);*/
            //socket.on("newComment", onListUserListener);
         /*   socket.on("memberlist", onMemberListListener);
            socket.on("newmember", onNewMemberListener);
            socket.on("memberleave", onMemberLeaveListener);
            socket.on("action recived", onActionListener);
            socket.on("action done", onActionDoneListener);
            socket.on("action full", onActionFullListener);
            socket.on("gifts recived", onReceivedGift);
            socket.on("hearts recived", onReceivedHeart);*/
            socket.connect();

        } catch (URISyntaxException e) {
            LLog.e(getClass().getSimpleName(), "" + e.getMessage());
        } catch (Exception e) {
            LLog.e(getClass().getSimpleName(), "" + e.getMessage());
        }
    }
    public void close() {
        if (socket != null) {
            socket.off();
            socket.disconnect();
        }
    }
}
