package com.core.helper.ttt.ui.a

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.ttt.model.MenuComicTTT
import com.core.helper.ttt.ui.f.FrmFavTTT
import com.core.helper.ttt.ui.f.FrmHomeTTT
import com.core.helper.ttt.ui.f.FrmProfileTTT
import com.core.utilities.LUIUtil
import com.core.utilities.LValidateUtil
import com.utils.util.KeyboardUtils
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer
import github.com.st235.lib_expandablebottombar.ExpandableBottomBarMenuItem
import kotlinx.android.synthetic.main.l_activity_comic_ttt.*

@LogTag("TTTComicActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(false)
class TTTComicActivity : BaseFontActivity() {

    val listMenuComicTTT = ArrayList<MenuComicTTT>()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_comic_ttt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupData()
        setupViews()

        LValidateUtil.isValidPackageName()
    }

    private fun setupData() {
        val activeColor = if (LUIUtil.isDarkTheme()) {
            Color.BLACK
        } else {
            Color.WHITE
        }
        val menuComicHome = MenuComicTTT(
                itemId = R.id.menuHome,
                iconId = R.drawable.baseline_home_black_24dp,
                textId = R.string.home,
                activeColor = activeColor
        )
        listMenuComicTTT.add(menuComicHome)

        val menuComicFavourite = MenuComicTTT(
                itemId = R.id.menuFavourite,
                iconId = R.drawable.baseline_favorite_black_24dp,
                textId = R.string.favourite,
                activeColor = activeColor
        )
        listMenuComicTTT.add(menuComicFavourite)

        val menuComicInformation = MenuComicTTT(
                itemId = R.id.menuProfile,
                iconId = R.drawable.baseline_person_black_24dp,
                textId = R.string.profile,
                activeColor = activeColor
        )
        listMenuComicTTT.add(menuComicInformation)
    }

    private fun setupViews() {
        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = listMenuComicTTT.size
        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                expandableBottomBar.select(listMenuComicTTT[position].itemId)
                KeyboardUtils.hideSoftInput(this@TTTComicActivity)
            }
        })
        expandableBottomBar.addItems(
                ExpandableBottomBarMenuItem.Builder(context = this)
                        .addItem(itemId = listMenuComicTTT[0].itemId, iconId = listMenuComicTTT[0].iconId, textId = listMenuComicTTT[0].textId, activeColor = listMenuComicTTT[0].activeColor)
                        .addItem(itemId = listMenuComicTTT[1].itemId, iconId = listMenuComicTTT[1].iconId, textId = listMenuComicTTT[1].textId, activeColor = listMenuComicTTT[1].activeColor)
                        .addItem(itemId = listMenuComicTTT[2].itemId, iconId = listMenuComicTTT[2].iconId, textId = listMenuComicTTT[2].textId, activeColor = listMenuComicTTT[2].activeColor)
                        .build()
        )
        expandableBottomBar.onItemSelectedListener = { _, menuItem ->
            logD("onItemSelectedListener " + menuItem.itemId)
            val index = getIndexOfListMenuComic(itemId = menuItem.itemId)
            index?.let {
                viewPager.setCurrentItem(it, true)
            }
        }

        expandableBottomBar.onItemReselectedListener = { _, menuItem ->
            logD("onItemReselectedListener" + menuItem.itemId)
        }
    }

    private fun getIndexOfListMenuComic(itemId: Int): Int? {
        for (i in 0 until listMenuComicTTT.size) {
            if (listMenuComicTTT[i].itemId == itemId) {
                return i
            }
        }
        return null
    }

    private inner class SlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    FrmHomeTTT()
                }
                1 -> {
                    FrmFavTTT()
                }
                2 -> {
                    FrmProfileTTT()
                }
                else -> {
                    FrmHomeTTT()
                }
            }
        }

        override fun getCount(): Int {
            return listMenuComicTTT.size
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showShortInformation(msg = getString(R.string.press_again_to_exit), isTopAnchor = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}
