package com.loitp.func.epub

import com.loitp.func.epub.exception.ReadingException
import com.loitp.func.epub.ContextHelper.getTextAfterCharacter
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.lang.reflect.Field

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// package.opf
class Package : BaseFindings() {
    val metadata: Metadata
    val manifest: Manifest
    val spine: Spine
    private val guide: Guide

    private var isMetadataFound = false
    private var isManifestFound = false
    private var isSpineFound = false
    private var isGuideFound = false

    init {
        metadata = Metadata()
        manifest = Manifest()
        spine = Spine()
        guide = Guide()
    }

    inner class Metadata {
        // Required Terms
        val title: String? = null
        private val language: String? = null
        private val identifier: String? = null

        // Optional Terms
        private val creator: String? = null
        private val contributor: String? = null
        private val publisher: String? = null
        private val subjects = ArrayList<String>()
        val description: String? = null
        val date: String? = null
        val type: String? = null
        val format: String? = null
        val source: String? = null
        private val relation: String? = null
        private val coverage: String? = null
        private val rights: String? = null
        var coverImageId: String? = null

        @Throws(ReadingException::class)
        fun fillAttributes(nodeList: NodeList) {
            val fields = Metadata::class.java.declaredFields
            var subjectList: MutableList<String?>? = null
            for (i in 0 until nodeList.length) {
                val node = nodeList.item(i)
//                if (node.nodeValue != null && node.nodeValue.matches("\\s+")) {
                if (node.nodeValue != null && node.nodeValue.matches(Regex(pattern = """\\s+"""))) {
                    continue
                }
                var nodeName = node.nodeName
                if (nodeName.contains(Constants.COLON.toString())) {
                    nodeName = getTextAfterCharacter(nodeName, Constants.COLON)
                }
                if (nodeName == "meta") {
                    if (node.hasAttributes()) {
                        val nodeMap = node.attributes
                        var isCoverImageNodeFound = false
                        var j = 0
                        while (j < nodeMap.length) {
                            val attribute = nodeMap.item(j)
                            if (!isCoverImageNodeFound && attribute.nodeName == "name" && attribute.nodeValue == "cover") { // This node states cover-image id.
                                isCoverImageNodeFound = true
                                j = -1 // Start the search from the beginng to find 'content' value.
                            } else if (isCoverImageNodeFound && attribute.nodeName == "content") {
                                coverImageId = attribute.nodeValue
                                break
                            }
                            j++
                        }
                    }
                }
                for (j in fields.indices) {
                    if (nodeName == fields[j].name) {
                        if (fields[j].name == "subject") {
                            if (subjectList == null) {
                                subjectList = ArrayList()
                            }
                            subjectList.add(nodeList.item(i).textContent)
                        } else {
                            fields[j].isAccessible = true
                            try {
                                fields[j][this] = nodeList.item(i).textContent
                                break
                            } catch (e: IllegalArgumentException) {
                                e.printStackTrace()
                                throw ReadingException("Exception while parsing " + Constants.EXTENSION_OPF + " content: " + e.message)
                            } catch (e: IllegalAccessException) {
                                e.printStackTrace()
                                throw ReadingException("Exception while parsing " + Constants.EXTENSION_OPF + " content: " + e.message)
                            }
                        }
                    }
                }
            }
            if (subjectList != null) {
                val field: Field
                try {
                    field = Metadata::class.java.getDeclaredField("subject")
                    field.isAccessible = true
                    field[this] = subjectList.toTypedArray()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    throw ReadingException("Exception while parsing subjects " + Constants.EXTENSION_OPF + " content: " + e.message)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                    throw ReadingException("Exception while parsing subjects " + Constants.EXTENSION_OPF + " content: " + e.message)
                } catch (e: NegativeArraySizeException) {
                    e.printStackTrace()
                    throw ReadingException("Exception while parsing subjects " + Constants.EXTENSION_OPF + " content: " + e.message)
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                    throw ReadingException("Exception while parsing subjects " + Constants.EXTENSION_OPF + " content: " + e.message)
                } catch (e: SecurityException) {
                    e.printStackTrace()
                    throw ReadingException("Exception while parsing subjects " + Constants.EXTENSION_OPF + " content: " + e.message)
                }
            }
        }

        fun print() {
            println("\n\nPrinting Metadata...\n")
            println("title: $title")
            println("language: $language")
            println("identifier: $identifier")
            println("creator: $creator")
            println("contributor: $contributor")
            println("publisher: $publisher")
            println("subject: $subjects")
            println("description: $description")
            println("date: $date")
            println("type: $type")
            println("format: $format")
            println("source: $source")
            println("relation: $relation")
            println("coverage: $coverage")
            println("rights: $rights")
            println("coverImageHref: $coverImageId")
        }
    }

