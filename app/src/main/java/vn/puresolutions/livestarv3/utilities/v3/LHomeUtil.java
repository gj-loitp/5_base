package vn.puresolutions.livestarv3.utilities.v3;

import org.greenrobot.eventbus.EventBus;

import vn.puresolutions.livestarv3.activity.homescreen.fragment.homenew.FrmHomeNew;

/**
 * Created by www.muathu@gmail.com on 9/6/2017.
 */

public class LHomeUtil {
    public static void reloadDataFollowOrSuggest() {
        //eventbus
        FrmHomeNew.FollowOrSuggestEvent followOrSuggestEvent = new FrmHomeNew.FollowOrSuggestEvent();
        followOrSuggestEvent.setReloadData(true);
        EventBus.getDefault().post(followOrSuggestEvent);
    }
}
