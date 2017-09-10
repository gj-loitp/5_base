package vn.puresolutions.livestar.room;

import android.content.Context;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Action;
import vn.puresolutions.livestar.core.api.model.GiftMessage;
import vn.puresolutions.livestar.core.api.model.HeartMessage;
import vn.puresolutions.livestar.core.api.model.Lounge;
import vn.puresolutions.livestar.core.api.model.Message;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.UserSocket;
import vn.puresolutions.livestar.core.api.model.Users;
import vn.puresolutions.livestar.custom.socket.Listener;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class RoomSocketHelper {
    private Socket socket;
    private Context context;
    private RoomCenter roomCenter;

    private Listener<Message> onMessageListener = new Listener<Message>(Message.class) {
        @Override
        public void call(Message data) {
            roomCenter.onReceiveNewMessage(data);
        }
    };

    private Listener<Action> onActionListener = new Listener<Action>(Action.class) {
        @Override
        public void call(Action data) {
            roomCenter.onActionChanged(data);
        }
    };

    private Listener<Lounge> onLoungeListener = new Listener<Lounge>(Lounge.class) {
        @Override
        public void call(Lounge data) {
            roomCenter.onLoungeChanged(data);
        }
    };

    private Listener<Void> onRoomConnectedListener = new Listener<Void>(Void.class) {
        @Override
        public void call(Void data) {
            roomCenter.onSocketConnected(true);
        }
    };

    private Listener<Void> onDuplicatedListener = new Listener<Void>(Void.class) {
        @Override
        public void call(Void data) {
            roomCenter.onDuplicated();
        }
    };

    private Listener<Void> onRoomErrorListener = new Listener<Void>(Void.class) {
        @Override
        public void call(Void data) {
            roomCenter.onSocketConnected(false);
        }
    };

    private Listener<Users> onMemberListListener = new Listener<Users>(Users.class) {
        @Override
        public void call(Users data) {
            roomCenter.onReceiveListUser(data);
        }
    };

    private Listener<UserSocket> onNewMemberListener = new Listener<UserSocket>(UserSocket.class) {
        @Override
        public void call(UserSocket data) {
            roomCenter.onReceiveNewUser(data);
        }
    };

    private Listener<Long> onMemberLeaveListener = new Listener<Long>(Long.class) {
        @Override
        public void call(Long data) {
            roomCenter.onReceiveUserLeave(data);
        }
    };

    private Listener<GiftMessage> onReceivedGift = new Listener<GiftMessage>(GiftMessage.class) {
        @Override
        public void call(GiftMessage data) {
            roomCenter.onReceivedGift(data);
        }
    };

    private Listener<HeartMessage> onReceivedHeart = new Listener<HeartMessage>(HeartMessage.class) {
        @Override
        public void call(HeartMessage data) {
            roomCenter.onReceivedHeart(data);
        }
    };

    public RoomSocketHelper(Context context, RoomCenter roomCenter) {
        this.context = context;
        this.roomCenter = roomCenter;
    }

    public void open(User user) {
        close();

        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = true;
            options.query = String.format("room=%s&token=%s&id=%s&email=%s&name=%s&avatar=%s",
                    roomCenter.roomId, user.getToken(), user.getId(), user.getEmail(), user.getName(), null);
            socket = IO.socket(context.getString(R.string.ls_socketUrl), options);

            // clear all events
            socket.off();

            // register events here
            socket.on("room connected", onRoomConnectedListener);
            socket.on(Socket.EVENT_ERROR, onRoomErrorListener);
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, onRoomErrorListener);
            socket.on(Socket.EVENT_DISCONNECT, onRoomErrorListener);

            socket.on("duplicated", onDuplicatedListener);
            socket.on("message", onMessageListener);
            socket.on("memberlist", onMemberListListener);
            socket.on("newmember", onNewMemberListener);
            socket.on("memberleave", onMemberLeaveListener);
            socket.on("action recived", onActionListener);
            socket.on("action done", onActionListener);
            socket.on("action full", onActionListener);
            socket.on("buy lounge", onLoungeListener);
            socket.on("gifts recived", onReceivedGift);
            socket.on("hearts recived", onReceivedHeart);
            socket.connect();

        } catch (URISyntaxException e) {
            Log.e(getClass().getSimpleName(), "" + e.getMessage());
        }
    }

    public void close() {
        if (socket != null) {
            socket.off();
            socket.disconnect();
        }
    }
}
