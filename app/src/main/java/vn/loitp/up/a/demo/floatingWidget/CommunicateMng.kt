package vn.loitp.up.a.demo.floatingWidget

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

    @Keep
    class MsgFromActivity(var msg: String)

    @Keep
    class MsgFromService(var msg: String)
}
