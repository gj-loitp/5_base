package vn.puresolutions.livestar.helper;

import android.util.SparseArray;

import java.util.ArrayList;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/30/2015
 */
public class NotificationCenter {

    private static int totalEvents = 1;

    // for room
    public static final int RoomLoading = totalEvents++;
    public static final int RoomLoaded = totalEvents++;
    public static final int RoomClose = totalEvents++;
    public static final int RoomSocketConnected = totalEvents++;
    public static final int RoomSocketError = totalEvents++;

    public static final int UserList = totalEvents++;
    public static final int UserNew = totalEvents++;
    public static final int UserLeave = totalEvents++;

    public static final int ChatReceivedNewMessage = totalEvents++;
    public static final int ReceivedAction = totalEvents++;
    public static final int BuyLounge = totalEvents++;
    public static final int LoadIdolCompleted = totalEvents++;
    public static final int HeartReceivedNewMessage = totalEvents++;

    public static final int UserLoggedIn = totalEvents++;
    public static final int UserReceiveHeart = totalEvents++;
    public static final int GiftsUpdate = totalEvents++;
    public static final int userMoneyChanged = totalEvents++;
    public static final int UserLoggedOut = totalEvents++;
    public static final int UserExpChanged = totalEvents++;
    public static final int UserLevelChanged = totalEvents++;

    public static final int EnterMainScreen = totalEvents++;
    public static final int KeyboardShow = totalEvents++;
    public static final int KeyboardHide = totalEvents++;

    public static final int FollowIdolSuccess = totalEvents++;
    public static final int FollowIdolFail = totalEvents++;

    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();

    private static volatile NotificationCenter Instance = null;

    public static NotificationCenter getInstance() {
        NotificationCenter localInstance = Instance;
        if (localInstance == null) {
            synchronized (NotificationCenter.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new NotificationCenter();
                }
            }
        }
        return localInstance;
    }

    public void addObserver(Object observer, int id) {
        ArrayList<Object> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, (objects = new ArrayList<>()));
        }
        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);
    }

    public void removeObserver(Object observer, int id) {
        ArrayList<Object> objects = observers.get(id);
        if (objects != null) {
            objects.remove(observer);
        }
    }

    public void postNotificationName(int id, Object... args) {
        ArrayList<Object> objects = observers.get(id);
        if (objects != null && !objects.isEmpty()) {
            for (int a = 0; a < objects.size(); a++) {
                Object obj = objects.get(a);
                ((NotificationCenterListener) obj).didReceivedNotification(id, args);
            }
        }
    }

    public interface NotificationCenterListener {
        void didReceivedNotification(int id, Object... args);
    }
}
