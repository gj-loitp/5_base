package vn.loitp.app.activity.api.truyentranhtuan

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.ttt.model.comic.Comic
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_api_ttt_fav_list.*
import kotlinx.android.synthetic.main.activity_api_ttt_fav_list.textView
import vn.loitp.app.R

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
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentranhtuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            tttViewModel?.favComic(comic = comic)
        }
        btRemove.setSafeOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentranhtuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            tttViewModel?.unfavComic(comic = comic)
        }
    }

    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->
            vm.listComicFavActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                    if (isSuccess == true) {
                        val listComicFav = actionData.data
                        listComicFav?.let {
                            LUIUtil.printBeautyJson(o = it, textView = textView)
                        }
                    }
                }
            })

            vm.favComicLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                    if (isSuccess == true) {
                        val id = actionData.data
                        showLongInformation("Add success with id $id")

                        tttViewModel?.getListComicFav()
                    }
                }
            })

            vm.unfavComicLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                    if (isSuccess == true) {
                        val comic = actionData.data
                        showLongInformation("Delete success ${comic?.title}")

                        tttViewModel?.getListComicFav()
                    }
                }
            })
        }
    }
}
