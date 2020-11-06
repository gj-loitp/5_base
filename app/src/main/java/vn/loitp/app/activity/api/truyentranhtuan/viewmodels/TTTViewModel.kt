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
}
