package vn.loitp.app.activity.customviews.button.fab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_fab.*
import vn.loitp.app.R

@LogTag("FabActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class FabActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fab
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FabActivity::class.java.simpleName
        }
        btAiNone.setSafeOnClickListener {
            btAiNone.shrink(object : ExtendedFloatingActionButton.OnChangedCallback() {
                override fun onShrunken(extendedFab: ExtendedFloatingActionButton?) {
                    super.onShrunken(extendedFab)
                }

                override fun onExtended(extendedFab: ExtendedFloatingActionButton?) {
                    super.onExtended(extendedFab)
                }
            })
        }
        btAiNone2.shrink()
        btAiNone2.setSafeOnClickListener {
            btAiNone2.extend()
        }
    }
}