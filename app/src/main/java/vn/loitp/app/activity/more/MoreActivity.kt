package vn.loitp.app.activity.more

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.helper.more.FrmMore
import com.loitpcore.core.utilities.LScreenUtil
import vn.loitp.app.R

@LogTag("MoreActivity")
@IsFullScreen(false)
class MoreActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_more
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.addFragment(
            activity = this,
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = FrmMore(),
            isAddToBackStack = false
        )
    }
}
