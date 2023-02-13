package vn.loitp.up.a.interviewVN

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.LogTag
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.model.QA
import kotlinx.android.synthetic.main.f_interview_vn_iq_detail.*
import vn.loitp.R
import vn.loitp.up.a.demo.fragmentFlow.BaseFragmentFlow

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmDetail")
class FrmDetail(private val qa: QA) : BaseFragmentFlow() {
    override fun setLayoutResourceId(): Int {
        return R.layout.f_interview_vn_iq_detail
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
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                popThisFragment()
            })
            ivIconRight?.isVisible = false
            this.tvTitle?.text = FrmDetail::class.java.simpleName
        }
        if (qa.ivA.isEmpty()) {
            ivQ.isVisible = false
        } else {
            ivQ.isVisible = true
            ivQ.loadGlide(
                any = qa.ivQ,
            )
        }
        if (qa.ivA.isEmpty()) {
            ivA.isVisible = false
        } else {
            ivA.isVisible = true
            ivA.loadGlide(
                any = qa.ivA,
            )
        }

        tvQ.text = qa.q
        tvA.text = qa.a
    }

}
