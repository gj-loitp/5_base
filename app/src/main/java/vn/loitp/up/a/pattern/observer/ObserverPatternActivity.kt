package vn.loitp.up.a.pattern.observer

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.APatternObserverBinding

// https://viblo.asia/p/android-design-patterns-the-observer-pattern-WAyK8xqpKxX

@LogTag("ObserverPatternActivity")
@IsFullScreen(false)
class ObserverPatternActivity : BaseActivityFont(), RepositoryObserver {
    private var mUserDataRepository: Subject? = null
    private lateinit var binding: APatternObserverBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APatternObserverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserDataRepository = UserDataRepository.getInstance()
        mUserDataRepository?.registerObserver(this)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ObserverPatternActivity::class.java.simpleName
        }
        binding.bt.setSafeOnClickListener {
            binding.textView.text = "Loading..."
            UserDataRepository.getInstance()?.getNewDataFromRemote()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onUserDataChanged(fullName: String, age: Int) {
        showShortInformation("onUserDataChanged $fullName - $age")
        binding.textView.text = "$fullName - $age"
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserDataRepository?.removeObserver(this)
    }
}
