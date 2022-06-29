package vn.loitp.app.activity.animation.animationview

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LAnimationUtil
import com.loitpcore.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.activity_animation_view.*
import vn.loitp.app.R
import java.util.* // ktlint-disable no-wildcard-imports

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
            LUIUtil.setDelay(
                mls = 500,
                runnable = {
                    LAnimationUtil.play(
                        view = tvAnim,
                        techniques = listAnim[position],
                        duration = 1_000
                    )
                }
            )
        }
        val dialog = builder.create()
        dialog.show()
    }
}
