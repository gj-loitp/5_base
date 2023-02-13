package vn.loitp.up.a.anim.activityTransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.*
import com.loitp.data.ActivityData
import vn.loitp.R
import vn.loitp.databinding.AAnimation1Binding

@LogTag("Animation1Activity")
@IsFullScreen(false)
class Animation1Activity : BaseActivityFont(), OnClickListener {

    private lateinit var binding: AAnimation1Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimation1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = Animation1Activity::class.java.simpleName
        }
        binding.btNoAnim.setOnClickListener(this)
        binding.btSystemDefault.setOnClickListener(this)
        binding.btSlideLeft.setOnClickListener(this)
        binding.btSlideRight.setOnClickListener(this)
        binding.btSlideDown.setOnClickListener(this)
        binding.btSlideUp.setOnClickListener(this)
        binding.btFade.setOnClickListener(this)
        binding.btZoom.setOnClickListener(this)
        binding.btWindMill.setOnClickListener(this)
        binding.btDiagonal.setOnClickListener(this)
        binding.btSpin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent = Intent(this, Animation2Activity::class.java)
        startActivity(intent)
        when (v) {
            binding.btNoAnim -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
                this.transActivityNoAnimation()
            }
            binding.btSystemDefault ->
                ActivityData.instance.type =
                    TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
            binding.btSlideLeft -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT
                this.slideLeft()
            }
            binding.btSlideRight -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT
                this.slideRight()
            }
            binding.btSlideDown -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN
                this.slideDown()
            }
            binding.btSlideUp -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SLIDE_UP
                this.slideUp()
            }
            binding.btFade -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_FADE
                this.fade()
            }
            binding.btZoom -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_ZOOM
                this.zoom()
            }
            binding.btWindMill -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_WINDMILL
                this.windmill()
            }
            binding.btDiagonal -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_DIAGONAL
                this.diagonal()
            }
            binding.btSpin -> {
                ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SPIN
                this.spin()
            }
        }
    }
}
