package vn.loitp.app.activity.animation.animationView

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LAnimationUtil
import com.loitpcore.core.utilities.LUIUtil
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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = AnimationViewActivity::class.java.simpleName
        }
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
