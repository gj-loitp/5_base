package vn.loitp.app.activity.customviews.textview.textDecorator

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.textView.textDecorator.LTextDecorator
import com.loitpcore.views.textView.textDecorator.OnTextClickListener
import kotlinx.android.synthetic.main.activity_text_view_text_decorator.*
import vn.loitp.app.R

// https://github.com/nntuyen/text-decorator?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=4982

@LogTag("TextDecoratorActivity")
@IsFullScreen(false)
class TextDecoratorActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_text_decorator
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = TextDecoratorActivity::class.java.simpleName
        }

        val text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis et porta ipsum. Praesent vitae libero a mi sodales accumsan. Donec tempor nulla turpis, vitae pellentesque ligula consectetur sed. Quisque commodo lorem eget ipsum pulvinar consequat. Nam erat risus, rhoncus quis ligula sed, tempor venenatis nulla. Duis quis placerat quam."
        LTextDecorator.decorate(textView = tvContent, content = text)
            .setTextColor(R.color.colorAccent, "Lorem", "amet")
            .setBackgroundColor(R.color.red, "dolor", "elit")
            .strikethrough("Duis", "Praesent")
            .underline("sodales", "quam")
            .setSubscript("vitae")
            .makeTextClickable(
                object : OnTextClickListener {
                    override fun onClick(view: View, text: String) {
                        showShortInformation(text)
                    }
                },
                false,
                "porta",
                "commodo",
                "tempor venenatis nulla"
            )
            .setTextColor(
                android.R.color.holo_green_light,
                "porta",
                "commodo",
                "tempor venenatis nulla"
            )
            .build()
    }
}
