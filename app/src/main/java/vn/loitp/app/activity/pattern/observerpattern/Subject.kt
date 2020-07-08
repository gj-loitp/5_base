package vn.loitp.app.activity.pattern.observerpattern

/**
 * Created by www.muathu@gmail.com on 1/31/2018.
 */

interface Subject {
    fun registerObserver(repositoryObserver: RepositoryObserver)

    fun removeObserver(repositoryObserver: RepositoryObserver)

    fun notifyObservers()
}
