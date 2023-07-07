package vn.loitp.up.a.api.coroutine.a

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.databinding.ACoroutineApiBinding

@LogTag("CoroutineAPIActivity")
@IsFullScreen(false)
class CoroutineAPIActivity : BaseActivityFont() {

    private lateinit var binding: ACoroutineApiBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACoroutineApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
