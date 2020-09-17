package vn.loitp.app.activity.pattern.observerpattern

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_pattern_observer.*
import vn.loitp.app.R

//https://viblo.asia/p/android-design-patterns-the-observer-pattern-WAyK8xqpKxX

@LayoutId(R.layout.activity_pattern_observer)
class ObserverPatternActivity : BaseFontActivity(), View.OnClickListener, RepositoryObserver {
    private var mUserDataRepository: Subject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserDataRepository = UserDataRepository.getInstance()
        mUserDataRepository?.registerObserver(this)

        bt.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View) {
        when (v) {
            bt -> {
                textView.text = "Loading..."
                UserDataRepository.getInstance()?.getNewDataFromRemote()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onUserDataChanged(fullName: String, age: Int) {
        showShort("onUserDataChanged $fullName - $age")
        textView.text = "$fullName - $age"
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserDataRepository?.removeObserver(this)
    }
}
