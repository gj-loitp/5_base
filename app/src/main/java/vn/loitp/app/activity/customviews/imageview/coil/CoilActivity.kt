package vn.loitp.app.activity.customviews.imageview.coil

import android.os.Bundle
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_coil.*
import vn.loitp.app.R

@LogTag("CoilActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CoilActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/coil-kt/coil"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CoilActivity::class.java.simpleName
        }

        iv0.load(Constants.URL_IMG_1)
        iv1.load(Constants.URL_IMG_2) {
            crossfade(true)
            placeholder(R.drawable.circle)
            transformations(CircleCropTransformation())
        }
        iv2.load(Constants.URL_IMG_2) {
            crossfade(true)
            placeholder(R.drawable.circle)
            transformations(
                RoundedCornersTransformation(
                    topLeft = 45f,
                    topRight = 0f,
                    bottomLeft = 0f,
                    bottomRight = 45f
                )
            )
        }
    }
}
