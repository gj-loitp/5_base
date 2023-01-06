package vn.loitp.a.api.coroutine.a

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.printBeautyJson
import com.loitp.core.ext.setColorForSwipeRefreshLayout
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.sv.model.UserTest
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.f_coroutine_user.*
import vn.loitp.R
import vn.loitp.a.api.coroutine.vm.TestViewModel

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

        swipeRefreshLayout.setColorForSwipeRefreshLayout()
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }

        val bundle = arguments
        bundle?.let { b ->
            val userTest = b.getSerializable(KEY_USER)
            if (userTest is UserTest) {
                textView.printBeautyJson(o = userTest)
                imageView.loadGlide(
                    any = userTest.avatar,
                    transformation = CropCircleWithBorderTransformation()
                )
            }
        }
    }
}
