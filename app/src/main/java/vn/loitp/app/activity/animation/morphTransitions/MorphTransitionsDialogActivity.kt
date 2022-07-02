package vn.loitp.app.activity.animation.morphTransitions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.loitpcore.animation.morphtransitions.FabTransform
import com.loitpcore.animation.morphtransitions.MorphTransform
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsSwipeActivity
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_morph_transtions_dialog.*
import vn.loitp.app.R

@LogTag("DialogActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class MorphTransitionsDialogActivity : BaseFontActivity() {

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

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_morph_transtions_dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootView.setOnClickListener {
            ActivityCompat.finishAfterTransition(this@MorphTransitionsDialogActivity)
        }
        when (intent.getIntExtra(EXTRA_TYPE, -1)) {
            TYPE_FAB -> FabTransform.setup(this, container)
            TYPE_BUTTON -> MorphTransform.setup(
                this,
                container,
                Color.WHITE,
                resources.getDimensionPixelSize(R.dimen.round_medium)
            )
        }
    }
}
