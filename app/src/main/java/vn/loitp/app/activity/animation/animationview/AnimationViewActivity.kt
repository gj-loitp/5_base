package vn.loitp.app.activity.animation.animationview

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.activity_animation_view.*
import vn.loitp.app.R
import java.util.*

class AnimationViewActivity : BaseFontActivity() {
    private var listAnim: List<Techniques> = ArrayList()
    private var listString: Array<String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAnimList()
        btSelectAnim.setOnClickListener {
            showDialogSelectAnim()
        }
    }

    private fun setupAnimList() {
        listAnim = ArrayList(EnumSet.allOf(Techniques::class.java))
        listString = arrayOfNulls(listAnim.size)
        listString?.let {
            for (i in listAnim.indices) {
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
        builder.setItems(listString) { _, position ->
            if (tvGuide.visibility != View.VISIBLE) {
                tvGuide.visibility = View.VISIBLE
            }
            LUIUtil.setDelay(mls = 1000, runnable = Runnable {
                LAnimationUtil.play(view = tvAnim, techniques = listAnim[position])
            })
        }
        val dialog = builder.create()
        dialog.show()
    }
}
