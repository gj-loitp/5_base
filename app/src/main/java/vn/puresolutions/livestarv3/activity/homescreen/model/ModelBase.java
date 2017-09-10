package vn.puresolutions.livestarv3.activity.homescreen.model;

import vn.puresolutions.livestar.common.Constants;

/**
 * Created by www.muathu@gmail.com on 7/25/2017.
 */

public class ModelBase {
    protected int type = Constants.TYPE_IDOL_FOLLOW_OR_SUGGEST;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
