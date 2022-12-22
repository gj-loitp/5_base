package vn.loitp.app.a.cv.stackExpandableView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_stack_expandable_view.*
import vn.loitp.R

@LogTag("StackExpandableViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class StackExpandableViewActivity : BaseFontActivity() {
    companion object {
        const val STARTING_ITEM_NUMBER = 30
    }

    private var counter = STARTING_ITEM_NUMBER

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_stack_expandable_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/fabiosassu/StackExpandableView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = StackExpandableViewActivity::class.java.simpleName
        }
        horizontalStack.setWidgets(getLayouts(false))
        verticalStack.setWidgets(getLayouts())
        // add items on button click
        addGroupBtn.setOnClickListener {
            counter++
            verticalStack.addWidget(getLayout(counter))
            horizontalStack.addWidget(getLayout(counter, false))
        }
    }

    private fun getLayouts(isVertical: Boolean = true) = mutableListOf<ItemView>().apply {
        for (index in 1..STARTING_ITEM_NUMBER) {
            add(getLayout(index, isVertical))
        }
    }.toList()

    private fun getLayout(index: Int, isVertical: Boolean = true) =
        ItemView(this).apply {
            id = ViewCompat.generateViewId()
            if (isVertical) {
                ViewGroup.LayoutParams.MATCH_PARENT to ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                ViewGroup.LayoutParams.WRAP_CONTENT to ViewGroup.LayoutParams.MATCH_PARENT
            }.let { (width, height) ->
                layoutParams = ConstraintLayout.LayoutParams(width, height)
            }
            text = "Layout $index"
        }
}
