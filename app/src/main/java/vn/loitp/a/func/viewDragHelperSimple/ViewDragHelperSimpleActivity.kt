package vn.loitp.a.func.viewDragHelperSimple

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_func_view_drag_helper_simple.*
import vn.loitp.R

@LogTag("ViewDragHelperSimpleActivity")
@IsFullScreen(false)
class ViewDragHelperSimpleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_view_drag_helper_simple
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
            this.tvTitle?.text = ViewDragHelperSimpleActivity::class.java.simpleName
        }
    }
}
