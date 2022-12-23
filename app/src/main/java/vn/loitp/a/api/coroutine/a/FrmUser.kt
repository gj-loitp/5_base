package vn.loitp.a.api.coroutine.a

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.sv.model.UserTest
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.f_coroutine_user.*
import vn.loitp.a.api.coroutine.vm.TestViewModel
import vn.loitp.R

@LogTag("FrmUser")
class FrmUser : BaseFragment() {
    companion object {
        const val KEY_USER = "KEY_USER"
    }

    private var testViewModel: TestViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.f_coroutine_user
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
