package vn.loitp.up.a.cv.rv.dragDrop

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AMainDragDropBinding

@LogTag("MainActivityDragDrop")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MainActivityDragDrop : BaseActivityFont(), CustomListener {

    private lateinit var binding: AMainDragDropBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMainDragDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MainActivityDragDrop::class.java.simpleName
        }
        binding.recyclerView1.init(listOf("A", "B", "C"), binding.tvEmptyList1)
        binding.recyclerView2.init(listOf("1", "2", "3"), binding.tvEmptyList2)
    }

    private fun RecyclerView.init(
        list: List<String>,
        emptyTextView: TextView
    ) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CustomAdapter(list, this@MainActivityDragDrop)
        this.adapter = adapter
        emptyTextView.setOnDragListener(adapter.dragInstance)
        this.setOnDragListener(adapter.dragInstance)
    }

    override fun setEmptyList(
        visibility: Int,
        recyclerView: Int,
        emptyTextView: Int
    ) {
        findViewById<RecyclerView>(recyclerView).visibility = visibility
        findViewById<TextView>(emptyTextView).visibility = visibility
    }
}
