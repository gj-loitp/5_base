package vn.loitp.up.a.cv.iv.blurry

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import jp.wasabeef.blurry.Blurry
import vn.loitp.R
import vn.loitp.databinding.ABlurryBinding
import vn.loitp.up.app.EmptyActivity

@LogTag("BlurryActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class BlurryActivity : BaseActivityFont() {

    private lateinit var binding: ABlurryBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABlurryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/wasabeef/Blurry"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }

        Blurry.with(this)
            .radius(25)
            .sampling(4)
            .color(Color.argb(66, 255, 255, 0))
            .capture(findViewById(R.id.iv))
            .getAsync {
                binding.iv.setImageDrawable(BitmapDrawable(resources, it))
            }
    }

}
