package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.loitp.app.activity.pattern.mvp.User

class UserViewModel(private val user: User) : ViewModel() {
    var mUser: User? = null

    init {
        mUser = user
    }

    class CustomViewModelFactory(private val user: User) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(user) as T
        }
    }
}
