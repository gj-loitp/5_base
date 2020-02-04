package vn.loitp.app.activity.animation.confetti

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.views.animation.confetti.CommonConfetti
import com.views.animation.confetti.ConfettiManager
import kotlinx.android.synthetic.main.activity_falling_confetti_from_top.*
import vn.loitp.app.R
import java.util.*

//https://github.com/jinatonic/confetti
class FallingConfettiFromTopActivity : BaseFontActivity(), View.OnClickListener {
    private var goldDark: Int = 0
    private var goldMed: Int = 0
    private var gold: Int = 0
    private var goldLight: Int = 0
    private lateinit var colors: IntArray
    private val activeConfettiManagers = ArrayList<ConfettiManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.generate_confetti_once_btn).setOnClickListener(this)
        findViewById<View>(R.id.generate_confetti_stream_btn).setOnClickListener(this)
        findViewById<View>(R.id.generate_confetti_infinite_btn).setOnClickListener(this)
        findViewById<View>(R.id.generate_confetti_stop_btn).setOnClickListener(this)

        goldDark = ContextCompat.getColor(activity, R.color.gold_dark)
        goldMed = ContextCompat.getColor(activity, R.color.gold_med)
        gold = ContextCompat.getColor(activity, R.color.gold)
        goldLight = ContextCompat.getColor(activity, R.color.gold_light)
        colors = intArrayOf(goldDark, goldMed, gold, goldLight)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_falling_confetti_from_top
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.generate_confetti_once_btn -> activeConfettiManagers.add(generateOnce())
            R.id.generate_confetti_stream_btn -> activeConfettiManagers.add(generateStream())
            R.id.generate_confetti_infinite_btn -> activeConfettiManagers.add(generateInfinite())
            else -> {
                for (confettiManager in activeConfettiManagers) {
                    confettiManager.terminate()
                }
                activeConfettiManagers.clear()
            }
        }
    }

    private fun generateOnce(): ConfettiManager {
        return CommonConfetti.rainingConfetti(rootView, colors)
                .oneShot()
    }

    private fun generateStream(): ConfettiManager {
        return CommonConfetti.rainingConfetti(rootView, colors)
                .stream(3000)
    }

    private fun generateInfinite(): ConfettiManager {
        return CommonConfetti.rainingConfetti(rootView, colors)
                .infinite()
    }
}
