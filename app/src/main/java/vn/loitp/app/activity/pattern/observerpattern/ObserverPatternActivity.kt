package vn.loitp.app.activity.pattern.observerpattern

import android.os.Bundle
import android.view.View
import android.widget.TextView

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.views.LToast

//https://viblo.asia/p/android-design-patterns-the-observer-pattern-WAyK8xqpKxX
class ObserverPatternActivity : BaseFontActivity(), View.OnClickListener, RepositoryObserver {
    private var mUserDataRepository: Subject? = null
    private var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv = findViewById<TextView>(R.id.tv)

        mUserDataRepository = UserDataRepository.getInstance()
        mUserDataRepository!!.registerObserver(this)

        findViewById<View>(R.id.bt).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_observer_pattern
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt -> {
                tv!!.text = "Loading..."
                UserDataRepository.getInstance()!!.getNewDataFromRemote()
            }
        }
    }

    override fun onUserDataChanged(fullname: String, age: Int) {
        LToast.show(activity, "onUserDataChanged $fullname - $age")
        tv!!.text = "$fullname - $age"
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserDataRepository!!.removeObserver(this)
    }
}
