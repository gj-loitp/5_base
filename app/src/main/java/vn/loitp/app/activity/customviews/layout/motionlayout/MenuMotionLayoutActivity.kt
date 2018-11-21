package vn.loitp.app.activity.customviews.layout.motionlayout

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_menu_motion_layout.*
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.layout.motionlayout.fragmentsdemo.FragmentExample2Activity
import vn.loitp.app.activity.customviews.layout.motionlayout.fragmentsdemo.FragmentExampleActivity
import vn.loitp.app.activity.customviews.layout.motionlayout.viewpagerdemo.MotionLayoutViewPagerActivity
import vn.loitp.app.activity.customviews.layout.motionlayout.viewpagerdemo.MotionLayoutViewPagerActivity2
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class MenuMotionLayoutActivity : BaseFontActivity(), CompoundButton.OnCheckedChangeListener {
    override fun setFullScreen(): Boolean {
        return false;
    }

    override fun setTag(): String {
        return "TAGMenuMotionLayoutActivity";
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_motion_layout
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var doShowPaths = false
    private val dataset: Array<DemosAdapter.Demo> = arrayOf(
            DemosAdapter.Demo("Basic Example (1/2)", "Basic motion example using referenced ConstraintLayout files", R.layout.motion_01_basic),
            DemosAdapter.Demo("Basic Example (2/2)", "Basic motion example using ConstraintSets defined in the MotionScene file", R.layout.motion_02_basic),
            DemosAdapter.Demo("Custom Attribute", "Show color interpolation (custom attribute)", R.layout.motion_03_custom_attribute),
            DemosAdapter.Demo("ImageFilterView (1/2)", "Show image cross-fade (using ML's ImageFilterView + custom attribute)", R.layout.motion_04_imagefilter),
            DemosAdapter.Demo("ImageFilterView (2/2)", "Show image saturation transition (using ML's ImageFilterView + custom attribute)", R.layout.motion_05_imagefilter),
            DemosAdapter.Demo("Keyframe Position (1/3)", "Use a simple keyframe to change the interpolated motion", R.layout.motion_06_keyframe),
            DemosAdapter.Demo("Keyframe Interpolation (2/3)", "More complex keyframe, adding rotation interpolation", R.layout.motion_07_keyframe),
            DemosAdapter.Demo("Keyframe Cycle (3/3)", "Basic example of using a keyframe cycle ", R.layout.motion_08_cycle),
            DemosAdapter.Demo("CoordinatorLayout Example (1/3)", "Basic example of using MotionLayout instead of AppBarLayout", R.layout.motion_09_coordinatorlayout),
            DemosAdapter.Demo("CoordinatorLayout Example (2/3)", "Slightly more complex example of MotionLayout replacing AppBarLayout, with multiple elements and parallax background", R.layout.motion_10_coordinatorlayout),
            DemosAdapter.Demo("CoordinatorLayout Example (3/3)", "Another AppBarLayout replacement example", R.layout.motion_11_coordinatorlayout),
            DemosAdapter.Demo("DrawerLayout Example (1/2)", "Basic DrawerLayout with MotionLayout", R.layout.motion_12_drawerlayout),
            DemosAdapter.Demo("DrawerLayout Example (2/2)", "Advanced DrawerLayout with MotionLayout", R.layout.motion_13_drawerlayout),
            DemosAdapter.Demo("Side Panel Example", "Side Panel, implemented with MotionLayout only", R.layout.motion_14_side_panel),
            DemosAdapter.Demo("Parallax Example", "Parallax background. Drag the car.", R.layout.motion_15_parallax),
            DemosAdapter.Demo("ViewPager Example", "Using MotionLayout with ViewPager", MotionLayoutViewPagerActivity::class.java),
            DemosAdapter.Demo("ViewPager Lottie Example", "Using MotionLayout and Lottie with ViewPager", MotionLayoutViewPagerActivity2::class.java),
            DemosAdapter.Demo("Complex Motion Example (1/4)", "Basic CoordinatorLayout-like behavior. Implemented with MotionLayout only, using a moving guideline. Note the view isn't resized. ", R.layout.motion_17_coordination),

            DemosAdapter.Demo("Complex Motion Example (2/4)", "Advanced CoordinatorLayout-like behavior (adding a FAB). Implemented with MotionLayout only, using a moving guideline. Note the view isn't resized.", R.layout.motion_18_coordination),
            DemosAdapter.Demo("Complex Motion Example (3/4)", "Advanced CoordinatorLayout-like behavior (adding a FAB). Implemented with MotionLayout only, using direct resizing of the view.", R.layout.motion_19_coordination),
            DemosAdapter.Demo("Complex Motion Example (4/4)", "Advanced Synchronized reval motion + helper (bounce). Implemented with MotionLayout only.", R.layout.motion_20_reveal),
            DemosAdapter.Demo("Fragment Transition Example (1/2)", "Example showing transitioning fragments within MotionLayout", FragmentExampleActivity::class.java),
            DemosAdapter.Demo("Fragment Transition Example (2/2)", "Example showing transitioning fragments within MotionLayout", FragmentExample2Activity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewManager = LinearLayoutManager(this)
        viewAdapter = DemosAdapter(dataset)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        showPaths.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(p0: CompoundButton?, value: Boolean) {
        doShowPaths = value
    }

    fun start(c: Class<*>, layoutFileId: Int) {
        val intent = Intent(this, c).apply {
            putExtra("layout_file_id", layoutFileId)
            putExtra("showPaths", doShowPaths)
        }
        startActivity(intent)
        LActivityUtil.tranIn(activity);
    }
}
