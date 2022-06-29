package com.loitpcore.function.epub.exception

class ReadingException : Exception {

    companion object {
        private const val serialVersionUID = -3674458503294310650L
    }

    constructor(message: String?) : super(message)
    constructor(message: String?, throwable: Throwable?) : super(message, throwable)
}
