package vn.loitp.up.a.pattern.observer

interface Subject {
    fun registerObserver(repositoryObserver: RepositoryObserver)

    fun removeObserver(repositoryObserver: RepositoryObserver)

    fun notifyObservers()
}
