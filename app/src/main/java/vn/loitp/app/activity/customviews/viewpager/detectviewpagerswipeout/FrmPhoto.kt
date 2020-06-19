package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout

import android.os.Bundle
import android.view.View
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_photo.*
import vn.loitp.app.R

class FrmPhoto : BaseFragment() {

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

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_photo
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }
}
