package com.core.helper.girl

import android.graphics.Color
import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import github.com.st235.lib_expandablebottombar.ExpandableBottomBarMenuItem
import kotlinx.android.synthetic.main.l_activity_girl.*

@LogTag("GirlActivity")
@IsFullScreen(false)
class GirlActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl)

        setupViews()
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
    }

}
