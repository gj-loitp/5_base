package vn.loitp.app.activity.pattern.observerpattern

/**
 * Created by www.muathu@gmail.com on 1/31/2018.
 */

interface RepositoryObserver {
    fun onUserDataChanged(fullname: String, age: Int)
}