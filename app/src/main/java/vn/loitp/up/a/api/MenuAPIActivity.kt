package vn.loitp.up.a.api

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.restApi.restClient.RestClient
import vn.loitp.R
import vn.loitp.databinding.AMenuApiBinding
import vn.loitp.up.a.api.coroutine.a.CoroutineAPIActivity
import vn.loitp.up.a.api.galleryAPI.GalleryAPIActivity
import vn.loitp.up.a.api.retrofit2.TestAPIRetrofit2Activity
import vn.loitp.up.a.api.truyentranhtuan.MenuTTTAPIActivity

@LogTag("MenuAPIActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAPIActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AMenuApiBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuAPIActivity::class.java.simpleName
        }
        binding.btCoroutineAPI.setOnClickListener(this)
        binding.btGalleryAPI.setOnClickListener(this)
        binding.btComicAPI.setOnClickListener(this)
        binding.btTestRetrofit2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btCoroutineAPI -> launchActivity(CoroutineAPIActivity::class.java)
            binding.btGalleryAPI -> {
                RestClient.init(baseApiUrl = getString(R.string.flickr_URL))
                launchActivity(GalleryAPIActivity::class.java)
            }
            binding.btComicAPI -> launchActivity(MenuTTTAPIActivity::class.java)
            binding.btTestRetrofit2 -> launchActivity(TestAPIRetrofit2Activity::class.java)
        }
    }
}
