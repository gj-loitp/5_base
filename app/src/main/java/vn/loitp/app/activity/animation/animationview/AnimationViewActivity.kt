package vn.loitp.app.activity.animation.animationview

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.activity_animation_view.*
import vn.loitp.app.R
import java.util.*

@LogTag("AnimationViewActivity")
@IsFullScreen(false)
class AnimationViewActivity : BaseFontActivity() {
    private var listAnim: List<Techniques> = ArrayList()
    private var listString: Array<String?>? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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

    private fun showDialogSelectAnim() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose:")
        builder.setItems(listString) { _, position ->
            if (tvGuide.visibility != View.VISIBLE) {
                tvGuide.visibility = View.VISIBLE
            }
            LUIUtil.setDelay(mls = 500, runnable = Runnable {
                LAnimationUtil.play(view = tvAnim, techniques = listAnim[position], duration = 1_000)
            })
        }
        val dialog = builder.create()
        dialog.show()
    }
}
