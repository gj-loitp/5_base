package vn.loitp.app.activity.pattern.observerPattern

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_pattern_observer.*
import vn.loitp.app.R

// https://viblo.asia/p/android-design-patterns-the-observer-pattern-WAyK8xqpKxX

@LogTag("ObserverPatternActivity")
@IsFullScreen(false)
class ObserverPatternActivity : BaseFontActivity(), View.OnClickListener, RepositoryObserver {
    private var mUserDataRepository: Subject? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pattern_observer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserDataRepository = UserDataRepository.getInstance()
        mUserDataRepository?.registerObserver(this)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ObserverPatternActivity::class.java.simpleName
        }
        bt.setOnClickListener(this)
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
        showShortInformation("onUserDataChanged $fullName - $age")
        textView.text = "$fullName - $age"
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserDataRepository?.removeObserver(this)
    }
}
