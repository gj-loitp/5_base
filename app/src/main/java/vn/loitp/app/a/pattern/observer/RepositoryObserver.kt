package vn.loitp.app.a.pattern.observer

interface RepositoryObserver {
    fun onUserDataChanged(fullName: String, age: Int)
}
