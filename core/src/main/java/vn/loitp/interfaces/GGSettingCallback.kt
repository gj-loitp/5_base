package vn.loitp.interfaces

import okhttp3.Call
import vn.loitp.model.App
import java.io.IOException

interface GGSettingCallback {
    fun onGGFailure(call: Call, e: IOException)

    fun onGGResponse(app: App, isNeedToShowMsg: Boolean)
}
