package vn.puresolutions.livestar.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.SuggestPurchaseActivity;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.ExpChanging;
import vn.puresolutions.livestar.core.api.model.LevelChanging;
import vn.puresolutions.livestar.core.api.model.MoneyChanging;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.custom.socket.Listener;

/**
 * Created by khanh on 7/2/16.
 */
public class NotificationSocketHelper {
    private static final String TAG = "NotificationSocket";

    private Socket socket;
    private Context context;

    private Listener<MoneyChanging> onMoneyChangedListener = new Listener<MoneyChanging>(MoneyChanging.class) {
        @Override
        public void call(MoneyChanging data) {
            Log.i(TAG, data.getOldValue() + ", " + data.getNewValue());

            User user = UserInfo.getUserLoggedIn();
            if (user != null) {
                int delta = data.getOldValue() - data.getNewValue();
                if (delta > 0) { // giảm tiền
                    // số tiền mới phải ít hơn số tiền hiện tại
                    if (data.getNewValue() < user.getMoney()) {
                        user.setMoney(data.getNewValue());

                        UserInfo.saveUserLogged();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.userMoneyChanged, user.getMoney());
                    }
                } else if (delta < 0) { // tăng tiền
                    // số tiền mới phải lớn hơn số tiền hiện tại
                    if (data.getNewValue() > user.getMoney()) {
                        user.setMoney(data.getNewValue());

                        UserInfo.saveUserLogged();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.userMoneyChanged, user.getMoney());
                    }
                }
            }

            if (data.getNewValue() < 20) {
                context.startActivity(new Intent(context, SuggestPurchaseActivity.class));
            }
        }
    };

    private Listener<LevelChanging> onLevelChangedListener = new Listener<LevelChanging>(LevelChanging.class) {
        @Override
        public void call(LevelChanging data) {
            User user = UserInfo.getUserLoggedIn();
            if (user != null) {
                user.setLevel(data.getNewLevel());
                UserInfo.saveUserLogged();
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserLevelChanged, data);
        }
    };

    private Listener<ExpChanging> onExpChangedListener = new Listener<ExpChanging>(ExpChanging.class) {
        @Override
        public void call(ExpChanging data) {
            User user = UserInfo.getUserLoggedIn();
            if (user != null) {
                user.setLevelPercent((int) data.getPercent());
                UserInfo.saveUserLogged();
            }
            NotificationCenter.getInstance()
                    .postNotificationName(NotificationCenter.UserExpChanged, (int) (data.getNewExp() - data.getOldExp()));
        }
    };

    public NotificationSocketHelper(Context context) {
        this.context = context;
    }

    public void open(User user) {
        close();

        try {
            IO.Options options = new IO.Options();
            options.forceNew = true;
            options.reconnection = true;
            options.query = String.format("token=%s&id=%s&email=%s&name=%s&avatar=%s",
                    user.getToken(), user.getId(), user.getEmail(), user.getName(), user.getAvatar());
            socket = IO.socket(context.getString(R.string.ls_notificationSocketUrl), options);

            // clear all events
            socket.off();

            // register events here
            socket.on("notification connected", args -> Log.i(TAG, "notification connected"));
            socket.on("change money", onMoneyChangedListener);
            socket.on("change exp", onExpChangedListener);
            socket.on("levelup", onLevelChangedListener);

            socket.connect();

        } catch (URISyntaxException e) {
            Log.e(getClass().getSimpleName(), "" + e.getMessage());
        }
    }

    public boolean isConnected() {
        return socket != null && socket.connected();
    }

    public void close() {
        if (socket != null) {
            socket.off();
            socket.disconnect();
            socket = null;
            Log.i(TAG, "notification closed");
        }
    }
}
