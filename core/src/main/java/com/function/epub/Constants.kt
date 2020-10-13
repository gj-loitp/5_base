package com.function.epub

internal object Constants {

    // Core files
    const val FILE_NAME_CONTAINER_XML = "container.xml"
    const val EXTENSION_NCX = ".ncx"
    const val EXTENSION_OPF = ".opf"

    // Keywords
    const val TAG_BODY_START = "<body"
    const val TAG_BODY_END = "</body>"
    const val TAG_IMG_START = "<img"
    const val TAG_IMG_END = "</img>"
    const val TAG_TABLE_START = "<table"
    const val TAG_TABLE_END = "</table>"
    const val TAG_OPENING = '<'
    const val TAG_CLOSING = '>'
    const val TAG_END = "/>"
    const val TAG_START = "</"
    const val DOT = '.'
    const val SLASH = '/'
    const val COLON = ':'
    const val EXTENSION_CSS = ".css"
    const val HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>"
    const val STRING_MARKER = "|"
    const val SAVE_FILE_NAME = "epubparser_progress.ser"
}
