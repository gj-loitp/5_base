package vn.loitp.up.a.cv.rv.gv

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.AGvBinding

@LogTag("loitpGridViewActivity")
@IsFullScreen(false)
class GridViewActivity : BaseActivityFont() {

    private lateinit var binding: AGvBinding
    val list = ArrayList<String>()
    val adapter = GridViewAdapter()

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
        for (i in 0..100) {
            list.add("Loitp $i")
        }

        binding.gv.adapter = adapter
        binding.gv.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, _: View?, i: Int, _: Long ->
                logD(">>> i $i")
            }
        adapter.updateList(list = list, isLoadImageNetwork = true)

        binding.btPick.setSafeOnClickListener {
            getListFiles()
        }
    }

    private fun getListFiles() {
        val fl =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/test large")
        val files = fl.listFiles()
        if (files.isNullOrEmpty()) {
            showShortError("No file in folder: Environment.DIRECTORY_DCIM/test large")
            return
        }
        list.clear()
        logD("getListFiles ${files.size}")
        for (i in files.indices) {
            logD("getListFiles ${files[i].name} + ${files[i].path}")
            list.add(files[i].path)
        }
        adapter.updateList(list = list, isLoadImageNetwork = false)
    }
}
