package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.utilities.LLog
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

    //At some point, you may need to clean up the view model. The idea is that you may want to delete or remove the data in the ViewModel on configuration change.
    //Simply override the onCleared method in the view model class. You can also cancel a long-running task in the onCleared method.
    //The onCleared method will call when the Activity completely gets destroyed.
    override fun onCleared() {
        super.onCleared()
        LLog.d("UserViewModel", "onCleared")
        //mUser = null
    }
}
