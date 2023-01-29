package vn.loitp.a.cv.tv.textDecorator

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.tv.textDecorator.LTextDecorator
import com.loitp.views.tv.textDecorator.OnTextClickListener
import kotlinx.android.synthetic.main.a_tv_text_decorator.*
import vn.loitp.R

// https://github.com/nntuyen/text-decorator?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=4982

@LogTag("TextDecoratorActivity")
@IsFullScreen(false)
class TextDecoratorActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_tv_text_decorator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = TextDecoratorActivityFont::class.java.simpleName
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
