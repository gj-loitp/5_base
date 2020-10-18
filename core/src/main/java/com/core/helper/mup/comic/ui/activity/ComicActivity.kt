package com.core.helper.mup.comic.ui.activity

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
import com.core.helper.mup.comic.model.MenuComic
import com.core.helper.mup.comic.ui.frm.FrmFavourite
import com.core.helper.mup.comic.ui.frm.FrmHome
import com.core.helper.mup.comic.ui.frm.FrmProfile
import com.core.utilities.LUIUtil
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer
import github.com.st235.lib_expandablebottombar.ExpandableBottomBarMenuItem
import kotlinx.android.synthetic.main.l_activity_girl.*

@LogTag("ComicActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
class ComicActivity : BaseFontActivity() {

    val listMenuComic = ArrayList<MenuComic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_comic)

        setupData()
        setupViews()
    }

    private fun setupData() {
        val activeColor = if (LUIUtil.isDarkTheme()) {
            Color.WHITE
        } else {
            Color.BLACK
        }
        val menuComicHome = MenuComic(
                itemId = R.id.menuHome,
                iconId = R.drawable.baseline_home_black_24dp,
                textId = R.string.home,
                activeColor = activeColor
        )
        listMenuComic.add(menuComicHome)

        val menuComicFavourite = MenuComic(
                itemId = R.id.menuFavourite,
                iconId = R.drawable.baseline_favorite_black_24dp,
                textId = R.string.favourite,
                activeColor = activeColor
        )
        listMenuComic.add(menuComicFavourite)

        val menuComicInformation = MenuComic(
                itemId = R.id.menuProfile,
                iconId = R.drawable.baseline_person_black_24dp,
                textId = R.string.profile,
                activeColor = activeColor
        )
        listMenuComic.add(menuComicInformation)
    }

    private fun setupViews() {
        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = listMenuComic.size
        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                expandableBottomBar.select(listMenuComic[position].itemId)
            }
        })
        expandableBottomBar.addItems(
                ExpandableBottomBarMenuItem.Builder(context = this)
                        .addItem(itemId = listMenuComic[0].itemId, iconId = listMenuComic[0].iconId, textId = listMenuComic[0].textId, activeColor = listMenuComic[0].activeColor)
                        .addItem(itemId = listMenuComic[1].itemId, iconId = listMenuComic[1].iconId, textId = listMenuComic[1].textId, activeColor = listMenuComic[1].activeColor)
                        .addItem(itemId = listMenuComic[2].itemId, iconId = listMenuComic[2].iconId, textId = listMenuComic[2].textId, activeColor = listMenuComic[2].activeColor)
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
        for (i in 0 until listMenuComic.size) {
            if (listMenuComic[i].itemId == itemId) {
                return i
            }
        }
        return null
    }

    private inner class SlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    FrmHome()
                }
                1 -> {
                    FrmFavourite()
                }
                2 -> {
                    FrmProfile()
                }
                else -> {
                    FrmHome()
                }
            }
        }

        override fun getCount(): Int {
            return listMenuComic.size
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
