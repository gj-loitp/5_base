package vn.loitp.up.a.anim.morphTransitions

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
import vn.loitp.R
import vn.loitp.databinding.AMorphTransitionsMainBinding

@LogTag("MainActivity")
@IsFullScreen(false)
class MorphTransitionsMainActivity : BaseActivityFont() {
    private lateinit var binding: AMorphTransitionsMainBinding

//    override fun setLayoutResourceId(): Int {
//        return R.layout.a_morph_transitions_main
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMorphTransitionsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.toolbar.setTitle(R.string.app_name)

        binding.fab.setSafeOnClickListener {
            val intent: Intent = if (binding.switchFullScreen.isChecked) {
                Intent(
                    this@MorphTransitionsMainActivity,
                    MorphTransitionsFullScreenActivity::class.java
                )
            } else {
                MorphTransitionsDialogActivity.newIntent(
                    this@MorphTransitionsMainActivity,
                    MorphTransitionsDialogActivity.TYPE_FAB
                )
            }
            FabTransform.addExtras(
                intent,
                ContextCompat.getColor(this@MorphTransitionsMainActivity, R.color.colorAccent),
                com.loitp.R.drawable.ic_account_circle_black_48dp,
            )
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@MorphTransitionsMainActivity,
                binding.fab,
                getString(com.loitp.R.string.transition_morph)
            )
            startActivity(intent, options.toBundle())
        }
        binding.button.setSafeOnClickListener {
            show(it)
        }
        binding.button1.setSafeOnClickListener {
            show(it)
        }
        binding.button2.setSafeOnClickListener {
            show(it)
        }
        binding.button3.setSafeOnClickListener {
            show(it)
        }
    }

    private fun show(view: View) {
        val intent = MorphTransitionsDialogActivity.newIntent(
            this@MorphTransitionsMainActivity,
            MorphTransitionsDialogActivity.TYPE_BUTTON
        )
        MorphTransform.addExtras(
            intent,
            ContextCompat.getColor(this@MorphTransitionsMainActivity, R.color.colorAccent),
            resources.getDimensionPixelSize(com.loitp.R.dimen.round_medium)
        )
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this@MorphTransitionsMainActivity,
            view,
            getString(com.loitp.R.string.transition_morph)
        )
        startActivity(intent, options.toBundle())
    }
}
