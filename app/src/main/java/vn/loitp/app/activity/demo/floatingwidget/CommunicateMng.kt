package vn.loitp.app.activity.demo.floatingwidget

import org.greenrobot.eventbus.EventBus

/**
 * Created by loitp on 5/31/2018.
 */
class CommunicateMng {
    companion object {
        @JvmStatic
        fun postFromActivity(msg: MsgFromActivity) {
            EventBus.getDefault().post(msg)
        }

        @JvmStatic
        fun postFromService(msg: MsgFromService) {
            EventBus.getDefault().post(msg)
        }

    }

    class MsgFromActivity(var msg: String)

    class MsgFromService(var msg: String)
}
