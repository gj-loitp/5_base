package vn.loitp.up.a.cv.rv.recyclerTabLayout.autoSelect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.ARecyclerTabLayoutBinding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.up.a.cv.rv.recyclerTabLayout.basic.RvTabDemoBasicActivity

class RvTabAutoSelectActivityRvTab : RvTabDemoBasicActivity() {

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabAutoSelectActivityRvTab::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }

    private lateinit var binding: ARecyclerTabLayoutBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARecyclerTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.recyclerTabLayout.setAutoSelectionMode(true)
    }
}
