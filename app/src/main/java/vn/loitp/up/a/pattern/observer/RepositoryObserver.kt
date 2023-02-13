package vn.loitp.up.a.pattern.observer

interface RepositoryObserver {
    fun onUserDataChanged(
        fullName: String,
        age: Int
    )
}
