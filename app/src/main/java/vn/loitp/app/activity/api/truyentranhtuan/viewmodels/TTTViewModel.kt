package vn.loitp.app.activity.api.truyentranhtuan.viewmodels

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseViewModel
import com.core.helper.mup.comic.service.ComicApiClient
import com.core.helper.mup.comic.service.ComicRepository
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chap
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Chaps
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.Info
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("loitppComicLoginViewModel")
class TTTViewModel : BaseViewModel() {

    companion object {
        const val FOLDER = ".TTT"
    }

    private val repository = ComicRepository(ComicApiClient.apiService)

    val listComicActionLiveData: ActionLiveData<ActionData<List<Comic>>> = ActionLiveData()
    val tttChapActionLiveData: ActionLiveData<ActionData<TTTChap>> = ActionLiveData()

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
}
