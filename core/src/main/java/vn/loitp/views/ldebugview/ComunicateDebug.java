package vn.loitp.views.ldebugview;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LENOVO on 7/6/2018.
 */

public class ComunicateDebug {
    public static class MsgFromActivity {
        public static int TYPE_D = 0;
        public static int TYPE_E = -1;
        public static int TYPE_I = 1;
        private int type;
        private String msg;

        public MsgFromActivity(int type, String msg) {
            this.type = type;
            this.msg = msg;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
