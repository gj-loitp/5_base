package vn.loitp.app.activity.pattern.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import loitp.basemaster.BR
import loitp.basemaster.R
import vn.loitp.app.activity.pattern.mvvm.model.User
import vn.loitp.app.activity.pattern.mvvm.viewModel.UserViewModel

/**
 * Created by Qichuan on 02/12/17.
 */
class MVVMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        /// Create the model with initial data
        val user = User()
        user.age = 20
        user.female = false
        user.firstName = "Johnny"
        user.lastName = "Depp"
        user.imageUrl = "https://media.giphy.com/media/zv8PVZLXBj81a/giphy.gif"
        user.tagline = "When Johnny Depp is young"

        /// Create the view model
        val userViewModel = UserViewModel(user)

        /// Data-Binding
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_mvvm)
        binding.setVariable(BR.user, userViewModel)
    }
}
