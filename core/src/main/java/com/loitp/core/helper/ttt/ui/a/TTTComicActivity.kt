package com.loitp.core.helper.ttt.ui.a

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.helper.ttt.model.MenuComicTTT
import com.loitp.core.helper.ttt.ui.f.FrmFavTTT
import com.loitp.core.helper.ttt.ui.f.FrmHomeTTT
import com.loitp.core.helper.ttt.ui.f.FrmProfileTTT
import com.loitpcore.R
import com.loitp.core.utilities.LUIUtil
import com.loitp.core.utils.KeyboardUtils
import com.loitpcore.views.viewPager.vpTransformers.ZoomOutSlideTransformer
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor
import kotlinx.android.synthetic.main.l_activity_ttt_comic.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTComicActivity")
@IsFullScreen(false)
class TTTComicActivity : BaseFontActivity() {

    val listMenuComicTTT = ArrayList<MenuComicTTT>()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_ttt_comic
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupData()
        setupViews()
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

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                expandableBottomBar.menu.select(listMenuComicTTT[position].itemId)
                KeyboardUtils.hideSoftInput(this@TTTComicActivity)
            }
        })

        expandableBottomBar.menu.add(
            MenuItemDescriptor.Builder(
                context = this,
                itemId = listMenuComicTTT[0].itemId,
                iconId = listMenuComicTTT[0].iconId,
                textId = listMenuComicTTT[0].textId,
                activeColor = listMenuComicTTT[0].activeColor
            ).build()
        )
        expandableBottomBar.menu.add(
            MenuItemDescriptor.Builder(
                context = this,
                itemId = listMenuComicTTT[1].itemId,
                iconId = listMenuComicTTT[1].iconId,
                textId = listMenuComicTTT[1].textId,
                activeColor = listMenuComicTTT[1].activeColor
            ).build()
        )
        expandableBottomBar.menu.add(
            MenuItemDescriptor.Builder(
                context = this,
                itemId = listMenuComicTTT[2].itemId,
                iconId = listMenuComicTTT[2].iconId,
                textId = listMenuComicTTT[2].textId,
                activeColor = listMenuComicTTT[2].activeColor
            ).build()
        )

        expandableBottomBar.onItemSelectedListener = { _, menuItem, _ ->
            logD("onItemSelectedListener " + menuItem.id)
            val index = getIndexOfListMenuComic(itemId = menuItem.id)
            index?.let {
                viewPager.setCurrentItem(/* item = */ it, /* smoothScroll = */ true)
            }
        }

        expandableBottomBar.onItemReselectedListener = { _, menuItem, _ ->
            logD("onItemReselectedListener" + menuItem.id)
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

    private inner class SlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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

    override fun onBaseBackPressed() {
//        super.onBaseBackPressed()
        if (doubleBackToExitPressedOnce) {
//            finish()//correct
            super.onBaseBackPressed()//correct
            return
        }
        this.doubleBackToExitPressedOnce = true
        showShortInformation(msg = getString(R.string.press_again_to_exit), isTopAnchor = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}
