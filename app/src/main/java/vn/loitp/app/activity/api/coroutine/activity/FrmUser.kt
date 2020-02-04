package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.utilities.LImageUtil
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.frm_coroutine_user.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.model.UserTest
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel

class FrmUser : BaseFragment() {
    lateinit var testViewModel: TestViewModel

    companion object {
        const val KEY_USER = "KEY_USER"
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coroutine_user
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        testViewModel = getViewModel(TestViewModel::class.java)

        btBack.setSafeOnClickListener {
            findNavController().popBackStack()
        }

        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            LLog.d(TAG, "setOnRefreshListener")
            swipeRefreshLayout.isRefreshing = false
        }


        val bundle = arguments
        bundle?.let { b ->
            val userTest = b.getSerializable(KEY_USER)
            if (userTest is UserTest) {
                LUIUtil.printBeautyJson(userTest, tv)
                LImageUtil.loadCircle(userTest.avatar, iv)
            }
        }
    }
}