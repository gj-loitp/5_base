package vn.loitp.app.activity.pattern.observerPattern

interface Subject {
    fun registerObserver(repositoryObserver: RepositoryObserver)

    fun removeObserver(repositoryObserver: RepositoryObserver)

    fun notifyObservers()
}
