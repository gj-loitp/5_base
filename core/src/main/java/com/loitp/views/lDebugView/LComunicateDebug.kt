package com.loitp.views.lDebugView

import org.greenrobot.eventbus.EventBus

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LComunicateDebug {

    @JvmStatic
    fun postFromActivity(msg: MsgFromActivity) {
        EventBus.getDefault().post(msg)
    }

    class MsgFromActivity(
        var type: Int = TYPE_D,
        var msg: String? = null,
        var any: Any? = null
    ) {
        companion object {
            @JvmField
            var TYPE_D = 0

            @JvmField
            var TYPE_E = -1

            @JvmField
            var TYPE_I = 1
        }
    }
}
