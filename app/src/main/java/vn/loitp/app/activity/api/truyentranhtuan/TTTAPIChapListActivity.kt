package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.helper.ttt.viewmodel.TTTViewModel
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_ttt_api_chap_list.*
import vn.loitp.app.R

@LogTag("TTTAPIChapListActivity")
@IsFullScreen(false)
class TTTAPIChapListActivity : BaseFontActivity() {

    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ttt_api_chap_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
        tttViewModel?.getListChap(link = "http://truyentuan.com/one-piece/")
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
            this.tvTitle?.text = TTTAPIChapListActivity::class.java.simpleName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->

            vm.tttChapActionLiveData.observe(this) { actionData ->
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
            }
        }
    }
}
