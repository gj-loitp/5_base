package com.interfaces

import com.model.GG
import okhttp3.Call

interface GGCallback {
    fun onGGFailure(call: Call, e: Exception)

    fun onGGResponse(listGG: ArrayList<GG>)
}
