package vn.loitp.up.a.cv.bt.autoSize

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.toggleScreenOrientation
import vn.loitp.databinding.AAutoSizeButtonBinding

@LogTag("AutoSizeButtonActivity")
@IsFullScreen(false)
class AutoSizeButtonActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: AAutoSizeButtonBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAutoSizeButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.btRotate.setOnClickListener(this)

        binding.bt0.setPortraitSizeWInDp(50f)
        binding.bt0.setPortraitSizeHInDp(50f)
        binding.bt0.setLandscapeSizeWInDp(250f)
        binding.bt0.setLandscapeSizeHInDp(250f)
        binding.bt0.setOnClickListener(this)

        binding.bt1.setPortraitSizeWInDp(150f)
        binding.bt1.setPortraitSizeHInDp(150f)
        binding.bt1.setLandscapeSizeWInDp(100f)
        binding.bt1.setLandscapeSizeHInDp(100f)
        binding.bt1.setOnClickListener(this)

        binding.bt2.setPortraitSizeWInPx(screenWidth)
        binding.bt2.setPortraitSizeHInPx(screenWidth / 10)
        binding.bt2.setLandscapeSizeWInPx(screenWidth / 2)
        binding.bt2.setLandscapeSizeHInPx(screenWidth / 2)
        binding.bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btRotate -> toggleScreenOrientation()
            binding.bt0, binding.bt1, binding.bt2 -> showShortInformation("Click")
        }
    }
}
