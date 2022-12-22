package vn.loitp.app.a.pattern.mvvm.viewModel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import vn.loitp.app.a.pattern.mvvm.model.User
import java.util.*

class UserViewModel(private val user: User) : Observer, BaseObservable() {

    // / Register itself as the observer of Model
    init {
        user.addObserver(this)
    }

    // / Notify the UI when change event emitting from Model is received.
    override fun update(p0: Observable?, p1: Any?) {
        if (p1 is String) {
            when (p1) {
                "age" -> {
                    notifyPropertyChanged(BR.age)
                }
                "firstName", "lastName" -> {
                    notifyPropertyChanged(BR.name)
                }
                "imageUrl" -> {
                    notifyPropertyChanged(BR.imageUrl)
                }
                "tagline" -> {
                    notifyPropertyChanged(BR.tagline)
                }
                "female" -> {
                    notifyPropertyChanged(BR.gender)
                }
            }
        }
    }

    val name: String
        @Bindable get() {
            return user.firstName + " " + user.lastName
        }

    val age: String
        @Bindable get() {
            return if (user.age <= 0) return ""
            else String.format(Locale.ENGLISH, "%d years old", user.age)
        }

    val gender: String
        @Bindable get() {
            return if (user.female) return "Female" else "Male"
        }

    val imageUrl: String
        @Bindable get() {
            return user.imageUrl
        }

    val tagline: String
        @Bindable get() {
            return "Tagline: " + user.tagline
        }

    //cannot delete view: View, it called by xml
    fun onButtonClick(view: View) {
        this.user.age = 35
        this.user.imageUrl = "https://media.giphy.com/media/w7M8g9cTom0Du/giphy.gif"
        this.user.tagline = "Now he has grown up..."
    }
}
