package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_page_list.*
import vn.loitp.app.R

@LogTag("TTTAPIPageListActivity")
@IsFullScreen(false)
class TTTAPIPageListActivity : BaseFontActivity() {

    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return (R.layout.activity_api_ttt_page_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModels()
        tttViewModel?.getListPage(link = "http://truyentranhtuan.com/one-piece-chuong-69/")
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->

            vm.listPageActionLiveData.observe(this, { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()
                    if (isSuccess == true) {
                        val listPage = actionData.data
                        listPage?.let {
                            LUIUtil.printBeautyJson(o = it, textView = textView)
                        }
                        tvTitle.text = "Danh sách page trong chap 69 - size: " + listPage?.size
                    }
                }
            })
        }
    }

}
