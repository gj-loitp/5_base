package vn.loitp.app.activity.pattern.observerPattern

interface RepositoryObserver {
    fun onUserDataChanged(fullName: String, age: Int)
}
