package com.loitpcore.views.wwlMusic.utils

object LWWLMusicIllegal {

    fun check(ok: Boolean) {
        require(ok)
    }

    fun check(ok: Boolean, any: Any) {
        require(ok) {
            any.toString()
        }
    }

    fun check(any: Any?): Any {
        if (any != null) {
            return any
        }
        throw IllegalArgumentException()
    }

    fun check(any: Any?, objectMsg: Any): Any {
        if (any != null) {
            return any
        }
        throw IllegalArgumentException(objectMsg.toString())
    }
}
