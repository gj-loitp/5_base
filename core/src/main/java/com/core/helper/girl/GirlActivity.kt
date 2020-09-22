package com.core.helper.girl

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
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

    val listMenuGirl = ArrayList<MenuGirl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl)

        setupData()
        setupViews()
    }

    private fun setupData() {
        val menuGirlHome = MenuGirl(
                itemId = R.id.menuHome,
                iconId = R.drawable.baseline_home_black_24dp,
                textId = R.string.menu_home,
                activeColor = Color.GRAY
        )
        listMenuGirl.add(menuGirlHome)

        val menuGirlFavourite = MenuGirl(
                itemId = R.id.menuFavourite,
                iconId = R.drawable.baseline_favorite_black_24dp,
                textId = R.string.menu_favourite,
                activeColor = Color.GRAY
        )
        listMenuGirl.add(menuGirlFavourite)

        val menuGirlInformation = MenuGirl(
                itemId = R.id.menuInformation,
                iconId = R.drawable.baseline_info_black_24dp,
                textId = R.string.menu_infor,
                activeColor = Color.GRAY
        )
        listMenuGirl.add(menuGirlInformation)
    }

    private fun setupViews() {
        viewPager.adapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = listMenuGirl.size
        viewPager.setPageTransformer(true, CubeInTransformer())
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                expandableBottomBar.select(listMenuGirl[position].itemId)
            }
        })
        expandableBottomBar.addItems(
                ExpandableBottomBarMenuItem.Builder(context = this)
                        .addItem(itemId = listMenuGirl[0].itemId, iconId = listMenuGirl[0].iconId, textId = listMenuGirl[0].textId, activeColor = listMenuGirl[0].activeColor)
                        .addItem(itemId = listMenuGirl[1].itemId, iconId = listMenuGirl[1].iconId, textId = listMenuGirl[1].textId, activeColor = listMenuGirl[1].activeColor)
                        .addItem(itemId = listMenuGirl[2].itemId, iconId = listMenuGirl[2].iconId, textId = listMenuGirl[2].textId, activeColor = listMenuGirl[2].activeColor)
                        .build()
        )
        expandableBottomBar.onItemSelectedListener = { view, menuItem ->
            logD("onItemSelectedListener " + menuItem.itemId)
            val index = getIndexOfListMenuGirl(itemId = menuItem.itemId)
            index?.let {
                viewPager.currentItem = it
            }
        }

        expandableBottomBar.onItemReselectedListener = { view, menuItem ->
            logD("onItemReselectedListener" + menuItem.itemId)
        }
    }

    private fun getIndexOfListMenuGirl(itemId: Int): Int? {
        for (i in 0 until listMenuGirl.size) {
            if (listMenuGirl[i].itemId == itemId) {
                return i
            }
        }
        return null
    }

    private inner class SlidePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    FrmHome()
                }
                1 -> {
                    FrmFavourite()
                }
                2 -> {
                    FrmInformation()
                }
                else -> {
                    FrmHome()
                }
            }
        }

        override fun getCount(): Int {
            return listMenuGirl.size
        }
    }
}
