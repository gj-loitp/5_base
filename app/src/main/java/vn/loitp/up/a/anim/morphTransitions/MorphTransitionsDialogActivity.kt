package vn.loitp.up.a.anim.morphTransitions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.loitp.anim.morphTransitions.FabTransform
import com.loitp.anim.morphTransitions.MorphTransform
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.AMorphTranstionsDialogBinding

@LogTag("DialogActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class MorphTransitionsDialogActivity : BaseActivityFont() {

    companion object {
        private const val EXTRA_TYPE = "type"
        const val TYPE_FAB = 1
        const val TYPE_BUTTON = 2

        fun newIntent(context: Context, type: Int): Intent {
            val intent = Intent(context, MorphTransitionsDialogActivity::class.java)
            intent.putExtra(EXTRA_TYPE, type)
            return intent
        }
    }

    private lateinit var binding: AMorphTranstionsDialogBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMorphTranstionsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rootView.setOnClickListener {
            ActivityCompat.finishAfterTransition(this@MorphTransitionsDialogActivity)
        }
        when (intent.getIntExtra(EXTRA_TYPE, -1)) {
            TYPE_FAB -> FabTransform.setup(this, binding.container)
            TYPE_BUTTON -> MorphTransform.setup(
                activity = this,
                target = binding.container,
                endColor = Color.WHITE,
                endCornerRadius = resources.getDimensionPixelSize(R.dimen.round_medium)
            )
        }
    }
}
