package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R
import vn.loitp.app.activity.tutorial.rxjava2.model.ApiUser
import vn.loitp.app.activity.tutorial.rxjava2.model.User
import vn.loitp.app.activity.tutorial.rxjava2.util.RxJavaUtils.Companion.apiUserList
import vn.loitp.app.activity.tutorial.rxjava2.util.RxJavaUtils.Companion.convertApiUserListToUserList

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
@LayoutId(R.layout.activity_rxjava2_flowable)
@LogTag("MapExampleActivity")
class MapExampleActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setOnClickListener {
            doSomeWork()
        }
    }

    /*
     * Here we are getting ApiUser Object from api server
     * then we are converting it into User Object because
     * may be our database support User Not ApiUser Object
     * Here we are using Map Operator to do that
     */
    private fun doSomeWork() {
        observable
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .map { apiUsers ->
                    convertApiUserListToUserList(apiUsers)
                }
                .subscribe(observer)
    }

    private val observable: Observable<List<ApiUser>>
        get() = Observable.create { listObservableEmitter: ObservableEmitter<List<ApiUser>> ->
            if (!listObservableEmitter.isDisposed) {
                listObservableEmitter.onNext(apiUserList)
                listObservableEmitter.onComplete()
            }
        }

    private val observer: Observer<List<User>>
        get() = object : Observer<List<User>> {
            override fun onSubscribe(d: Disposable) {
                logD("onSubscribe : " + d.isDisposed)
            }

            override fun onNext(userList: List<User>) {
                textView.append("\nonNext")
                for (user in userList) {
                    textView.append("\nfirstname : ${user.firstname}")
                }
            }

            override fun onError(e: Throwable) {
                textView.append("\nonError : ${e.message}")
            }

            override fun onComplete() {
                textView.append("\nonComplete")
            }
        }

    override fun setFullScreen(): Boolean {
        return false
    }

}
