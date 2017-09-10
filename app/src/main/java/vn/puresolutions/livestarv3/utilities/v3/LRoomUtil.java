package vn.puresolutions.livestarv3.utilities.v3;

import java.util.Date;

/**
 * Created by www.muathu@gmail.com on 8/28/2017.
 */

public class LRoomUtil {
    private final static String TAG = LRoomUtil.class.getSimpleName();

    public static boolean isRoomUpdateInfoBefore(String createAt, String updateAt) {
        LLog.d(TAG, "createAt " + createAt);
        LLog.d(TAG, "updateAt " + updateAt);
        Date createDate = DateUtils.stringToDate(createAt, "yyyy-MM-dd");
        Date updateDate = DateUtils.stringToDate(updateAt, "yyyy-MM-dd");
        if (createDate != null && updateDate != null) {
            long diff = updateDate.getTime() - createDate.getTime();
            long seconds = diff / 1000;
            //long minutes = seconds / 60;
            //long hours = minutes / 60;
            //long days = hours / 24;
            //LLog.d(TAG, "seconds " + seconds + ", minutes " + minutes + ", hours: " + hours + ",days " + days);
            if (seconds > 20) {
                LLog.d(TAG, "room da tung duoc update info");
                return true;
            } else {
                LLog.d(TAG, "room chua tung duoc update info");
                return false;
            }
        } else {
            //TODO handle if cannot convert date from server
            return true;
        }
    }
}
