package vn.loitp.up.a.cv.rv.gv

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.AGvBinding
import vn.loitp.up.common.Constants

@LogTag("GridViewActivity")
@IsFullScreen(false)
class GridViewActivity : BaseActivityFont() {

    private lateinit var binding: AGvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AGvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = GridViewActivity::class.java.simpleName
        }
        val list = ArrayList<String>()
        for (i in 0..100) {
            list.add("Loitp $i")
        }
        val adapter = GridViewAdapter(list = list)
        binding.gv.adapter = adapter
        binding.gv.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, _: View?, i: Int, _: Long ->
                logD(">>> i $i")
            }
    }
}
