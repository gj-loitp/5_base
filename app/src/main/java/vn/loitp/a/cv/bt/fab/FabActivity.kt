package vn.loitp.a.cv.bt.fab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_fab.*
import vn.loitp.R

@LogTag("FabActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class FabActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fab
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
