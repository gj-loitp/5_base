package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_showcase.*
import kotlinx.android.synthetic.main.activity_showcase.lActionBar
import vn.loitp.app.R
import vn.loitp.app.activity.EmptyActivity
import vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens.*
import vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens.navigation.NavigationComponentActivity

@LogTag("ShowCaseActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShowCaseActivity : BaseFontActivity() {

    private val showCaseInfos by lazy {
        listOf(
            createShowCase<ProgrammaticallyCreatedDemoActivity>(
                title = getString(R.string.programmatically_title),
                description = getString(R.string.programmatically_description)
            ),
            createShowCase<XmlDeclaredActivity>(
                title = getString(R.string.xml_declared_title),
                description = getString(R.string.xml_declared_description)
            ),
            createShowCase<JavaActivity>(
                title = getString(R.string.interop_title),
                description = getString(R.string.interop_description)
            ),
            createShowCase<CoordinatorLayoutActivity>(
                title = getString(R.string.coordinator_base_title),
                description = getString(R.string.coordinator_base_description)
            ),
            createShowCase<ScrollableCoordinatorLayoutActivity>(
                title = getString(R.string.coordinator_scroll_title),
                description = getString(R.string.coordinator_scroll_description)
            ),
            createShowCase<NavigationComponentActivity>(
                title = getString(R.string.navigation_components_title),
                description = getString(R.string.navigation_components_description)
            ),
            createShowCase<StylesActivity>(
                title = getString(R.string.styles_title),
                description = getString(R.string.styles_description)
            ),
            createShowCase<NotificationBadgeActivity>(
                title = getString(R.string.notification_badges_title),
                description = getString(R.string.notification_badges_description)
            )
        )
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_showcase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                            url = "https://github.com/st235/ExpandableBottomBar"
                        )
                    }
                )
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

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    companion object {
        const val ARGS_SCREEN_TITLE = "args.screen_title"
    }
}
