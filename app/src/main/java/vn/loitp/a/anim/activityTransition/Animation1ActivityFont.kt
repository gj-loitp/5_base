package vn.loitp.a.anim.activityTransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.*
import com.loitp.core.utilities.LUIUtil
import com.loitp.data.ActivityData
import kotlinx.android.synthetic.main.a_animation_1.*
import vn.loitp.R

@LogTag("Animation1Activity")
@IsFullScreen(false)
class Animation1ActivityFont : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_animation_1
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = Animation1ActivityFont::class.java.simpleName
        }
        btNoAnim.setOnClickListener(this)
        btSystemDefault.setOnClickListener(this)
        btSlideLeft.setOnClickListener(this)
        btSlideRight.setOnClickListener(this)
        btSlideDown.setOnClickListener(this)
        btSlideUp.setOnClickListener(this)
        btFade.setOnClickListener(this)
        btZoom.setOnClickListener(this)
        btWindMill.setOnClickListener(this)
        btDiagonal.setOnClickListener(this)
        btSpin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent = Intent(this, Animation2ActivityFont::class.java)
        startActivity(intent)
        when (v) {
            btNoAnim -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
                this.transActivityNoAnimation()
            }
            btSystemDefault ->
                ActivityData.instance.type =
                    Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
            btSlideLeft -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT
                this.slideLeft()
            }
            btSlideRight -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT
                this.slideRight()
            }
            btSlideDown -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN
                this.slideDown()
            }
            btSlideUp -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP
                this.slideUp()
            }
            btFade -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_FADE
                this.fade()
            }
            btZoom -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_ZOOM
                this.zoom()
            }
            btWindMill -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL
                this.windmill()
            }
            btDiagonal -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL
                this.diagonal()
            }
            btSpin -> {
                ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SPIN
                this.spin()
            }
        }
    }
}
