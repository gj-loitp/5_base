package vn.loitp.up.a.cv.ab.collapsingToolbarLayout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.showPopup
import com.loitp.views.layout.appBar.LAppBarLayout
import vn.loitp.R
import vn.loitp.databinding.ACollapsingToolbarLayoutWithTabLayoutBinding

@LogTag("CollapsingToolbarLayoutActivity")
@IsFullScreen(false)
class CollapsingToolbarLayoutActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: ACollapsingToolbarLayoutWithTabLayoutBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACollapsingToolbarLayoutWithTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setCustomStatusBar(
            colorStatusBar = Color.TRANSPARENT,
            colorNavigationBar = getColor(R.color.colorPrimary)
        )

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener {
            onBaseBackPressed()
        }

        binding.appBarLayout.setOnStateChangeListener(object : LAppBarLayout.OnStateChangeListener {
            override fun onStateChange(toolbarChange: LAppBarLayout.State) {
                when (toolbarChange) {
                    LAppBarLayout.State.COLLAPSED -> // COLLAPSED appBarLayout min
                        logD("COLLAPSED toolbarChange: $toolbarChange")
                    LAppBarLayout.State.EXPANDED -> // EXPANDED appBarLayout max
                        logD("EXPANDED toolbarChange: $toolbarChange")
                    else -> // IDLE appBarLayout not min not max
                        logD("IDLE toolbarChange: $toolbarChange")
                }
            }
        })

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.btMenu.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btMenu -> this.showPopup(
                showOnView = v,
                menuRes = R.menu.menu_popup,
                callback = { menuItem ->
                    showShortInformation(menuItem.title.toString())
                }
            )
        }
    }
}
