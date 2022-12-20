package com.loitp.func.epub.exception

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ReadingException : Exception {

    companion object {
        private const val serialVersionUID = -3674458503294310650L
    }

    constructor(message: String?) : super(message)
    constructor(
        message: String?,
        throwable: Throwable?
    ) : super(message, throwable)
}
