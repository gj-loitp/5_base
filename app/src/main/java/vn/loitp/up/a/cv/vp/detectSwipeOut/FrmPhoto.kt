package vn.loitp.up.a.cv.vp.detectSwipeOut

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_photo.*
import vn.loitp.R

@LogTag("FrmPhoto")
class FrmPhoto : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_photo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val vpPhoto = bundle.getSerializable("vpphoto") as VPPhoto?
            vpPhoto?.let {
                relativeLayoutParent.setBackgroundColor(it.color)
                textView.text = it.string
            }
        }
    }
}
