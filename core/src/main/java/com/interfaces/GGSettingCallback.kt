package com.interfaces

import com.model.App
import okhttp3.Call
import java.io.IOException

interface GGSettingCallback {
    fun onGGFailure(call: Call, e: IOException)

    fun onGGResponse(app: App, isNeedToShowMsg: Boolean)
}
