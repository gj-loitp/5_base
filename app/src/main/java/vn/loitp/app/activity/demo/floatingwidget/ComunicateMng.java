package vn.loitp.app.activity.demo.floatingwidget;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by loitp on 5/31/2018.
 */

public class ComunicateMng {
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

    public static class MsgFromService {
        private String msg;

        public MsgFromService(String msg) {
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

    public static void postFromService(MsgFromService msg) {
        EventBus.getDefault().post(msg);
    }

}
