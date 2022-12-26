package vn.loitp.app.a.cv.layout.expansionPanel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_expansion_panel_sample_recycler.*
import vn.loitp.R
import vn.loitp.app.a.cv.layout.expansionPanel.ExpansionPanelSampleActivityRecycler.RecyclerAdapter.RecyclerHolder

@LogTag("ExpansionPanelSampleActivityRecycler")
@IsFullScreen(false)
class ExpansionPanelSampleActivityRecycler : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_expansion_panel_sample_recycler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ExpansionPanelSampleActivityRecycler::class.java.simpleName
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter()
        recyclerView.adapter = adapter
        // fill with empty objects
        val list = ArrayList<String>()
        for (i in 0..29) {
            list.add(System.currentTimeMillis().toString())
        }
        adapter.setItems(list)
    }

    class RecyclerAdapter : RecyclerView.Adapter<RecyclerHolder>() {
        private val list = ArrayList<String>()
        private val expansionsCollection = ExpansionLayoutCollection()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
            return RecyclerHolder.buildFor(parent)
        }

        override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
            holder.bind()
            expansionsCollection.add(holder.expansionLayout)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setItems(items: ArrayList<String>) {
            list.addAll(items)
            notifyDataSetChanged()
        }

        class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var expansionLayout: ExpansionLayout = itemView.findViewById(R.id.expansionLayout)
            fun bind() {
                expansionLayout.collapse(false)
            }

            companion object {
                private const val LAYOUT = R.layout.item_expansion_panel_recycler_cell

                fun buildFor(viewGroup: ViewGroup): RecyclerHolder {
                    return RecyclerHolder(
                        LayoutInflater.from(viewGroup.context).inflate(LAYOUT, viewGroup, false)
                    )
                }
            }
        }

        init {
            expansionsCollection.openOnlyOne(true)
        }
    }
}
