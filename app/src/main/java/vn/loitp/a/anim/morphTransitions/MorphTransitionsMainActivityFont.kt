package vn.loitp.a.anim.morphTransitions

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.anim.morphTransitions.MorphTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.a_morph_transitions_main.*
import vn.loitp.R

@LogTag("MainActivity")
@IsFullScreen(false)
class MorphTransitionsMainActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_morph_transitions_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        toolbar.setTitle(R.string.app_name)

        fab.setSafeOnClickListener {
            val intent: Intent = if (switchFullScreen.isChecked) {
                Intent(
                    this@MorphTransitionsMainActivityFont,
                    MorphTransitionsFullScreenActivityFont::class.java
                )
            } else {
                MorphTransitionsDialogActivityFont.newIntent(
                    this@MorphTransitionsMainActivityFont,
                    MorphTransitionsDialogActivityFont.TYPE_FAB
                )
            }
            FabTransform.addExtras(
                intent,
                ContextCompat.getColor(this@MorphTransitionsMainActivityFont, R.color.colorAccent),
                R.drawable.ic_account_circle_black_48dp,
            )
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@MorphTransitionsMainActivityFont,
                fab,
                getString(R.string.transition_morph)
            )
            startActivity(intent, options.toBundle())
        }
        button.setSafeOnClickListener {
            show(it)
        }
        button1.setSafeOnClickListener {
            show(it)
        }
        button2.setSafeOnClickListener {
            show(it)
        }
        button3.setSafeOnClickListener {
            show(it)
        }
    }

    private fun show(view: View) {
        val intent = MorphTransitionsDialogActivityFont.newIntent(
            this@MorphTransitionsMainActivityFont,
            MorphTransitionsDialogActivityFont.TYPE_BUTTON
        )
        MorphTransform.addExtras(
            intent,
            ContextCompat.getColor(this@MorphTransitionsMainActivityFont, R.color.colorAccent),
            resources.getDimensionPixelSize(R.dimen.round_medium)
        )
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this@MorphTransitionsMainActivityFont,
            view,
            getString(R.string.transition_morph)
        )
        startActivity(intent, options.toBundle())
    }
}
