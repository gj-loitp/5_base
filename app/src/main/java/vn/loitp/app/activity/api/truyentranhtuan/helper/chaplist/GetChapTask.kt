package vn.loitp.app.activity.api.truyentranhtuan.helper.chaplist

import android.os.AsyncTask
import com.core.base.BaseApplication
import com.core.utilities.LStoreUtil
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chap
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chaps
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Info
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap
import java.util.*

//TODO convert coroutine
class GetChapTask(
        private val url: String,
        private val callback: Callback?)
    : AsyncTask<Void, Void, Boolean>() {

    companion object {
        private const val MAX_TRY_AGAIN = 5
    }

    private val logTag = javaClass.simpleName
    private var tttChap: TTTChap? = null
    private var chapList: List<Chap> = ArrayList()
    private var stringInfo = ""

    interface Callback {
        fun onSuccess(tttChap: TTTChap?)
        fun onError()
    }

    override fun doInBackground(vararg voids: Void): Boolean {
        tttChap = doTask(url)

        var getChapSuccess = false

        if (tttChap != null) {
            val chaps = tttChap?.chaps
            if (chaps != null) {
                chapList = chaps.chap
                //kiem tra xem cac phan tu cua chapList co giong nhau ko
                try {
                    if (chapList[0].tit == chapList[1].tit) {
                        //co trung
                        for (i in chapList.indices) {
                            chapList[i].tit = chapList[i].tit + " * Chap (" + (chapList.size - i) + ")"
                        }
                    }
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
                stringInfo = """
                    ${tttChap?.info?.otherName}

                    ${tttChap?.info?.author}

                    ${tttChap?.info?.type}

                    ${tttChap?.info?.newChap}

                    ${tttChap?.info?.summary}

                    """.trimIndent()

                val fileSaved = LStoreUtil.writeToFile(
                        folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN,
                        fileName = LStoreUtil.getFileNameComic(url),
                        body = BaseApplication.gson.toJson(tttChap))
                getChapSuccess = fileSaved != null
            }
        }
        return getChapSuccess
    }

    override fun onPostExecute(getChapSuccess: Boolean) {
        if (getChapSuccess) {
            callback?.onSuccess(tttChap)
        } else {
            callback?.onError()
        }
        super.onPostExecute(getChapSuccess)
    }

    private var numberOfTryAgain = 0
    private fun doTask(link: String): TTTChap {
        val document: Document
        val tttChap = TTTChap()
        try {
            val info = Info()
            val chapList: MutableList<Chap> = ArrayList()
            document = Jsoup.connect(link).get()
            val eInforBox = document.select("div#infor-box")

            //img cover
            val coverImage = eInforBox.select("img[src]")
            // .com/wp-content/uploads/2014/08/anima-anh-bia.jpg
            info.cover = coverImage.attr("src")

            //infocomic
            val infoComics = eInforBox.select("p")
            val infoComicList = ArrayList<String>()
            for (x in infoComics) {
                infoComicList.add(x.text())
            }
            try {
                info.otherName = infoComicList[0]
                info.author = infoComicList[1]
                info.type = infoComicList[2]
                info.newChap = infoComicList[3]
                info.summary = infoComicList[4]
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val eChap = document.select("span[class=chapter-name]")
            for (x in eChap) {
                val chap = Chap()
                chap.tit = x.text()
                val linkChap = x.select("a")
                // .com/anima-chuong-74/
                chap.url = linkChap.attr("href")
                chapList.add(chap)
            }

            //url, ngay release chap, tam thoi chua can dung
            /*Elements url = document.select("span[class=url-name]");
              for (Element d : url) {
                Log.d(TAG, "url: " + d.text());//24.07.2015
              }*/
            val chaps = Chaps()
            chaps.chap = chapList
            tttChap.info = info
            tttChap.chaps = chaps

        } catch (e: Exception) {
            e.printStackTrace()
            numberOfTryAgain++
            if (numberOfTryAgain > MAX_TRY_AGAIN) {
                return tttChap
            }
            doTask(link)
        }
        return tttChap
    }

}
