package vn.loitp.up.a.cv.rv.recyclerTabLayout.rtl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.ARecyclerTabLayoutDemoRtlBinding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.up.a.cv.rv.recyclerTabLayout.DemoColorPagerAdapter
import vn.loitp.up.a.cv.rv.recyclerTabLayout.basic.RvTabDemoBasicActivity
import vn.loitp.up.a.cv.rv.recyclerTabLayout.utils.DemoData

@LogTag("RvTabDemoRtlActivity")
@IsFullScreen(false)
class RvTabDemoRtlActivity : BaseActivityFont() {
    private lateinit var binding: ARecyclerTabLayoutDemoRtlBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerTabLayoutDemoRtlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val keyDemo = intent.getStringExtra(RvTabDemoBasicActivity.KEY_DEMO)
        if (keyDemo.isNullOrEmpty()) {
            onBaseBackPressed()
            return
        }
        val demo = Demo.valueOf(keyDemo)

        binding.toolbar.setTitle(demo.titleResId)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val items = DemoData.loadDemoColorItems(this)
        items.sortedWith { lhs, rhs -> lhs.name.compareTo(rhs.name) }

        val adapter = DemoColorPagerAdapter()
        adapter.addAll(items)
        binding.viewPager.adapter = adapter

        binding.recyclerTabLayout.setUpWithViewPager(binding.viewPager)
    }

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabDemoRtlActivity::class.java)
            intent.putExtra(RvTabDemoBasicActivity.KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }
}