    inner class Manifest {
        var xmlItemList: List<XmlItem>
            private set

        fun fillXmlItemList(nodeList: NodeList?) {
            nodeList?.let {
                xmlItemList = nodeListToXmlItemList(nodeList = it)
            }
        }

        fun print() {
            println("\n\nPrinting Manifest...\n")
            for (i in xmlItemList.indices) {
                val xmlItem = xmlItemList[i]
                println("xmlItem(" + i + ")" + ": value:" + xmlItem.value + " attributes: " + xmlItem.attributes)
            }
        }

        init {
            xmlItemList = ArrayList()
        }
    }

    // <b>Ordered</b> Term of Contents, mostly filled with ids of application/xhtml+xml files in manifest node.
    inner class Spine {
        var xmlItemList: List<XmlItem>
            private set

        fun fillXmlItemList(nodeList: NodeList?) {
            nodeList?.let {
                xmlItemList = nodeListToXmlItemList(nodeList = it)
            }
        }

        fun print() {
            println("\n\nPrinting Spine...\n")
            for (i in xmlItemList.indices) {
                val xmlItem = xmlItemList[i]
                println("xmlItem(" + i + ")" + ": value:" + xmlItem.value + " attributes: " + xmlItem.attributes)
            }
        }

        init {
            xmlItemList = ArrayList()
        }
    }

    inner class Guide {
        private var xmlItemList: List<XmlItem>

        fun fillXmlItemList(nodeList: NodeList?) {
            nodeList?.let {
                xmlItemList = nodeListToXmlItemList(nodeList = it)
            }
        }

        fun print() {
            println("\n\nPrinting Guide...\n")
            for (i in xmlItemList.indices) {
                val xmlItem = xmlItemList[i]
                println("xmlItem(" + i + ")" + ": value:" + xmlItem.value + " attributes: " + xmlItem.attributes)
            }
        }

        init {
            xmlItemList = ArrayList()
        }
    }

    @Throws(ReadingException::class)
    override fun fillContent(node: Node?): Boolean {
        var nodeName = node?.nodeName
        if (nodeName?.contains(Constants.COLON.toString()) == true) {
            nodeName = getTextAfterCharacter(text = nodeName, character = Constants.COLON)
        }
        when (nodeName) {
            "metadata" -> {
                node?.childNodes?.let {
                    metadata.fillAttributes(nodeList = it)
                    isMetadataFound = true
                }
            }
            "manifest" -> {
                node?.childNodes?.let {
                    manifest.fillXmlItemList(nodeList = it)
                    isManifestFound = true
                }
            }
            "spine" -> {
                node?.childNodes?.let {
                    spine.fillXmlItemList(nodeList = it)
                    isSpineFound = true
                }
            }
            "guide" -> {
                node?.childNodes?.let {
                    guide.fillXmlItemList(nodeList = it)
                    isGuideFound = true
                }
            }
        }
        return isMetadataFound && isManifestFound && isSpineFound && isGuideFound
    }

    fun print() {
        metadata.print()
        manifest.print()
        spine.print()
        guide.print()
    }
}
