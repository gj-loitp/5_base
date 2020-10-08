package com.function.epub

import com.function.epub.exception.ReadingException
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.util.*

// Helper classes and methods used in Findings
abstract class BaseFindings {
    @Throws(ReadingException::class)

    abstract fun fillContent(node: Node?): Boolean

    inner class XmlItem {
        var value: String? = null
        var attributes: Map<String, String>? = null

    }

    protected fun nodeListToXmlItemList(nodeList: NodeList): List<XmlItem> {
        val xmlItemList: MutableList<XmlItem> = ArrayList()
        for (i in 0 until nodeList.length) {
            val xmlItem = nodeToXmlItem(nodeList.item(i))
            xmlItem.attributes?.let {
                xmlItemList.add(xmlItem)
            }
        }
        return xmlItemList
    }

    protected fun nodeToXmlItem(node: Node): XmlItem {
        val xmlItem = XmlItem()
        xmlItem.value = node.textContent
        if (node.hasAttributes()) {
            val nodeMap = node.attributes
            val attributes: MutableMap<String, String> = HashMap()
            for (j in 0 until nodeMap.length) {
                val attribute = nodeMap.item(j)
                attributes[attribute.nodeName] = attribute.nodeValue
            }
            xmlItem.attributes = attributes
        }
        return xmlItem
    }
}
