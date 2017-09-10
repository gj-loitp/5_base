package vn.puresolutions.livestarv3.utilities.v3;

import android.content.Context;

import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import vn.puresolutions.livestar.custom.socket.Listener;
import vn.puresolutions.livestarv3.activity.livestream.model.ChatMessage;
import vn.puresolutions.livestarv3.app.LSApplication;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class RoomSocketHelper {
    private Socket socket;
    private static RoomSocketHelper instance;

    public static RoomSocketHelper getInstance() {
        RoomSocketHelper localInstance = instance;
        if (localInstance == null) {
            synchronized (RoomSocketHelper.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RoomSocketHelper();
                }
            }
        }
        return localInstance;
    }

    public RoomSocketHelper() {
        instance = this;
    }

    /*private Listener<Action> onActionListener = new Listener<Action>(Action.class) {
        @Override
        public void call(Action data) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.ReceivedAction, data);
        }
    };

    private Listener<Action> onActionDoneListener = new Listener<Action>(Action.class) {
        @Override
        public void call(Action data) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.ActionDone, data);
        }
    };

    private Listener<Action> onActionFullListener = new Listener<Action>(Action.class) {
        @Override
        public void call(Action data) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.ActionFull, data);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.ReceivedAction, data);
        }
    };

    private Listener<Void> onRoomErrorListener = new Listener<Void>(Void.class) {
        @Override
        public void call(Void data) {
            //TODO
        }
    };

    private Listener<Users> onMemberListListener = new Listener<Users>(Users.class) {
        @Override
        public void call(Users data) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserList, data);
        }
    };

    private Listener<UserSocket> onNewMemberListener = new Listener<UserSocket>(UserSocket.class) {
        @Override
        public void call(UserSocket data) {
            NotificationCenter.getInstance()
                    .postNotificationName(NotificationCenter.UserNew, data);
        }
    };

    private Listener<Long> onMemberLeaveListener = new Listener<Long>(Long.class) {
        @Override
        public void call(Long userId) {
            NotificationCenter.getInstance()
                    .postNotificationName(NotificationCenter.UserLeave, userId);
        }
    };

    private Listener<GiftMessage> onReceivedGift = new Listener<GiftMessage>(GiftMessage.class) {
        @Override
        public void call(GiftMessage data) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.ChatReceivedNewMessage, data);
        }
    };
/*    private Listener<HeartMessage> onReceivedHeart = new Listener<HeartMessage>(HeartMessage.class) {
        @Override
        public void call(HeartMessage data) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.HeartReceivedNewMessage);
            Log.i("Heart", "socket event");
        }
    };*/
    private Listener<ChatMessage> onDisconnectListener = new Listener<ChatMessage>(ChatMessage.class) {
        @Override
        public void call(ChatMessage data) {
            LLog.d("RoomSocketHelperV3", "Disconnect:+ "+LSApplication.getInstance().getGson().toJson(data));
            if (callback!=null){
                callback.onDisconnect(data);
            }
        }
    };

    private Listener<ChatMessage> onSendHeartListener = new Listener<ChatMessage>(ChatMessage.class) {
        @Override
        public void call(ChatMessage data) {
            LLog.d("RoomSocketHelperV3", "Heart:+ "+LSApplication.getInstance().getGson().toJson(data));
            if (callback!=null){
                callback.onSendHeart(data);
            }
        }
    };

    private Listener<ChatMessage> onSendGiftListener = new Listener<ChatMessage>(ChatMessage.class) {
        @Override
        public void call(ChatMessage data) {
            LLog.d("RoomSocketHelperV3", "Gift:+ "+LSApplication.getInstance().getGson().toJson(data));
            if (callback!=null){
                callback.onNewConnectUser(data);
            }
        }
    };

    private Listener<ChatMessage> onConnectUserListener = new Listener<ChatMessage>(ChatMessage.class) {
        @Override
        public void call(ChatMessage message) {
            //LLog.d("RoomSocketHelperV3","mess: "+message);
            LLog.d("RoomSocketHelperV3", "onConnectUserListener: " + LSApplication.getInstance().getGson().toJson(message));
            if (callback!=null){
                callback.onNewConnectUser(message);
            }
            //NotificationCenter.getInstance().postNotificationName(NotificationCenter.ChatReceivedNewMessage, message);
        }
    };

    private Listener<ChatMessage> onMessageListener = new Listener<ChatMessage>(ChatMessage.class) {
        @Override
        public void call(ChatMessage message) {
            LLog.d("RoomSocketHelperV3", "onMessageListener: " + LSApplication.getInstance().getGson().toJson(message));
            if (callback != null) {

            }
        }
    };
    private Listener<ChatMessage> onNewCommentListener = new Listener<ChatMessage>(ChatMessage.class) {
        @Override
        public void call(ChatMessage message) {
            LLog.d("RoomSocketHelperV3", "onNewCommentListener: " + LSApplication.getInstance().getGson().toJson(message));
            if (callback != null) {
                callback.onNewComment(message);
            }
        }
    };

    public void sendChat(Context mContext, String text) {
        socket.emit("comment", text);
        //socket.emit("comment","anhanh",text);
        //Log.d("RoomSocketHelperV3","comment: ");
    }

    public void endRoom(Context mContext) {
        socket.emit("endRoom", "end");
        //socket.emit("comment","anhanh",text);
        //Log.d("RoomSocketHelperV3","comment: ");
    }

    public void open(String roomID, String token, Emitter.Listener listener) {
        close();

        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = true;
            options.query = String.format("Authorization=%s&roomId=%s",
                    token, roomID);
            socket = IO.socket("http://dev.livestar.vn:8050/", options);
            //socket = IO.socket("http://192.168.0.113:3000/", options);

            // clear all events
            socket.off();

            // register events here
            socket.on("isConnected", listener);
            //socket.on("message", onMessageListener);
            socket.on("newComment", onNewCommentListener);
            socket.on("connectUser", onConnectUserListener);
            socket.on("sendGift", onSendGiftListener);
            socket.on("sendHeart", onSendHeartListener);
            socket.on("disconnectUser", onDisconnectListener);
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

    public interface Callback {
        public void onNewComment(ChatMessage message);
        public void onNewConnectUser(ChatMessage message);
        public void onDisconnect(ChatMessage message);
        public void onSendHeart(ChatMessage message);
        public void onSendGift(ChatMessage message);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}