package vn.loitp.app.activity.function.viewDragHelper

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_func_view_drag_helper.*
import vn.loitp.app.R

@LogTag("ViewDragHelperActivity")
@IsFullScreen(false)
class ViewDragHelperActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_drag_helper
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
            this.tvTitle?.text = ViewDragHelperActivity::class.java.simpleName
        }
    }
}
