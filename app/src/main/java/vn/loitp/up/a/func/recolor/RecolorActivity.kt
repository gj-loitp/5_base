package vn.loitp.up.a.func.recolor

import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import com.simmorsal.recolor_project.ReColor
import vn.loitp.R
import vn.loitp.databinding.AFuncRecolorBinding

// https://github.com/SIMMORSAL/ReColor

@LogTag("RecolorActivity")
@IsFullScreen(false)
class RecolorActivity : BaseActivityFont() {
    private lateinit var binding: AFuncRecolorBinding

    private var imageViewColorSetNumber = 0
    private var textViewColorSetNumber = 0
    private var isStatusBarColorChanged = false
    private var isNavigationBarColorChanged = false

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncRecolorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        logAListOfColors()
    }

    private fun logAListOfColors() {
        val colorList = ReColor(this).getColorIntArray("E91E63", "1E88E5", 20)
        for (i in colorList.indices) {
            logD(colorList[i].toString())
        }
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecolorActivity::class.java.simpleName
        }

        // changing space_invaders' color
        binding.imageView.setOnClickListener {
            when (imageViewColorSetNumber) {
                0 -> {
                    binding.imageView.recolor(
                        startColor = Color.RED,
                        endColor = Color.YELLOW,
                        duration = 300
                    )
                    imageViewColorSetNumber = 1
                }
                1 -> {
                    binding.imageView.recolor(
                        startColor = Color.YELLOW,
                        endColor = Color.GREEN,
                        duration = 300
                    )
                    imageViewColorSetNumber = 2
                }
                2 -> {
                    binding.imageView.recolor(
                        startColor = Color.GREEN,
                        endColor = Color.BLUE,
                        duration = 300
                    )
                    imageViewColorSetNumber = 3
                }
                3 -> {
                    binding.imageView.recolor(
                        startColor = Color.BLUE,
                        endColor = Color.RED,
                        duration = 300
                    )
                    imageViewColorSetNumber = 0
                }
            }
        }

        // changing the color on the text view
        binding.textView.setOnClickListener {
            when (textViewColorSetNumber) {
                0 -> {
                    binding.textView.recolor(
                        startColor = Color.RED,
                        endColor = Color.YELLOW,
                        duration = 300
                    )
                    textViewColorSetNumber = 1
                }
                1 -> {
                    binding.textView.recolor(
                        startColor = Color.YELLOW,
                        endColor = Color.BLACK,
                        duration = 300
                    )
                    textViewColorSetNumber = 2
                }
                2 -> {
                    binding.textView.recolor(
                        startColor = Color.BLACK,
                        endColor = Color.WHITE,
                        duration = 300
                    )
                    textViewColorSetNumber = 3
                }
                3 -> {
                    binding.textView.recolor(
                        startColor = Color.WHITE,
                        endColor = Color.RED,
                        duration = 300
                    )
                    textViewColorSetNumber = 0
                }
            }
        }
        binding.linReColorBackground.setOnClickListener {
            it.recolor(
                startColor = Color.RED,
                endColor = Color.GREEN,
                duration = 300,
                onReColorFinish = {
                    showSnackBarInfor("onReColorFinish")
                }
            )
        }

        binding.theCardView.setOnClickListener {
            it.recolor(
                startColor = Color.RED,
                endColor = Color.GREEN,
                duration = 300,
                onReColorFinish = {
                    showSnackBarInfor("onReColorFinish")
                }
            )
        }

        // changing statusBar color
        binding.linRecolorStatusBar.setOnClickListener {
            isStatusBarColorChanged = if (!isStatusBarColorChanged) {
                // if starting color is null, color will be automatically retrieved from status bar
                // same is true for navigation bar
                this.recolorStatusBar(
                    startColor = null,
                    endColor = Color.RED,
                    duration = 300
                )
                !isStatusBarColorChanged
            } else {
                this.recolorStatusBar(
                    startColor = null,
                    endColor = Color.YELLOW,
                    duration = 300
                )
                !isStatusBarColorChanged
            }
        }

        // changing navigationBar color
        binding.linRecolorNavigationBar.setOnClickListener {
            isNavigationBarColorChanged = if (!isNavigationBarColorChanged) {
                this.recolorNavigationBar(
                    startColor = null,
                    endColor = Color.YELLOW,
                    duration = 300
                )
                !isNavigationBarColorChanged
            } else {
                this.recolorNavigationBar(
                    startColor = null,
                    endColor = Color.GREEN,
                    duration = 300
                )
                !isNavigationBarColorChanged
            }
        }

        // changing both statusBar and navigationBar colors
        binding.linRecolorBoth.setOnClickListener {
            isStatusBarColorChanged = if (!isStatusBarColorChanged) {
                this.recolorStatusBar(
                    startColor = null,
                    endColor = Color.YELLOW,
                    duration = 300
                )
                !isStatusBarColorChanged
            } else {
                this.recolorStatusBar(
                    startColor = null,
                    endColor = Color.RED,
                    duration = 300
                )
                !isStatusBarColorChanged
            }
            isNavigationBarColorChanged = if (!isNavigationBarColorChanged) {
                this.recolorNavigationBar(
                    startColor = null,
                    endColor = Color.YELLOW,
                    duration = 300
                )
                !isNavigationBarColorChanged
            } else {
                this.recolorNavigationBar(
                    startColor = null,
                    endColor = Color.RED,
                    duration = 300
                )
                !isNavigationBarColorChanged
            }
        }

        // pulsing statusBar to an orange color for 2 times
        binding.linPulseStatusBar.setOnClickListener {
            this.recolorStatusBarPulse(
                pulseColor = Color.RED,
                pulseSpeed = 200,
                pulseCount = 2
            )
        }

        // pulsing navigationBar to an orange color for 2 times
        binding.linPulseNavigationBar.setOnClickListener {
            this.recolorNavigationBarPulse(
                pulseColor = Color.RED,
                pulseSpeed = 200,
                pulseCount = 2
            )
        }

        // pulsing both colors' to an orange color for 5 times really fast
        binding.linPulseBoth.setOnClickListener {
            this.recolorStatusBarPulse(
                pulseColor = Color.RED,
                pulseSpeed = 200,
                pulseCount = 2
            )
            this.recolorNavigationBarPulse(
                pulseColor = Color.RED,
                pulseSpeed = 200,
                pulseCount = 2
            )
        }
        binding.ib.setOnClickListener {
            it.recolor(
                startColor = Color.WHITE,
                endColor = Color.RED,
                duration = 300
            )
        }
    }
}
