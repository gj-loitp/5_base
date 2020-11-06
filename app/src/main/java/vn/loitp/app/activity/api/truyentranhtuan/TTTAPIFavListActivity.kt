package vn.loitp.app.activity.api.truyentranhtuan

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.google.ads.interactivemedia.v3.internal.it
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_api_ttt_fav_list.*
import kotlinx.android.synthetic.main.activity_api_ttt_fav_list.textView
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.viewmodels.TTTViewModel

@LogTag("TTTAPIFavListActivity")
@IsFullScreen(false)
class TTTAPIFavListActivity : BaseFontActivity() {

    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_fav_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModels()
        setupViews()

        tttViewModel?.getListComicFav()
    }

    private fun setupViews() {
        btAdd.setSafeOnClickListener {

        }
        btRemove.setSafeOnClickListener {

        }
    }

//    btAddVuongPhongLoi.setOnClickListener {
//        val comic = Comic()
//        comic.date = "29.07.2014"
//        comic.url = "http://truyentranhtuan.com/vuong-phong-loi-i/"
//        comic.title = "Vương Phong Lôi I"
//        addComic(comic)
//    }

    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->
            vm.listComicFavActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                    if (isSuccess == true) {
                        val listComicFav = actionData.data
                        listComicFav?.let {
                            LUIUtil.printBeautyJson(o = it, textView = textView)
                        }
                    }
                }
            })
        }
    }
}