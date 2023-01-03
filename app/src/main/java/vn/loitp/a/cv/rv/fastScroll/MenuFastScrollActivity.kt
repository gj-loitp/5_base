package vn.loitp.a.cv.rv.fastScroll

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.thedeanda.lorem.LoremIpsum
import kotlinx.android.synthetic.main.a_menu_fast_scroll.*
import vn.loitp.R

@LogTag("FastScrollMenuActivity")
@IsFullScreen(false)
class MenuFastScrollActivity : BaseFontActivity() {

    enum class Samples(
        val title: String,
        val fragmentClass: Class<out Fragment>
    ) {
        JustText(title = "Just text", fragmentClass = JustTextFragment::class.java),
        TextWithIcon(title = "Text with icon", fragmentClass = TextWithIconFragment::class.java),
        Styled(title = "Styled", fragmentClass = StyledFragment::class.java),
        Filtered(title = "Filtered", fragmentClass = FilteredFragment::class.java),
        CustomScroll(title = "Custom scroll", fragmentClass = CustomScrollFragment::class.java)
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_fast_scroll
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
                                        this@MenuFastScrollActivity,
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
