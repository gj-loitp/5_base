package vn.loitp.a.pattern.observer

interface RepositoryObserver {
    fun onUserDataChanged(
        fullName: String,
        age: Int
    )
}
