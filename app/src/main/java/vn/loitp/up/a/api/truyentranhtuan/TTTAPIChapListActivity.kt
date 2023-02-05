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
import vn.loitp.databinding.ATttApiChapListBinding

@LogTag("TTTAPIChapListActivity")
@IsFullScreen(false)
class TTTAPIChapListActivity : BaseActivityFont() {
    private lateinit var binding: ATttApiChapListBinding
    private var tttViewModel: TTTViewModel? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATttApiChapListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModels()
        tttViewModel?.getListChap(link = "http://truyentuan.com/one-piece/")
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
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
                    binding.progressBar.showProgress()
                } else {
                    binding.progressBar.hideProgress()
                    if (isSuccess == true) {
                        val tttChap = actionData.data
                        tttChap?.let {
                            binding.textView.printBeautyJson(o = it)
                        }
                        binding.tvTitle.text =
                            "Chap truyện One Piece - size: " + tttChap?.chaps?.chap?.size
                    }
                }
            }
        }
    }
}
