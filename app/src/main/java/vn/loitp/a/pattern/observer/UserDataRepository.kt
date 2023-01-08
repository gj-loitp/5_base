package vn.loitp.a.pattern.observer

import android.os.Handler
import android.os.Looper
import com.loitp.core.ext.getRandomNumber

class UserDataRepository private constructor() : Subject {
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

    private var mFullName: String = ""
    private var mAge: Int = 0 // ktlint-disable no-wildcard-imports
    private val mObservers: ArrayList<RepositoryObserver> = ArrayList()

    // Simulate network
    fun getNewDataFromRemote() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                setUserData(
                    "Loitp ^^! +${System.currentTimeMillis()}",
                    getRandomNumber(100)
                )
            },
            2000
        )
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

    private fun setUserData(
        fullName: String,
        age: Int
    ) {
        mFullName = fullName
        mAge = age
        notifyObservers()
    }
}
