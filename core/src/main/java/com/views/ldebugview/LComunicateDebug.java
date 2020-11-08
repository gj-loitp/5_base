package com.views.ldebugview;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by LENOVO on 7/6/2018.
 */
//TODO convert kotlin
public class LComunicateDebug {
    public static class MsgFromActivity {
        public static int TYPE_D = 0;
        public static int TYPE_E = -1;
        public static int TYPE_I = 1;
        private int type;
        private String msg;
        private Object object;

        public MsgFromActivity(int type, String msg, Object object) {
            this.type = type;
            this.msg = msg;
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
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
