package vn.loitp.app.activity.api.truyentranhtuan

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_remove_fav_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.RemoveComicFavListTask
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic

@LayoutId(R.layout.activity_api_ttt_remove_fav_list)
@LogTag("TTTAPIRemoveFavListActivity")
@IsFullScreen(false)
class TTTAPIRemoveFavListActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        indicatorView.hide()
        btAddVuongPhongLoi.setOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentranhtuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            removeComic(comic)
        }
        btAddLayers.setOnClickListener {
            val comic = Comic()
            comic.date = "28.06.2015"
            comic.url = "http://truyentranhtuan.com/layers/"
            comic.title = "Layers"
            removeComic(comic)
        }
        btAddBlackHaze.setOnClickListener {
            val comic = Comic()
            comic.date = "12.03.2017"
            comic.url = "http://truyentranhtuan.com/black-haze/"
            comic.title = "Black Haze"
            removeComic(comic)
        }
    }

    private fun removeComic(comic: Comic) {
        indicatorView.smoothToShow()
        RemoveComicFavListTask(mComic = comic,
                callback = object : RemoveComicFavListTask.Callback {
                    override fun onRemoveComicSuccess(mComic: Comic, comicList: List<Comic>) {
                        showShortInformation("onRemoveComicSuccess")
                        LUIUtil.printBeautyJson(o = comicList, textView = textView)
                        indicatorView.smoothToHide()
                    }

                    override fun onComicIsNotExist(mComic: Comic, comicList: List<Comic>) {
                        showShortInformation("onComicIsNotExist")
                        LUIUtil.printBeautyJson(o = comicList, textView = textView)
                        indicatorView.smoothToHide()
                    }

                    override fun onRemoveComicError() {
                        showShortInformation("onRemoveComicError")
                        indicatorView.smoothToHide()
                    }
                }).execute()
    }
}