package vn.loitp.up.a.cv.progress.window

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.AProgressWindowBinding

@LogTag("WindowProgressActivity")
@IsFullScreen(false)
class WindowProgressActivity : BaseActivityFont() {

    private lateinit var binding: AProgressWindowBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = AProgressWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.wp10progressBar.setIndicatorRadius(5)
        binding.showWP7Btn.setOnClickListener {
            binding.wp7progressBar.showProgressBar()
        }
        binding.hideWP7Btn.setOnClickListener {
            binding.wp7progressBar.hideProgressBar()
        }
        binding.showWP10Btn.setOnClickListener {
            binding.wp10progressBar.showProgressBar()
        }
        binding.hideWP10Btn.setOnClickListener {
            binding.wp10progressBar.hideProgressBar()
        }
    }
}
