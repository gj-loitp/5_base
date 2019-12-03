package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.core.base.BaseFontActivity
import com.core.utilities.LDateUtil
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.activity_view_model.*
import loitp.basemaster.R
import vn.loitp.app.activity.pattern.mvp.User
import vn.loitp.app.app.LApplication
import java.util.*

//https://codinginfinite.com/architecture-component-viewmodel-example/
class ViewModelActivity : BaseFontActivity() {

    private lateinit var colorChangerViewModel: ColorChangerViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var timeChangerViewModel: TimeChangerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LLog.d(TAG, "onCreate")

        colorChangerViewModel = ViewModelProvider(this).get(ColorChangerViewModel::class.java)
        colorChangerViewModel.colorResource.observe(this, Observer {
            ll.setBackgroundColor(it)
        })
        btChangeColor.setOnClickListener {
            colorChangerViewModel.colorResource.value = generateRandomColor()
        }

        val defUser = User()
        defUser.fullName = "Loitp"
        defUser.email = "www.muathu@gmail.com"
        val factory = UserViewModel.CustomViewModelFactory(defUser)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        tv.text = LApplication.gson.toJson(userViewModel.mUser)
        btChangeUser.setOnClickListener {
            val user = User()
            user.fullName = "Loitp" + System.currentTimeMillis()
            user.email = "www.muathu@gmail.com" + System.currentTimeMillis()
            userViewModel.mUser = user
            tv.text = LApplication.gson.toJson(userViewModel.mUser)
        }

        timeChangerViewModel = ViewModelProvider(this).get(TimeChangerViewModel::class.java)
        timeChangerViewModel.timerValue.observe(this, Observer {
            tvTime.text = "$it -> " + LDateUtil.getDateCurrentTimeZoneMls(it, "yyyy-MM-dd HH:mm:ss")
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_model
    }

    private fun generateRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}
