package vn.loitp.a.cv.cornerSheet

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_example.*
import vn.loitp.R
import vn.loitp.a.EmptyActivity
import vn.loitp.a.cv.cornerSheet.sp.ShopActivity

@LogTag("ExampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CornetSheetExampleActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_example
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = this@CornetSheetExampleActivity,
                            url = "https://github.com/HeyAlex/CornerSheet"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }

        main.setOnClickListener {
            val intent = Intent(this, BehaviorSampleActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }

        support_sample.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
