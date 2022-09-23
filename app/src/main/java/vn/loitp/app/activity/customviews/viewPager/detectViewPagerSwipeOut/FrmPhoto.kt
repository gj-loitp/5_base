package vn.loitp.app.activity.customviews.viewPager.detectViewPagerSwipeOut

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_photo.*
import vn.loitp.app.R

@LogTag("FrmPhoto")
class FrmPhoto : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_photo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            //TODO fix getSerializable
            val vpPhoto = bundle.getSerializable("vpphoto") as VPPhoto?
            vpPhoto?.let {
                relativeLayoutParent.setBackgroundColor(it.color)
                textView.text = it.string
            }
        }
    }
}
