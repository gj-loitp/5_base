package vn.loitp.up.a.cv.dragView

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.tuanhav95.drag.DragView
import vn.loitp.R
import vn.loitp.databinding.ADragViewNormalBinding
import vn.loitp.up.a.cv.dragView.frm.BottomFragment
import vn.loitp.up.a.cv.dragView.frm.NormalTopFragment

@LogTag("NormalActivity")
@IsFullScreen(false)
class NormalActivity : BaseActivityFont() {
    private lateinit var binding: ADragViewNormalBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADragViewNormalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NormalActivity::class.java.simpleName
        }
        binding.dragView.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
            }

            override fun onChangePercent(percent: Float) {
                binding.alpha.alpha = 1 - percent
            }
        })

        supportFragmentManager.beginTransaction().add(R.id.frameFirst, NormalTopFragment()).commit()
        supportFragmentManager.beginTransaction().add(R.id.frameSecond, BottomFragment()).commit()

        binding.btnMax.setSafeOnClickListener { binding.dragView.maximize() }
        binding.btnMin.setSafeOnClickListener { binding.dragView.minimize() }
        binding.btnClose.setSafeOnClickListener { binding.dragView.close() }
    }
}
