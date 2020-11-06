package vn.loitp.app.activity.api.truyentranhtuan.viewmodels

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseViewModel
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import vn.loitp.app.activity.api.truyentranhtuan.db.TTTDatabase
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chap
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chaps
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Info
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("loitppComicLoginViewModel")
class TTTViewModel : BaseViewModel() {
    val listComicActionLiveData: ActionLiveData<ActionData<List<Comic>>> = ActionLiveData()
    val tttChapActionLiveData: ActionLiveData<ActionData<TTTChap>> = ActionLiveData()
    val listPageActionLiveData: ActionLiveData<ActionData<List<String>>> = ActionLiveData()

    val listComicFavActionLiveData: ActionLiveData<ActionData<List<Comic>>> = ActionLiveData()
    val favComicLiveData: ActionLiveData<ActionData<Long>> = ActionLiveData()

    fun getListComic(link: String) {
        listComicActionLiveData.set(
                ActionData(isDoing = true)
        )

        fun parseData(link: String): ArrayList<Comic> {
            val listComic = ArrayList<Comic>()
            val document: Document
            try {
                /*parse jsoup from url*/
                document = Jsoup.connect(link).get()

                for (eMangaFocus in document.select("div[class=manga-focus]")) {
                    val comic = Comic()

                    val eTitle = eMangaFocus.select("span[class=manga]")
                    comic.title = eTitle.text()

                    val urlComic = eTitle.select("a")

                    // http://truyentranhtuan.com/anima/
                    comic.url = urlComic.attr("href")

                    val eDate = eMangaFocus.select("span[class=current-date]")
                    comic.date = eDate.text()

                    logD("comic " + BaseApplication.gson.toJson(comic))

                    listComic.add(comic)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return listComic
        }

        ioScope.launch {

            logD(">>>getListComic link $link")
            val listComic = parseData(link = link)
            logD("comicList " + BaseApplication.gson.toJson(listComic))

            listComicActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = listComic
                    )
            )
        }

    }

    fun getListChap(link: String) {
        tttChapActionLiveData.set(
                ActionData(isDoing = true)
        )
        logD(">>>getListChap link $link")

        fun doTask(link: String): TTTChap {
            val document: Document
            val tttChap = TTTChap()
            try {
                val info = Info()
                val listChap = ArrayList<Chap>()
                document = Jsoup.connect(link).get()
                val eInforBox = document.select("div#infor-box")

                //img cover
                val coverImage = eInforBox.select("img[src]")
                // .com/wp-content/uploads/2014/08/anima-anh-bia.jpg
                info.cover = coverImage.attr("src")

                //infor comic
                val infoComics = eInforBox.select("p")
                val infoComicList = java.util.ArrayList<String>()
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
                    listChap.add(chap)
                }

                val chaps = Chaps()
                chaps.chap = listChap
                tttChap.info = info
                tttChap.chaps = chaps
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return tttChap
        }

        ioScope.launch {
            val tttChap = doTask(link = link)
            logD("tttChap " + BaseApplication.gson.toJson(tttChap))

            tttChapActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = tttChap
                    )
            )
        }
    }

    fun getListPage(link: String) {
        listPageActionLiveData.set(
                ActionData(isDoing = true)
        )
        logD(">>>getListPage link $link")

        fun doTask(link: String): ArrayList<String> {
            var originalString: String
            var stringAfterSplit: String
            val arrString: Array<String>
            val document: Document
            val imgList = ArrayList<String>()

            return try {
                var needToSortList = false
                val subFirstString0 = "var slides_page_url_path = ["
                val subLastString0 = "if (slides_page_url_path.length > 0)"
                val subFirstString1 = "var slides_page_path ="
                val subLastString1 = "var use_server_gg"

                document = Jsoup.connect(link).get()
                val scriptElements = document.getElementsByTag("script")
                for (element in scriptElements) {
                    for (node in element.dataNodes()) {
                        originalString = node.wholeData
                        //fileHelper.writeToFile(null, "test.txt", string);
                        if (originalString.contains("slides_page_url_path")) {
                            var firstIndex = originalString.indexOf(subFirstString0)
                            var lastIndex = originalString.indexOf(subLastString0)

                            stringAfterSplit = originalString.substring(firstIndex + subFirstString0.length, lastIndex)
                            stringAfterSplit = stringAfterSplit.replace("];", "")
                            stringAfterSplit = stringAfterSplit.replace("\"", "")
                            stringAfterSplit = stringAfterSplit.trim { it <= ' ' }

                            if (stringAfterSplit.isEmpty()) {
                                firstIndex = originalString.indexOf(subFirstString1)
                                lastIndex = originalString.indexOf(subLastString1)
                                stringAfterSplit = originalString.substring(firstIndex + subFirstString1.length, lastIndex)
                                stringAfterSplit = stringAfterSplit.replace("];", "")
                                stringAfterSplit = stringAfterSplit.replace("[", "")
                                stringAfterSplit = stringAfterSplit.replace("\"", "")
                                stringAfterSplit = stringAfterSplit.trim { it <= ' ' }
                                needToSortList = true
                            }
                            arrString = stringAfterSplit.split(regex = ",".toRegex()).toTypedArray()
                            Collections.addAll(imgList, *arrString)
                            if (needToSortList) {
                                imgList.sortWith { o1, o2 ->
                                    o1.compareTo(other = o2, ignoreCase = true)
                                }
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

        ioScope.launch {
            val listPage = doTask(link = link)
            if (listPage.isNotEmpty()) {
                for (i in listPage.indices) {
                    val urlImg = listPage[i]
                    if (urlImg.contains("http://images2-focus-opensocial.googleusercontent.com")) {
                        val index = urlImg.lastIndexOf("url=http")
                        val tmp = urlImg.substring(index + 4)
                        listPage[i] = tmp
                    }
                }
            }
            listPageActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = listPage
                    )
            )
        }
    }

    fun getListComicFav() {
        listComicFavActionLiveData.set(
                ActionData(isDoing = true)
        )
        ioScope.launch {
            val listComicFav = TTTDatabase.instance?.tttDao()?.getListComic()
            listComicFavActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = listComicFav
                    )
            )
        }
    }

    fun favComic(comic: Comic) {
        favComicLiveData.set(
                ActionData(isDoing = true)
        )
        ioScope.launch {
            val id = TTTDatabase.instance?.tttDao()?.insert(comic)
            favComicLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = id
                    )
            )
        }
    }
}
