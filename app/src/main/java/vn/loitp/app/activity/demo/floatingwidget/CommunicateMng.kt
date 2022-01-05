package vn.loitp.app.activity.demo.floatingwidget

import androidx.annotation.Keep
import org.greenrobot.eventbus.EventBus

@Keep
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
