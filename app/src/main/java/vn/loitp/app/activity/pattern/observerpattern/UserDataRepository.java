package vn.loitp.app.activity.pattern.observerpattern;

/**
 * Created by www.muathu@gmail.com on 1/31/2018.
 */

import android.os.Handler;

import java.util.ArrayList;

public class UserDataRepository implements Subject {
    private String mFullName;
    private int mAge;
    private static UserDataRepository INSTANCE = null;

    private ArrayList<RepositoryObserver> mObservers;

    private UserDataRepository() {
        mObservers = new ArrayList<>();
        //getNewDataFromRemote();
    }

    // Simulate network
    public void getNewDataFromRemote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setUserData("Chike Mgbemena", 101);
            }
        }, 2000);
    }

    // Creates a Singleton of the class
    public static UserDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository();
        }
        return INSTANCE;
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if (!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if (mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer : mObservers) {
            observer.onUserDataChanged(mFullName, mAge);
        }
    }

    public void setUserData(String fullName, int age) {
        mFullName = fullName;
        mAge = age;
        notifyObservers();
    }
}