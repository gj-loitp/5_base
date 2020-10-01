package vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayoutwithtablayout

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LAppResource
import com.core.utilities.LPopupMenu
import com.core.utilities.LUIUtil
import com.google.android.material.snackbar.Snackbar
import com.interfaces.CallbackPopup
import com.views.LToast
import kotlinx.android.synthetic.main.activity_collapsingtoolbar_withtablayout.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_collapsingtoolbar_withtablayout)
@LogTag("CollapsingToolbarWithTabLayoutActivity")
@IsFullScreen(false)
class CollapsingToolbarWithTabLayoutActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomStatusBar(colorStatusBar = Color.TRANSPARENT, colorNavigationBar = LAppResource.getColor(R.color.colorPrimary))

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        fab.setOnClickListener(this)

        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        viewPager.adapter = mSectionsPagerAdapter

        tabs.setupWithViewPager(viewPager)
        LUIUtil.changeTabsFont(tabLayout = tabs, fontName = Constants.FONT_PATH)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            R.id.btMenu -> LPopupMenu.show(this, v, R.menu.menu_popup, object : CallbackPopup {
                override fun clickOnItem(menuItem: MenuItem) {
                    LToast.show(this@CollapsingToolbarWithTabLayoutActivity, menuItem.title.toString())
                }
            })
        }
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.frm_text, container, false)
            val textView = rootView.findViewById<TextView>(R.id.textView)
            textView.setText(R.string.large_text)
            return rootView
        }

        companion object {

            private const val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "SECTION 1"
                1 -> return "SECTION 2"
                2 -> return "SECTION 3"
            }
            return null
        }
    }

//    private fun changeTabsFont(tabLayout: TabLayout, fontName: String) {
//        val vg = tabLayout.getChildAt(0) as ViewGroup
//        val tabsCount = vg.childCount
//        for (j in 0 until tabsCount) {
//            val vgTab = vg.getChildAt(j) as ViewGroup
//            val tabChildsCount = vgTab.childCount
//            for (i in 0 until tabChildsCount) {
//                val tabViewChild = vgTab.getChildAt(i)
//                if (tabViewChild is TextView) {
//                    CalligraphyUtils.applyFontToTextView(tabLayout.context, tabViewChild, fontName)
//                }
//            }
//        }
//    }
}
