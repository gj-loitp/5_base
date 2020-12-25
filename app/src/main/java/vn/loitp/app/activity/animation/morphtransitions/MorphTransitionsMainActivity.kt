package vn.loitp.app.activity.animation.morphtransitions

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.animation.morphtransitions.FabTransform
import com.animation.morphtransitions.MorphTransform
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_morph_transitions_main.*
import vn.loitp.app.R

@LogTag("MainActivity")
@IsFullScreen(false)
class MorphTransitionsMainActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_morph_transitions_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        toolbar.setTitle(R.string.app_name)

        fab.setSafeOnClickListener {
            val intent: Intent = if (switchFullScreen.isChecked) {
                Intent(this@MorphTransitionsMainActivity, MorphTransitionsFullScreenActivity::class.java)
            } else {
                MorphTransitionsDialogActivity.newIntent(this@MorphTransitionsMainActivity, MorphTransitionsDialogActivity.TYPE_FAB)
            }
            if (Build.VERSION.SDK_INT >= 21) {
                FabTransform.addExtras(
                        intent,
                        ContextCompat.getColor(this@MorphTransitionsMainActivity, R.color.colorAccent),
                        R.drawable.ic_account_circle_black_48dp
                )
                val options = ActivityOptions.makeSceneTransitionAnimation(this@MorphTransitionsMainActivity, fab, getString(R.string.transition_morph))
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
                overridePendingTransition(R.anim.anim_morph_transitions_fade_in, R.anim.anim_morph_transitions_do_nothing)
            }
        }
        button.setSafeOnClickListener {
            val intent = MorphTransitionsDialogActivity.newIntent(this@MorphTransitionsMainActivity, MorphTransitionsDialogActivity.TYPE_BUTTON)
            if (Build.VERSION.SDK_INT >= 21) {
                MorphTransform.addExtras(
                        intent,
                        ContextCompat.getColor(this@MorphTransitionsMainActivity, R.color.colorAccent),
                        resources.getDimensionPixelSize(R.dimen.round_medium)
                )
                val options = ActivityOptions.makeSceneTransitionAnimation(
                        this@MorphTransitionsMainActivity,
                        button,
                        getString(R.string.transition_morph)
                )
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
                overridePendingTransition(R.anim.anim_morph_transitions_fade_in, R.anim.anim_morph_transitions_do_nothing)
            }
        }
    }
}
