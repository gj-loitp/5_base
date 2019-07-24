package vn.loitp.app.activity.pattern.mvp

import com.core.utilities.LUIUtil

class DemoPresenter(var view: View?) {
    private val user: User = User()

    fun updateFullName(fullName: String) {
        user.fullName = fullName
        view?.updateUserInfoTextView(user.toString())
    }

    fun updateEmail(email: String) {
        user.email = email
        view?.updateUserInfoTextView(user.toString())
    }

    fun doALongTask() {
        view?.showProgressBar()
        LUIUtil.setDelay(5000, Runnable {
            view?.let{
                it.hideProgressBar()
                it.onDoALongTask("Finish do a long task (5000mls) ${System.currentTimeMillis()}")
            }
        })
    }

    interface View {
        fun updateUserInfoTextView(info: String)

        fun showProgressBar()

        fun hideProgressBar()

        fun onDoALongTask(result: String)
    }
}