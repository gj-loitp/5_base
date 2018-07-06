package vn.loitp.app.activity.customviews.ldebugview;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LENOVO on 7/6/2018.
 */

public class ComunicateDebug {
    public static class MsgFromActivity {
        private String msg;

        public MsgFromActivity(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static void postFromActivity(MsgFromActivity msg) {
        EventBus.getDefault().post(msg);
    }
}
