package vn.loitp.app.activity.pattern.observerpattern;

/**
 * Created by www.muathu@gmail.com on 1/31/2018.
 */

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);

    void removeObserver(RepositoryObserver repositoryObserver);

    void notifyObservers();
}