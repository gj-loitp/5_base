package vn.loitp.up.a.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.printBeautyJson
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showProgress
import com.loitp.core.helper.ttt.viewmodel.TTTViewModel
import vn.loitp.databinding.ATttApiPageListBinding

@LogTag("TTTAPIPageListActivity")
@IsFullScreen(false)
class TTTAPIPageListActivity : BaseActivityFont() {
    private lateinit var binding: ATttApiPageListBinding
    private var tttViewModel: TTTViewModel? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATttApiPageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModels()
        tttViewModel?.getListPage(link = "http://truyentuan.com/one-piece-chuong-69/")
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = TTTAPIPageListActivity::class.java.simpleName
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
                    binding.progressBar.showProgress()
                } else {
                    binding.progressBar.hideProgress()
                    if (isSuccess == true) {
                        val listPage = actionData.data
                        listPage?.let {
                            binding.textView.printBeautyJson(o = it)
                        }
                        binding.tvTitle.text =
                            "Danh sách page trong chap 69 - size: " + listPage?.size
                    }
                }
            }
        }
    }

}
