package com.function.epub

import com.function.epub.exception.ReadingException
import org.w3c.dom.Node

internal class Container : BaseFindings() {
    private var rootFile: XmlItem? = null

    @get:Throws(ReadingException::class)
    val fullPathValue: String?
        get() = if (rootFile != null &&
                rootFile?.attributes != null &&
                rootFile?.attributes!!.containsKey("full-path") &&
                rootFile?.attributes!!["full-path"] != null &&
                rootFile!!.attributes!!["full-path"] != "") {

            rootFile!!.attributes!!["full-path"]
        } else {
            throw ReadingException(Constants.EXTENSION_OPF + " file not found.")
        }

    override fun fillContent(node: Node?): Boolean {
        if (node?.nodeName != null && node.nodeName == "rootfile") {
            rootFile = nodeToXmlItem(node)
            return true
        }
        return false
    }

    // debug
    fun print() {
        println("\n\nPrinting Container...\n")
        println("title: " + if (rootFile != null) rootFile?.value else null)
    }
}
