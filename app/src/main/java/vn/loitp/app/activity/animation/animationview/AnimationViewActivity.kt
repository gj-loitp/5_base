package vn.loitp.app.activity.animation.animationview

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import vn.loitp.app.R
import java.util.*

class AnimationViewActivity : BaseFontActivity() {
    private var tvAnim: TextView? = null
    private var tvGuide: TextView? = null

    private var listAnim: List<Techniques> = ArrayList()
    private var arr: Array<String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvAnim = findViewById(R.id.tv_anim)
        tvGuide = findViewById(R.id.tv_guide)
        val btSelectAnim = findViewById<Button>(R.id.bt_select_anim)

        setupAnimList()

        btSelectAnim.setOnClickListener { _ -> showDialogSelectAnim() }
    }

    private fun setupAnimList() {
        listAnim = ArrayList(EnumSet.allOf(Techniques::class.java))
        arr = arrayOfNulls(listAnim.size)
        arr?.let {
            for (i in listAnim.indices) {
                //LLog.d(TAG, result.get(i) + "");
                it[i] = listAnim[i].toString()
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_view
    }

    private fun showDialogSelectAnim() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Choose:")
        builder.setItems(arr) { _, position ->
            LLog.d(TAG, "onClick $position")
            if (tvGuide?.visibility != View.VISIBLE) {
                tvGuide?.visibility = View.VISIBLE
            }
            LUIUtil.setDelay(1000, Runnable {
                LAnimationUtil.play(tvAnim, listAnim[position])
            })
        }
        val dialog = builder.create()
        dialog.show()
    }
}
