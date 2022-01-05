package vn.loitp.app.activity.pattern.observerpattern

interface Subject {
    fun registerObserver(repositoryObserver: RepositoryObserver)

    fun removeObserver(repositoryObserver: RepositoryObserver)

    fun notifyObservers()
}
