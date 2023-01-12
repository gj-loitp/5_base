package vn.loitp.a.cv.stackExpandableView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_stack_expandable_view.*
import vn.loitp.R

@LogTag("StackExpandableViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class StackExpandableViewActivity : BaseActivityFont() {
    companion object {
        const val STARTING_ITEM_NUMBER = 30
    }

    private var counter = STARTING_ITEM_NUMBER

    override fun setLayoutResourceId(): Int {
        return R.layout.a_stack_expandable_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
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
