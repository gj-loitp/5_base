package vn.loitp.a.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.printBeautyJson
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.ttt.viewmodel.TTTViewModel
import com.loitp.core.utilities.LDialogUtil
import kotlinx.android.synthetic.main.a_ttt_api_page_list.*
import vn.loitp.R

@LogTag("TTTAPIPageListActivity")
@IsFullScreen(false)
class TTTAPIPageListActivityFont : BaseActivityFont() {

    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return (R.layout.a_ttt_api_page_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
        tttViewModel?.getListPage(link = "http://truyentuan.com/one-piece-chuong-69/")
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TTTAPIPageListActivityFont::class.java.simpleName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->

            vm.listPageActionLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                    if (isSuccess == true) {
                        val listPage = actionData.data
                        listPage?.let {
                            textView.printBeautyJson(o = it)
                        }
                        tvTitle.text = "Danh sách page trong chap 69 - size: " + listPage?.size
                    }
                }
            }
        }
    }

}
