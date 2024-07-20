package vn.loitp.up.a.cv.tv.selectable

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.tv.selectable.SelectableListener
import vn.loitp.R
import vn.loitp.databinding.ATvSelectableBinding

@LogTag("SelectableTextViewActivity")
@IsFullScreen(false)
class SelectableTextViewActivity : BaseActivityFont() {
    private lateinit var binding: ATvSelectableBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvSelectableBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = SelectableTextViewActivity::class.java.simpleName
        }

        binding.selectableView.setActivity(this)
        binding.selectableView.setText(getString(R.string.i_love_you))
        binding.selectableView.addOnSaveClickListener(object : SelectableListener {
            override fun selectedText(text: String?) {
                showShortInformation(msg = text)
            }
        })
        binding.tvEmptyBox.tag = 0
        binding.tvEmptyBox.setOnClickListener { view: View ->
            val type = view.tag as Int
            if (type == 0) {
                binding.tvEmptyBox.setBackgroundResource(R.drawable.selector_cancel_btn)
                binding.selectableView.selectAll()
                binding.tvEmptyBox.tag = 1
            } else {
                binding.tvEmptyBox.setBackgroundResource(R.drawable.selector_emptybox)
                binding.selectableView.hideCursor()
                binding.tvEmptyBox.tag = 0
            }
        }
    }
}
