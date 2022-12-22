package vn.loitp.app.a.pattern.observerPattern

interface RepositoryObserver {
    fun onUserDataChanged(fullName: String, age: Int)
}
