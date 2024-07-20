package com.loitp.func.mee.source

import removeComments

class TextSource(val value: String) : ISource {
    override fun getLines(): List<String> {
        return value.split("\n").removeComments()
    }

    override fun getText(): String {
        return value
    }
}
