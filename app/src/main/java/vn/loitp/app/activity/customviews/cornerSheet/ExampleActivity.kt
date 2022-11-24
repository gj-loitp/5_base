package vn.loitp.app.activity.customviews.cornerSheet

import android.content.Intent
import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LSocialUtil
import kotlinx.android.synthetic.main.example_activity.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.cornerSheet.support.ShopActivity

@LogTag("ExampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ExampleActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.example_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        btGithub.setSafeOnClickListener {
            LSocialUtil.openUrlInBrowser(
                context = this,
                url = "https://github.com/HeyAlex/CornerSheet"
            )
        }
    }
}
