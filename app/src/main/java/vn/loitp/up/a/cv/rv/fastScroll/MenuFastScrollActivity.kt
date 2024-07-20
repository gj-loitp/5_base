package vn.loitp.up.a.cv.rv.fastScroll

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.thedeanda.lorem.LoremIpsum
import vn.loitp.R
import vn.loitp.databinding.AMenuFastScrollBinding

@LogTag("FastScrollMenuActivity")
@IsFullScreen(false)
class MenuFastScrollActivity : BaseActivityFont() {

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

    private lateinit var binding: AMenuFastScrollBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuFastScrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }

        Samples.values().forEach { sample ->
            val button =
                layoutInflater.inflate(
                    R.layout.layout_fast_scroll_menu_button,
                    binding.rootView,
                    false
                )
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
            binding.layoutButtons.addView(button)
        }

        fun updateViews() {
            val sampleFragment = supportFragmentManager.findFragmentById(R.id.layoutContainer)
            val isShowingSample = (sampleFragment != null)
            binding.layoutMenuView.isGone = isShowingSample
            binding.toolbar.isGone = !isShowingSample
            binding.toolbar.title = sampleFragment?.let {
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
