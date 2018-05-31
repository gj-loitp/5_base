package vn.loitp.app.activity.demo.floatingwidget;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LENOVO on 5/31/2018.
 */

public class ComunicateMng {
    public static class Msg {
        private String msg;

        public Msg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static void post(Msg msg) {
        EventBus.getDefault().post(msg);
    }

}
