package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_chap_list.*
import kotlinx.android.synthetic.main.activity_api_ttt_chap_list.progressBar
import kotlinx.android.synthetic.main.activity_api_ttt_chap_list.textView
import kotlinx.android.synthetic.main.activity_api_ttt_chap_list.tvTitle
import kotlinx.android.synthetic.main.activity_api_ttt_comic_list.*
import vn.loitp.app.R

@LogTag("TTTAPIChapListActivity")
@IsFullScreen(false)
class TTTAPIChapListActivity : BaseFontActivity() {

    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_chap_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModels()
        tttViewModel?.getListChap(link = "http://truyentranhtuan.com/one-piece/")
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->

            vm.tttChapActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                    if (isSuccess == true) {
                        val tttChap = actionData.data
                        tttChap?.let {
                            LUIUtil.printBeautyJson(o = it, textView = textView)
                        }
                        tvTitle.text = "Chap truyện One Piece - size: " + tttChap?.chaps?.chap?.size
                    }
                }
            })
        }
    }

}
