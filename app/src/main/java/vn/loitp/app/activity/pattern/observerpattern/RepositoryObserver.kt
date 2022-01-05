package vn.loitp.app.activity.pattern.observerpattern

interface RepositoryObserver {
    fun onUserDataChanged(fullName: String, age: Int)
}
