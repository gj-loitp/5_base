package vn.loitp.data;

import vn.loitp.core.common.Constants;

/**
 * Created by www.muathu@gmail.com on 1/4/2018.
 */

public class ActivityData {
    private static final ActivityData ourInstance = new ActivityData();

    public static ActivityData getInstance() {
        return ourInstance;
    }

    private ActivityData() {
    }

    private int type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
