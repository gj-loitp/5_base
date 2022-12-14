package vn.loitp.app.activity.function.viewDragHelperSimple

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_func_view_draghelper_simple.*
import vn.loitp.app.R

@LogTag("ViewDragHelperSimpleActivity")
@IsFullScreen(false)
class ViewDragHelperSimpleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_draghelper_simple
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
