package vn.loitp.up.a.cv.rv.recyclerTabLayout.years

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.ARecyclerTabLayoutBinding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo
import java.util.*

@LogTag("RvTabYearsActivity")
@IsFullScreen(false)
class RvTabYearsActivity : BaseActivityFont() {
    private lateinit var binding: ARecyclerTabLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerTabLayoutBinding.inflate(layoutInflater)
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

        val startYear = 1900
        val endYear = 3000
        val initialYear = Calendar.getInstance().get(Calendar.YEAR)

        val items = ArrayList<String>()
        for (i in startYear..endYear) {
            items.add(i.toString())
        }

        val demoYearsPagerAdapter = YearsPagerAdapter()
        demoYearsPagerAdapter.addAll(items)

        binding.viewPager.adapter = demoYearsPagerAdapter
        binding.viewPager.currentItem = initialYear - startYear

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
            val intent = Intent(context, RvTabYearsActivity::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }
}
