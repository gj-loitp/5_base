package vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayoutwithtablayout

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LPopupMenu
import com.core.utilities.LUIUtil
import com.google.android.material.snackbar.Snackbar
import com.interfaces.CallbackPopup
import com.views.LToast
import kotlinx.android.synthetic.main.activity_collapsingtoolbar_withtablayout.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_collapsingtoolbar_withtablayout)
class CollapsingToolbarWithTabLayoutActivity : BaseFontActivity(), OnClickListener {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary))

        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener { onBackPressed() }

//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
//        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));

//        LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
//        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
//            @Override
//            public void onStateChange(LAppBarLayout.State toolbarChange) {
//                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
//                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
//                    //COLLAPSED appBarLayout min
//                    LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
//                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
//                    //EXPANDED appBarLayout max
//                    LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
//                } else {
//                    //IDLE appBarLayout not min not max
//                    LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
//                }
//            }
//        });

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
            R.id.btMenu -> LPopupMenu.show(activity, v, R.menu.menu_popup, object : CallbackPopup {
                override fun clickOnItem(menuItem: MenuItem) {
                    LToast.show(activity, menuItem.title.toString())
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
