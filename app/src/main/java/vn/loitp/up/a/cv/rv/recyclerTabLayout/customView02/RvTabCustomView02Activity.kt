package vn.loitp.up.a.cv.rv.recyclerTabLayout.customView02

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.ARecyclerTabLayoutDemoCustomView02Binding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.up.a.cv.rv.recyclerTabLayout.DemoImagePagerAdapter
import vn.loitp.up.a.cv.rv.recyclerTabLayout.utils.DemoData

@LogTag("RvTabCustomView02Activity")
@IsFullScreen(false)
class RvTabCustomView02Activity : BaseActivityFont() {
    private lateinit var binding: ARecyclerTabLayoutDemoCustomView02Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerTabLayoutDemoCustomView02Binding.inflate(layoutInflater)
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

        val adapter = DemoImagePagerAdapter()
        adapter.addAll(DemoData.loadImageResourceList())

        binding.viewPager.adapter = adapter

        binding.recyclerTabLayout.setUpWithAdapter(RvTabCustomView02Adapter(binding.viewPager))
        binding.recyclerTabLayout.setPositionThreshold(0.5f)
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
            val intent = Intent(context, RvTabCustomView02Activity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }
}
