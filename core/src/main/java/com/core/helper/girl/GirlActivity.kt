package com.core.helper.girl

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.viewpager.viewpagertransformers.CubeInTransformer
import github.com.st235.lib_expandablebottombar.ExpandableBottomBarMenuItem
import kotlinx.android.synthetic.main.l_activity_girl.*

@LogTag("GirlActivity")
@IsFullScreen(false)
class GirlActivity : BaseFontActivity() {

    val listPage = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl)

        setupData()
        setupViews()
    }

    private fun setupData() {
        listPage.add(getString(R.string.menu_home))
        listPage.add(getString(R.string.menu_favourite))
        listPage.add(getString(R.string.menu_infor))
    }

    private fun setupViews() {
        expandableBottomBar.addItems(
                ExpandableBottomBarMenuItem.Builder(context = this)
                        .addItem(itemId = R.id.menuHome, iconId = R.drawable.baseline_home_black_24dp, textId = R.string.menu_home, activeColor = Color.GRAY)
                        .addItem(itemId = R.id.menuFavourite, iconId = R.drawable.baseline_favorite_black_24dp, textId = R.string.menu_favourite, activeColor = Color.GRAY)
                        .addItem(itemId = R.id.menuInformation, iconId = R.drawable.baseline_info_black_24dp, textId = R.string.menu_infor, activeColor = Color.GRAY)
                        .build()
        )
        expandableBottomBar.onItemSelectedListener = { view, menuItem ->
            logD("onItemSelectedListener " + menuItem.itemId)
        }

        expandableBottomBar.onItemReselectedListener = { view, menuItem ->
            logD("onItemReselectedListener" + menuItem.itemId)
        }

        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.setPageTransformer(true, CubeInTransformer())
    }

    private inner class SlidePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return FrmGirl()
        }

        override fun getCount(): Int {
            return listPage.size
        }
    }
}
