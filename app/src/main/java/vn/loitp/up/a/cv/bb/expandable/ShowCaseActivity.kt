package vn.loitp.up.a.cv.bb.expandable

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AShowcaseBinding
import vn.loitp.up.a.cv.bb.expandable.screens.*
import vn.loitp.up.a.cv.bb.expandable.screens.navi.NavigationComponentActivity
import vn.loitp.up.app.EmptyActivity

@LogTag("ShowCaseActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShowCaseActivity : BaseActivityFont() {
    companion object {
        const val ARGS_SCREEN_TITLE = "args.screen_title"
    }

    private val showCaseInfos by lazy {
        listOf(
            createShowCase<ProgrammaticallyCreatedDemoActivity>(
                title = getString(R.string.programmatically_title),
                description = getString(R.string.programmatically_description)
            ), createShowCase<XmlDeclaredActivity>(
                title = getString(R.string.xml_declared_title),
                description = getString(R.string.xml_declared_description)
            ), createShowCase<JavaActivity>(
                title = getString(R.string.interop_title),
                description = getString(R.string.interop_description)
            ), createShowCase<CoordinatorLayoutActivity>(
                title = getString(R.string.coordinator_base_title),
                description = getString(R.string.coordinator_base_description)
            ), createShowCase<ScrollableCoordinatorLayoutActivityFont>(
                title = getString(R.string.coordinator_scroll_title),
                description = getString(R.string.coordinator_scroll_description)
            ), createShowCase<NavigationComponentActivity>(
                title = getString(R.string.navigation_components_title),
                description = getString(R.string.navigation_components_description)
            ), createShowCase<StylesActivityFont>(
                title = getString(R.string.styles_title),
                description = getString(R.string.styles_description)
            ), createShowCase<NotificationBadgeActivity>(
                title = getString(R.string.notification_badges_title),
                description = getString(R.string.notification_badges_description)
            )
        )
    }
    private lateinit var binding: AShowcaseBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AShowcaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/st235/ExpandableBottomBar"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
        val adapter = ShowCaseAdapter(showCaseInfos) { info ->
            val intent = Intent(this@ShowCaseActivity, info.toClass)
            intent.putExtra(ARGS_SCREEN_TITLE, title)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
    }
}
