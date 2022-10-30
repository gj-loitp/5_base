package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.frm_coroutine_get_list_user.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel

@LogTag("FrmGetListUser")
class FrmGetListUser : BaseFragment() {
    private var testViewModel: TestViewModel? = null
    private var userListAdapter: UserListAdapter? = null
    private var page = 1

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coroutine_get_list_user
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (frmRootView == null) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            frmRootView
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        setupViewModels()

        testViewModel?.let { tvm ->
            if (tvm.userTestListLiveData.value == null) {
                logD("tvm.userAction.value == null")
                testViewModel?.getUserTestListByPage(page = page, isRefresh = false)
            } else {
                logD("tvm.userAction.value != null")
            }
        }
    }

    private fun setupViews() {
        context?.let {
            userListAdapter = UserListAdapter(it, callback = { _, userTest ->
                val bundle = Bundle()
                bundle.putSerializable(FrmUser.KEY_USER, userTest)
                findNavController().navigate(R.id.action_frmGetListUser_to_frmUser, bundle)
            })
        }
        rvUserTest.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvUserTest.adapter = userListAdapter
        LUIUtil.setScrollChange(
            recyclerView = rvUserTest,
            onBottom = {
                logD("onBottom")
                page += 1
                testViewModel?.getUserTestListByPage(page = page, isRefresh = false)
            }
        )

        btBack.setSafeOnClickListener {
            logD("popBackStack")
            (activity as? BaseFontActivity)?.apply {
                onBaseBackPressed()
            }
            // findNavController().popBackStack()
        }

        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            logD("setOnRefreshListener")
            page = 1
            testViewModel?.getUserTestListByPage(page = page, isRefresh = true)
        }
    }

    private fun setupViewModels() {
        testViewModel = getViewModel(TestViewModel::class.java)
        testViewModel?.let { tvm ->
            tvm.userActionLiveData.observe(
                owner = viewLifecycleOwner,
                observer = { action ->
                    logD("userAction.observe action.isDoing ${action.isDoing}")
                    action.isDoing?.let { isDoing ->
                        swipeRefreshLayout.isRefreshing = isDoing
                    }

                    action.data?.let { userTestList ->
                        val isRefresh = action.isSwipeToRefresh
                        tvm.addUserList(userTestList = userTestList, isRefresh = isRefresh)
                    }

                    action.errorResponse?.let { error ->
                        logE("observe error " + BaseApplication.gson.toJson(error))
                        error.message?.let {
                            showDialogError(
                                errMsg = it,
                                runnable = {
                                    // do nothing
                                }
                            )
                        }
                    }
                }
            )
            tvm.userTestListLiveData.observe(
                viewLifecycleOwner
            ) {
                logD("userTestList.observe size: ${it?.size}")
                userListAdapter?.setList(it)
            }
        }
    }
}
