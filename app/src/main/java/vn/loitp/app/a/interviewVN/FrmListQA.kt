package vn.loitp.app.a.interviewVN

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.LogTag
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_package.*
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_qa.*
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_qa.lActionBar
import kotlinx.android.synthetic.main.frm_interview_vn_iq_list_qa.recyclerView
import kotlinx.android.synthetic.main.view_movie_list.*
import vn.loitp.R
import vn.loitp.app.a.demo.fragmentFlow.BaseFragmentFlow
import vn.loitp.app.a.interviewVN.adt.QAAdapter

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmListQA")
class FrmListQA(private val linkGGDrive: String?) : BaseFragmentFlow() {

    companion object {
        const val KEY_NEXT_LINK = "KEY_NEXT_LINK"
    }

    private var concatAdapter = ConcatAdapter()
    private var qaAdapter = QAAdapter(
        listQA = ArrayList(),
        isShowADefault = false,
        isShowNextLink = false
    )

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_interview_vn_iq_list_qa
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupData()
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
            this.tvTitle?.text = FrmListQA::class.java.simpleName
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        qaAdapter.onClickRootListener = { qa, _ ->
            if (activity is InterviewVNIQActivity) {
                (activity as InterviewVNIQActivity).addFragment(FrmDetail(qa))
            }
        }
        concatAdapter.addAdapter(qaAdapter)
        recyclerView.adapter = concatAdapter
    }

    private fun setupData() {
        if (linkGGDrive.isNullOrEmpty()) {
            return
        }
        showDialogProgress()
        LStoreUtil.getPkgFromGGDrive(
            linkGGDriveSetting = linkGGDrive,
            onGGFailure = { _, e ->
                hideDialogProgress()
                showShortError(e.toString())
            },
            onGGResponse = { pkg ->
                activity?.runOnUiThread {
                    hideDialogProgress()
                    val list = pkg?.data
                    if (list.isNullOrEmpty()) {
                        showShortError(getString(R.string.no_data_eng))
                    } else {
                        qaAdapter.setData(list)
                    }
                }
            }
        )
    }
}
