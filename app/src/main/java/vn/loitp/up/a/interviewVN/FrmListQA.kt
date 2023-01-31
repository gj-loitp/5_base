package vn.loitp.up.a.interviewVN

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.LogTag
import com.loitp.core.ext.getPkgFromGGDrive
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.f_interview_vn_iq_list_qa.*
import kotlinx.android.synthetic.main.i_movie_list.*
import vn.loitp.R
import vn.loitp.a.demo.fragmentFlow.BaseFragmentFlow
import vn.loitp.up.a.interviewVN.adt.QAAdapter

//https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
@LogTag("FrmListQA")
class FrmListQA(private val linkGGDrive: String?) : BaseFragmentFlow() {

//    companion object {
//        const val KEY_NEXT_LINK = "KEY_NEXT_LINK"
//    }

    private var concatAdapter = ConcatAdapter()
    private var qaAdapter = QAAdapter(
        listQA = ArrayList(),
        isShowADefault = false,
        isShowNextLink = false
    )

    override fun setLayoutResourceId(): Int {
        return R.layout.f_interview_vn_iq_list_qa
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
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                popThisFragment()
            })
            ivIconRight?.isVisible = false
            this.tvTitle?.text = FrmListQA::class.java.simpleName
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        qaAdapter.onClickRootListener = { qa, _ ->
            if (activity is InterviewVNIQActivity) {
                (activity as InterviewVNIQActivity).addFrm(FrmDetail(qa))
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
        getPkgFromGGDrive(
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
