package vn.loitp.a.cv.cornerSheet

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_example.*
import vn.loitp.R
import vn.loitp.a.cv.cornerSheet.sp.ShopActivityFont
import vn.loitp.app.EmptyActivityFont

@LogTag("ExampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CornetSheetExampleActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_example
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        this@CornetSheetExampleActivityFont.openUrlInBrowser(
                            url = "https://github.com/HeyAlex/CornerSheet"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivityFont::class.java.simpleName
        }

        main.setOnClickListener {
            val intent = Intent(this, BehaviorSampleActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }

        support_sample.setOnClickListener {
            val intent = Intent(this, ShopActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
    }
}
