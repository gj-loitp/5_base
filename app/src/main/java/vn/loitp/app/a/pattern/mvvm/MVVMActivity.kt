package vn.loitp.app.a.pattern.mvvm

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import vn.loitp.BR
import vn.loitp.R
import vn.loitp.app.a.pattern.mvvm.vm.UserViewModel

@LogTag("MVVMActivity")
@IsFullScreen(false)
class MVVMActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pattern_mvvm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        // / Create the model with initial data
        val user = vn.loitp.app.a.pattern.mvvm.md.User()
        user.age = 20
        user.female = false
        user.firstName = "Johnny"
        user.lastName = "Depp"
        user.imageUrl = "https://media.giphy.com/media/zv8PVZLXBj81a/giphy.gif"
        user.tagline = "When Johnny Depp is young"

        // / Create the view model
        val userViewModel = UserViewModel(user)

        // / Data-Binding
        val binding =
            DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_pattern_mvvm)
        binding.setVariable(BR.user, userViewModel)
    }
}
