package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.service.model.UserTest
import com.views.setSafeOnClickListener
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.frm_coroutine_user.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel

@LayoutId(R.layout.frm_coroutine_user)
@LogTag("FrmUser")
class FrmUser : BaseFragment() {
    private var testViewModel: TestViewModel? = null

    companion object {
        const val KEY_USER = "KEY_USER"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        testViewModel = getViewModel(TestViewModel::class.java)

        btBack.setSafeOnClickListener {
            findNavController().popBackStack()
        }

        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout = swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }

        val bundle = arguments
        bundle?.let { b ->
            val userTest = b.getSerializable(KEY_USER)
            if (userTest is UserTest) {
                LUIUtil.printBeautyJson(o = userTest, textView = textView)
                LImageUtil.load(
                        context = activity,
                        any = userTest.avatar,
                        imageView = imageView,
                        transformation = CropCircleWithBorderTransformation()
                )
            }
        }
    }
}