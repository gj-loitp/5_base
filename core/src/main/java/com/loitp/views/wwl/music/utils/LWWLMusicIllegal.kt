package com.loitp.views.wwl.music.utils

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LWWLMusicIllegal {

    fun check(ok: Boolean) {
        require(ok)
    }

    fun check(
        ok: Boolean,
        any: Any
    ) {
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

    fun check(
        any: Any?,
        objectMsg: Any
    ): Any {
        if (any != null) {
            return any
        }
        throw IllegalArgumentException(objectMsg.toString())
    }
}
