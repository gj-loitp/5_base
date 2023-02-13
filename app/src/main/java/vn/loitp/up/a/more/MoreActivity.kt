package vn.loitp.up.a.more

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.addFragment
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.more.FrmMore
import vn.loitp.R
import vn.loitp.databinding.AMoreBinding

@LogTag("MoreActivity")
@IsFullScreen(false)
class MoreActivity : BaseActivityFont() {

    private lateinit var binding: AMoreBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_more
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MoreActivity::class.java.simpleName
        }
        this.addFragment(
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = FrmMore(),
            isAddToBackStack = false
        )
    }
}
