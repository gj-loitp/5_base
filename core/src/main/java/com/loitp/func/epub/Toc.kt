package com.loitp.func.epub

import androidx.annotation.Keep
import com.loitp.func.epub.exception.ReadingException
import com.loitp.func.epub.ContextHelper.encodeToUtf8
import com.loitp.func.epub.ContextHelper.getTextAfterCharacter
import org.w3c.dom.DOMException
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.Serializable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// toc.ncx
@Keep
class Toc : BaseFindings(), Serializable {

    companion object {
        private const val serialVersionUID = 8154412879349792795L
    }

    val head: Head
    val navMap: NavMap
    var lastPageIndex = 0

    init {
        head = Head()
        navMap = NavMap()
    }

    @Transient
    private var isHeadFound = false

    @Transient
    private var isNavMapFound = false

    inner class Head : Serializable {

//        companion object {
//            private const val serialVersionUID = -5861717309893477622L
//        }

        private val uid: String? = null
        private val depth: String? = null
        private val totalPageCount: String? = null
        private val maxPageNumber: String? = null

        @Throws(ReadingException::class)
        fun fillAttributes(nodeList: NodeList) {
            val fields = Head::class.java.declaredFields
            for (i in 0 until nodeList.length) {
                var metaNodeName = nodeList.item(i).nodeName
                if (metaNodeName.contains(Constants.COLON.toString())) {
                    metaNodeName = getTextAfterCharacter(metaNodeName, Constants.COLON)
                }
                if (metaNodeName == "meta") {
                    val attributes = nodeList.item(i).attributes
                    for (k in 0 until attributes.length) {
                        val attribute = attributes.item(k)
                        if (attribute.nodeName == "name") {
                            var attributeNodeValue = attribute.nodeValue
                            if (attributeNodeValue.contains(Constants.COLON.toString())) {
                                attributeNodeValue = getTextAfterCharacter(
                                    text = attributeNodeValue,
                                    character = Constants.COLON
                                )
                            }
                            for (j in fields.indices) {
                                if (attributeNodeValue == fields[j].name) {

                                    // Find content in attributes
                                    for (l in 0 until attributes.length) {
                                        if (attributes.item(l).nodeName == "content") {
                                            fields[j].isAccessible = true
                                            try {
                                                fields[j][this] = attributes.item(l).nodeValue
                                            } catch (e: IllegalArgumentException) {
                                                e.printStackTrace()
                                                throw ReadingException("Exception while parsing " + Constants.EXTENSION_NCX + " content: " + e.message)
                                            } catch (e: IllegalAccessException) {
                                                e.printStackTrace()
                                                throw ReadingException("Exception while parsing " + Constants.EXTENSION_NCX + " content: " + e.message)
                                            } catch (e: DOMException) {
                                                e.printStackTrace()
                                                throw ReadingException("Exception while parsing " + Constants.EXTENSION_NCX + " content: " + e.message)
                                            }
                                            break
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        fun print() {
            println("\n\nPrinting Head...\n")
            println("uid: $uid")
            println("depth: $depth")
            println("totalPageCount: $totalPageCount")
            println("maxPageNumber: $maxPageNumber")
        }
    }

    inner class NavMap : Serializable {

//        companion object {
//            private const val serialVersionUID = -3629764613712749465L
//        }

        private var navPoints: MutableList<NavPoint>? = null
        fun getNavPoints(): List<NavPoint>? {
            return navPoints
        }

        // check: navMap (epub2) and pageList (epub3) should be merged as well. Just as we merged spine and toc.ncx. Or just sorting them by their playOrder is enough?
        @Throws(ReadingException::class)
        fun fillNavPoints(possiblyNavPoints: NodeList) {
            if (navPoints == null) {
                navPoints = ArrayList()
            }
            for (i in 0 until possiblyNavPoints.length) {
                var navPointNodeName = possiblyNavPoints.item(i).nodeName
                if (navPointNodeName.contains(Constants.COLON.toString())) {
                    navPointNodeName =
                        getTextAfterCharacter(text = navPointNodeName, character = Constants.COLON)
                }
                if (navPointNodeName == "navPoint" || navPointNodeName == "pageTarget") {
                    val navPoint = NavPoint()
                    val nodeMap = possiblyNavPoints.item(i).attributes
                    for (j in 0 until nodeMap.length) {
                        val attribute = nodeMap.item(j)
                        when (attribute.nodeName) {
                            "id" -> {
                                navPoint.id = attribute.nodeValue
                            }
                            "playOrder" -> {
                                navPoint.playOrder = attribute.nodeValue.toInt()
                            }
                            "type" -> {
                                navPoint.type = attribute.nodeValue
                            }
                        }
                    }
                    var hasNestedNavPoints = false
                    val navPointChildNodes = possiblyNavPoints.item(i).childNodes
                    for (k in 0 until navPointChildNodes.length) {
                        val navPointChild = navPointChildNodes.item(k)
                        var navPointChildNodeName = navPointChild.nodeName
                        if (navPointChildNodeName.contains(Constants.COLON.toString())) {
                            navPointChildNodeName = getTextAfterCharacter(
                                text = navPointChildNodeName,
                                character = Constants.COLON
                            )
                        }
                        if (navPointChildNodeName == "navLabel") {
                            val navLabelChildNodes = navPointChild.childNodes
                            for (l in 0 until navLabelChildNodes.length) {
                                var navLabelChildNodeName = navLabelChildNodes.item(l).nodeName
                                if (navLabelChildNodeName.contains(Constants.COLON.toString())) {
                                    navLabelChildNodeName = getTextAfterCharacter(
                                        text = navLabelChildNodeName,
                                        character = Constants.COLON
                                    )
                                }
                                if (navLabelChildNodeName == "text") {
                                    navPoint.navLabel = navLabelChildNodes.item(l).textContent
                                }
                            }
                        } else if (navPointChildNodeName == "content") {
                            val contentAttributes = navPointChild.attributes
                            for (m in 0 until contentAttributes.length) {
                                val contentAttribute = contentAttributes.item(m)
                                if (contentAttribute.nodeName == "src") {
                                    val contentSrc = contentAttribute.nodeValue
                                    if (contentSrc != null && contentSrc != "") {
                                        val encodedContentSrc = encodeToUtf8(
                                            getTextAfterCharacter(
                                                text = contentSrc,
                                                character = Constants.SLASH
                                            )
                                        )
                                        navPoint.contentSrc = encodedContentSrc
                                    }
                                }
                            }
                        } else if (!hasNestedNavPoints && navPointChildNodeName == "navPoint") {
                            hasNestedNavPoints = true
                        }
                    }
                    var duplicateOrNullContentSrc = false
                    navPoints?.let {
                        for (navPointItem in it) {
                            if (navPoint.contentSrc == null || navPoint.contentSrc == navPointItem.contentSrc) {
                                duplicateOrNullContentSrc = true
                                break
                            }
                        }
                    }
                    if (!duplicateOrNullContentSrc) {
                        navPoints?.add(navPoint)
                    }

                    // Sometimes navPoint nodes may have another navPoint nodes inside them. Even though this means malformed toc.ncx file, it shouldn't hurt to try to read them as well.
                    if (hasNestedNavPoints) {
                        fillNavPoints(navPointChildNodes)
                    }
                }
            }
        }

        fun sortNavMaps() {

            // If playOrders are not given, then use the order in file.
            navPoints?.sortWith { o1, o2 ->
                if (o1.playOrder < o2.playOrder) -1 else 1 // if equals, first occurence should be sorted as first.
            }
        }

        fun print() {
            println("\n\nPrinting NavPoints...\n")
            navPoints?.let {
                for (i in it.indices) {
                    val navPoint = it[i]
                    println("navPoint (" + i + ") id: " + navPoint.id + ", playOrder: " + navPoint.playOrder + ", navLabel(Text): " + navPoint.navLabel + ", content src: " + navPoint.contentSrc)
                }
            }
        }
    }

    @Throws(ReadingException::class)
    override fun fillContent(node: Node?): Boolean {
        var nodeName = node?.nodeName ?: ""
        if (nodeName.contains(Constants.COLON.toString())) {
            nodeName = getTextAfterCharacter(text = nodeName, character = Constants.COLON)
        }
        if (nodeName == "head") {
            node?.childNodes?.let {
                head.fillAttributes(it)
            }
            isHeadFound = true
        } else if (nodeName == "navMap" || nodeName == "pageList") { // if pageList exists then it's epub3 if only navMap exists then it's epub2.
            node?.childNodes?.let {
                navMap.fillNavPoints(it)
            }
            navMap.sortNavMaps()
            isNavMapFound = true
        }
        return isHeadFound && isNavMapFound
    }

    fun print() {
        head.print()
        navMap.print()
    }
}
