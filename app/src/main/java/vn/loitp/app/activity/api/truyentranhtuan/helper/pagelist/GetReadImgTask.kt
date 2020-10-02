package vn.loitp.app.activity.api.truyentranhtuan.helper.pagelist

import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*
import kotlin.collections.ArrayList

//TODO croutine
class GetReadImgTask(link: String, callback: Callback?) : AsyncTask<Void, Void, Void>() {
    private val logTag = javaClass.simpleName

    private var imagesListOfOneChap = ArrayList<String>() //list img per chap
    private var link = ""
    private var callback: Callback?

    init {
        this.link = link
        this.callback = callback
    }

    interface Callback {
        fun onSuccess(imagesListOfOneChap: List<String>?)
        fun onError()
    }

    override fun onPreExecute() {
        imagesListOfOneChap.clear()
        super.onPreExecute()
    }

    override fun doInBackground(vararg voids: Void): Void? {
        imagesListOfOneChap = doTask(link = link)
        if (!imagesListOfOneChap.isEmpty()) {
            for (i in imagesListOfOneChap.indices) {
                val urlImg = imagesListOfOneChap[i]
                if (urlImg.contains("http://images2-focus-opensocial.googleusercontent.com")) {
                    val index = urlImg.lastIndexOf("url=http")
                    val tmp = urlImg.substring(index + 4)
                    imagesListOfOneChap[i] = tmp
                }
            }
        }
        return null
    }

    override fun onPostExecute(aVoid: Void?) {
        if (!imagesListOfOneChap.isEmpty()) {
            callback?.onSuccess(imagesListOfOneChap)
        } else {
            callback?.onError()
        }
        super.onPostExecute(aVoid)
    }

    private fun doTask(link: String): ArrayList<String> {
        var originalString = ""
        var stringAfterSplit = ""
        val arrString: Array<String>
        val document: Document
        val imgList = ArrayList<String>()

        return try {
            var needToSortList = false
            val subFirstString_0 = "var slides_page_url_path = ["
            val subLastString_0 = "if (slides_page_url_path.length > 0)"
            val subFirstString_1 = "var slides_page_path ="
            val subLastString_1 = "var use_server_gg"

            document = Jsoup.connect(link).get()
            val scriptElements = document.getElementsByTag("script")
            for (element in scriptElements) {
                for (node in element.dataNodes()) {
                    originalString = node.wholeData
                    //fileHelper.writeToFile(null, "test.txt", string);
                    if (originalString.contains("slides_page_url_path")) {
                        var firstIndex = originalString.indexOf(subFirstString_0)
                        var lastIndex = originalString.indexOf(subLastString_0)

                        stringAfterSplit = originalString.substring(firstIndex + subFirstString_0.length, lastIndex)
                        stringAfterSplit = stringAfterSplit.replace("];", "")
                        stringAfterSplit = stringAfterSplit.replace("\"", "")
                        stringAfterSplit = stringAfterSplit.trim { it <= ' ' }

                        if (stringAfterSplit.isEmpty()) {
                            firstIndex = originalString.indexOf(subFirstString_1)
                            lastIndex = originalString.indexOf(subLastString_1)
                            stringAfterSplit = originalString.substring(firstIndex + subFirstString_1.length, lastIndex)
                            stringAfterSplit = stringAfterSplit.replace("];", "")
                            stringAfterSplit = stringAfterSplit.replace("[", "")
                            stringAfterSplit = stringAfterSplit.replace("\"", "")
                            stringAfterSplit = stringAfterSplit.trim { it <= ' ' }
                            needToSortList = true
                        }
                        arrString = stringAfterSplit.split(",".toRegex()).toTypedArray()
                        Collections.addAll(imgList, *arrString)
                        if (needToSortList) {
                            imgList.sortWith(Comparator { o1, o2 ->
                                o1.compareTo(o2, ignoreCase = true)
                            })
                        }
                        return imgList
                    }
                }
            }
            imgList
        } catch (e: Exception) {
            e.printStackTrace()
            imgList
        }
    }

}
