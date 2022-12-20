package vn.loitp.app.activity.api.coroutine.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.service.model.UserTest
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.frm_coroutine_user.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.viewmodel.TestViewModel

@LogTag("FrmUser")
class FrmUser : BaseFragment() {
    companion object {
        const val KEY_USER = "KEY_USER"
    }

    private var testViewModel: TestViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coroutine_user
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
            //TODO fix getSerializable
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
