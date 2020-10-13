package vn.loitp.app.activity.customviews.textview.selectabletextView

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_selectable.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_selectable)
@LogTag("SelectableTextViewActivity")
@IsFullScreen(false)
class SelectableTextViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        selectableView.setActivity(this)
        selectableView.setText(getString(R.string.i_love_you))
        selectableView.addOnSaveClickListener { text: String? ->
            showShort(msg = text)
        }
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