package vn.loitp.app.a.pattern.observer

interface Subject {
    fun registerObserver(repositoryObserver: RepositoryObserver)

    fun removeObserver(repositoryObserver: RepositoryObserver)

    fun notifyObservers()
}
