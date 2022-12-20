package com.loitp.func.epub.exception

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class OutOfPagesException(
    index: Int,
    val pageCount: Int
) : Exception("Out of bounds at position: " + index + ", max length is: " + (pageCount - 1)) {

    companion object {
        private const val serialVersionUID = 2607084451614265004L
    }
}
