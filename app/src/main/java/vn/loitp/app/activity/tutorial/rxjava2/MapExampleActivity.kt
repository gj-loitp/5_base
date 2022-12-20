package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
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

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
@LogTag("MapExampleActivity")
@IsFullScreen(false)
class MapExampleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rxjava2_flowable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MapExampleActivity::class.java.simpleName
        }
        btn.setSafeOnClickListener {
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
}
