package vn.loitp.up.a.cv.rv.recyclerTabLayout.tabScrollDisabled

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.ARecyclerTabLayoutDemoTabScrollDisabledBinding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.up.a.cv.rv.recyclerTabLayout.DemoColorPagerAdapter
import vn.loitp.up.a.cv.rv.recyclerTabLayout.utils.DemoData

@LogTag("RvTabScrollDisabledActivity")
@IsFullScreen(false)
class RvTabScrollDisabledActivity : BaseActivityFont() {
    private lateinit var binding: ARecyclerTabLayoutDemoTabScrollDisabledBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerTabLayoutDemoTabScrollDisabledBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val keyDemo = intent.getStringExtra(KEY_DEMO)
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

        val demoColorPagerAdapter = DemoColorPagerAdapter()
        demoColorPagerAdapter.addAll(items)
        binding.viewPager.adapter = demoColorPagerAdapter

        binding.recyclerTabLayout.setUpWithViewPager(binding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBaseBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val KEY_DEMO = "demo"

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabScrollDisabledActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }
}
