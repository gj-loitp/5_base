package vn.loitp.up.a.tut

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.tut.retrofit2.Retrofit2ActivityFont
import vn.loitp.a.tut.rxjava2.MenuRxJava2ActivityFont
import vn.loitp.databinding.ATutMenuBinding

@LogTag("MenuTutorialActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuTutorialActivity : BaseActivityFont() {

    private lateinit var binding: ATutMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATutMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuTutorialActivity::class.java.simpleName
        }
        binding.btRxJava2.setSafeOnClickListener {
            launchActivity(MenuRxJava2ActivityFont::class.java)
        }
        binding.btRetrofit2.setSafeOnClickListener {
            launchActivity(Retrofit2ActivityFont::class.java)
        }
    }
}
