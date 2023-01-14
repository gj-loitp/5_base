package vn.loitp.a.cv.iv.coil

import android.os.Bundle
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_coil.*
import vn.loitp.R
import vn.loitp.common.Constants.Companion.URL_IMG_2

@LogTag("CoilActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CoilActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_coil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/coil-kt/coil"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CoilActivityFont::class.java.simpleName
        }

        iv0.load(URL_IMG_1)
        iv1.load(URL_IMG_2) {
            crossfade(true)
            placeholder(R.drawable.circle)
            transformations(CircleCropTransformation())
        }
        iv2.load(URL_IMG_2) {
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
