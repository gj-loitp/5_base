package com.loitp.func.mee.source

import removeComments
import java.io.File

class FileSource(pathname: String) : File(pathname), ISource {
    override fun getLines(): List<String> {
        return this.readLines().removeComments()
    }

    override fun getText(): String {
        return getLines().joinToString("\n")
    }
}
