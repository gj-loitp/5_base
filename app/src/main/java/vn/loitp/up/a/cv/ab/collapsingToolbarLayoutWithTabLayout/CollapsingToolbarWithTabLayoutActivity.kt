package vn.loitp.up.a.cv.ab.collapsingToolbarLayoutWithTabLayout

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.showPopup
import vn.loitp.R
import vn.loitp.databinding.ACollapsingtoolbarWithtablayoutBinding

@LogTag("CollapsingToolbarWithTabLayoutActivity")
@IsFullScreen(false)
class CollapsingToolbarWithTabLayoutActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: ACollapsingtoolbarWithtablayoutBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACollapsingtoolbarWithtablayoutBinding.inflate(layoutInflater)
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

        binding.fab.setOnClickListener(this)

        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        binding.viewPager.adapter = mSectionsPagerAdapter

        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.changeTabsFont(fontName = FONT_PATH)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.fab -> {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            binding.btMenu -> {
                this.showPopup(
                    showOnView = v,
                    menuRes = R.menu.menu_popup,
                    callback = { menuItem ->
                        showShortInformation(menuItem.title.toString())
                    }
                )
            }
        }
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
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

    //TODO upgrade vp 2
    inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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
}
