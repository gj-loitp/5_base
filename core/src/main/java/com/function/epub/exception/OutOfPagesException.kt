package com.function.epub.exception

class OutOfPagesException(
    index: Int,
    val pageCount: Int
) : Exception("Out of bounds at position: " + index + ", max length is: " + (pageCount - 1)) {

    companion object {
        private const val serialVersionUID = 2607084451614265004L
    }
}
