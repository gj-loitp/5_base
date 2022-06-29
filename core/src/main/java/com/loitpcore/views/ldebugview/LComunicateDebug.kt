package com.loitpcore.views.ldebugview

import org.greenrobot.eventbus.EventBus

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
