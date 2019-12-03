package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.core.base.BaseFontActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LLog.d(TAG, "onCreate")

        colorChangerViewModel = ViewModelProviders.of(this).get(ColorChangerViewModel::class.java)

        ll.setBackgroundColor(colorChangerViewModel.getColorResource())
        btChangeColor.setOnClickListener {
            val color = generateRandomColor()
            ll.setBackgroundColor(color)
            colorChangerViewModel.setColorResource(color)
        }

        val defUser = User()
        defUser.fullName = "Loitp"
        defUser.email = "www.muathu@gmail.com"
        val factory = UserViewModel.CustomViewModelFactory(defUser)
        userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        tv.text = LApplication.gson.toJson(userViewModel.mUser)
        btChangeUser.setOnClickListener {
            val user = User()
            user.fullName = "Loitp" + System.currentTimeMillis()
            user.email = "www.muathu@gmail.com" + System.currentTimeMillis()
            userViewModel.mUser = user
            tv.text = LApplication.gson.toJson(userViewModel.mUser)
        }
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
