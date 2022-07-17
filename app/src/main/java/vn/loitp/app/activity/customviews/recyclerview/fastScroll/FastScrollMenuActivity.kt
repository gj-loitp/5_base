package vn.loitp.app.activity.customviews.recyclerview.fastScroll

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.setSafeOnClickListener
import com.thedeanda.lorem.LoremIpsum
import kotlinx.android.synthetic.main.layout_fast_scroll_menu.*
import vn.loitp.app.R

@LogTag("SampleActivity")
@IsFullScreen(false)
class FastScrollMenuActivity : BaseFontActivity() {

    enum class Samples(
        val title: String,
        val fragmentClass: Class<out Fragment>
    ) {
        JustText("Just text", JustTextFragment::class.java),
        TextWithIcon("Text with icon", TextWithIconFragment::class.java),
        Styled("Styled", StyledFragment::class.java),
        Filtered("Filtered", FilteredFragment::class.java),
        CustomScroll("Custom scroll", CustomScrollFragment::class.java)
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.layout_fast_scroll_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        Samples.values().forEach { sample ->
            val button =
                layoutInflater.inflate(R.layout.layout_fast_scroll_menu_button, rootView, false)
                    .apply {
                        this as Button
                        text = sample.title
                        setSafeOnClickListener {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.layoutContainer,
                                    Fragment.instantiate(
                                        this@FastScrollMenuActivity,
                                        sample.fragmentClass.name
                                    )
                                )
                                .addToBackStack(null)
                                .commit()
                        }
                    }
            layoutButtons.addView(button)
        }

        fun updateViews() {
            val sampleFragment = supportFragmentManager.findFragmentById(R.id.layoutContainer)
            val isShowingSample = (sampleFragment != null)
            layoutMenuView.isGone = isShowingSample
            toolbar.isGone = !isShowingSample
            toolbar.title = sampleFragment?.let {
                Samples.values().find { it.fragmentClass == sampleFragment::class.java }?.title
            }
        }

        updateViews()
        supportFragmentManager.addOnBackStackChangedListener {
            updateViews()
        }

        AsyncTask.execute {
            // Preload the sample data. Not thread-safe, but not a big deal.
            LoremIpsum.getInstance()
        }
    }
}
