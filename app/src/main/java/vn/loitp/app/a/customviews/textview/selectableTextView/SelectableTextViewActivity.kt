package vn.loitp.app.a.customviews.textview.selectableTextView

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.tv.selectable.SelectableListener
import kotlinx.android.synthetic.main.activity_text_view_selectable.*
import vn.loitp.R

@LogTag("SelectableTextViewActivity")
@IsFullScreen(false)
class SelectableTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_selectable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SelectableTextViewActivity::class.java.simpleName
        }

        selectableView.setActivity(this)
        selectableView.setText(getString(R.string.i_love_you))
        selectableView.addOnSaveClickListener(object : SelectableListener {
            override fun selectedText(text: String?) {
                showShortInformation(msg = text)
            }
        })
        tvEmptyBox.tag = 0
        tvEmptyBox.setOnClickListener { view: View ->
            val type = view.tag as Int
            if (type == 0) {
                tvEmptyBox.setBackgroundResource(R.drawable.selector_cancel_btn)
                selectableView.selectAll()
                tvEmptyBox.tag = 1
            } else {
                tvEmptyBox.setBackgroundResource(R.drawable.selector_emptybox)
                selectableView.hideCursor()
                tvEmptyBox.tag = 0
            }
        }
    }
}
