package vn.loitp.app.activity.api.truyentranhtuan

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.helper.ttt.model.comic.Comic
import com.loitp.core.helper.ttt.viewmodel.TTTViewModel
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_ttt_api_fav_list.*
import vn.loitp.app.R

@LogTag("TTTAPIFavListActivity")
@IsFullScreen(false)
class TTTAPIFavListActivity : BaseFontActivity() {

    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ttt_api_fav_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModels()
        setupViews()

        tttViewModel?.getListComicFav()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TTTAPIFavListActivity::class.java.simpleName
        }
        btAdd.setSafeOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            comic.isFav = true
            tttViewModel?.favComic(comic = comic)
        }
        btRemove.setSafeOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            comic.isFav = false
            tttViewModel?.unfavComic(comic = comic)
        }
    }

    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->
            vm.listComicFavActionLiveData.observe(this) { actionData ->
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
            }

            vm.favComicLiveData.observe(this) { actionData ->
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
            }

            vm.unfavComicLiveData.observe(this) { actionData ->
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
            }
        }
    }
}
