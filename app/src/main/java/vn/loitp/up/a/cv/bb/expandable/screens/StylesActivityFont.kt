package vn.loitp.up.a.cv.bb.expandable.screens

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.AStylesBinding

@LogTag("StylesActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class StylesActivityFont : BaseActivityFont() {
    private lateinit var binding: AStylesBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AStylesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
