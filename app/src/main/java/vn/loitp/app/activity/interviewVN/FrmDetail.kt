package vn.loitp.app.activity.interviewVN

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.model.data.QA
import kotlinx.android.synthetic.main.frm_interview_vn_iq_detail.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.fragmentFlow.BaseFragmentFlow

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmDetail")
class FrmDetail(private val qa: QA) : BaseFragmentFlow() {
    override fun setLayoutResourceId(): Int {
        return R.layout.frm_interview_vn_iq_detail
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onBackPressed(): Boolean {
        popThisFragment()
        return true
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                popThisFragment()
            })
            ivIconRight?.isVisible = false
            this.tvTitle?.text = FrmDetail::class.java.simpleName
        }
        if (qa.ivA.isEmpty()) {
            ivQ.isVisible = false
        } else {
            ivQ.isVisible = true
            LImageUtil.load(
                context = activity,
                any = qa.ivQ,
                imageView = ivQ
            )
        }
        if (qa.ivA.isEmpty()) {
            ivA.isVisible = false
        } else {
            ivA.isVisible = true
            LImageUtil.load(
                context = activity,
                any = qa.ivA,
                imageView = ivA
            )
        }

        tvQ.text = qa.q
        tvA.text = qa.a
    }

}
