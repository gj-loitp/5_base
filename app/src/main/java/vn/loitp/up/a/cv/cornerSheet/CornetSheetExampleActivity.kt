package vn.loitp.up.a.cv.cornerSheet

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
import vn.loitp.databinding.AExampleBinding
import vn.loitp.up.a.cv.cornerSheet.sp.ShopActivityFont
import vn.loitp.up.app.EmptyActivity

@LogTag("ExampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CornetSheetExampleActivity : BaseActivityFont() {
    private lateinit var binding: AExampleBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        this@CornetSheetExampleActivity.openUrlInBrowser(
                            url = "https://github.com/HeyAlex/CornerSheet"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }

        binding.main.setOnClickListener {
            launchActivity(BehaviorSampleActivity::class.java)
        }

        binding.supportSample.setOnClickListener {
            launchActivity(ShopActivityFont::class.java)
        }
    }
}
