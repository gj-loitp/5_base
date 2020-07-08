package vn.loitp.app.activity.pattern.observerpattern

/**
 * Created by www.muathu@gmail.com on 1/31/2018.
 */

import android.os.Handler
import java.util.*

class UserDataRepository private constructor() : Subject {
    private var mFullName: String = ""
    private var mAge: Int = 0
    private val mObservers: ArrayList<RepositoryObserver> = ArrayList()

    init {
        //getNewDataFromRemote();
    }

    // Simulate network
    fun getNewDataFromRemote() {
        val handler = Handler()
        handler.postDelayed({ setUserData("Loitp ^^! +${System.currentTimeMillis()}", 101) }, 2000)
    }

    override fun registerObserver(repositoryObserver: RepositoryObserver) {
        if (!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver)
        }
    }

    override fun removeObserver(repositoryObserver: RepositoryObserver) {
        if (mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver)
        }
    }

    override fun notifyObservers() {
        for (observer in mObservers) {
            observer.onUserDataChanged(mFullName, mAge)
        }
    }

    fun setUserData(fullName: String, age: Int) {
        mFullName = fullName
        mAge = age
        notifyObservers()
    }

    companion object {
        private var instance: UserDataRepository? = null

        // Creates a Singleton of the class
        fun getInstance(): UserDataRepository? {
            if (instance == null) {
                instance = UserDataRepository()
            }
            return instance
        }
    }
}