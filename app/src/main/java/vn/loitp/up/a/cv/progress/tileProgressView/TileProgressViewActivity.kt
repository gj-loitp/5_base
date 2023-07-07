package vn.loitp.up.a.cv.progress.tileProgressView

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATileProgressViewBinding

@LogTag("TileProgressViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TileProgressViewActivity : BaseActivityFont() {
    private lateinit var binding: ATileProgressViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATileProgressViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/iammert/TileProgressView"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TileProgressViewActivity::class.java.simpleName
        }

        binding.tileProgressView.setProgress(69f)
        binding.tileProgressView.setColor(Color.RED)
//        tileProgressView.setBackgroundColor(Color.GREEN)
//        tileProgressView.setLoadingColor(Color.YELLOW)
    }
}
