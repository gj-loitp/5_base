package vn.loitp.app.activity.customviews.layout.expansionpanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import kotlinx.android.synthetic.main.activity_expansion_panel_sample_recycler.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.expansionpanel.ExpansionPanelSampleActivityRecycler.RecyclerAdapter.RecyclerHolder

@LayoutId(R.layout.activity_expansion_panel_sample_recycler)
@LogTag("ExpansionPanelSampleActivityRecycler")
class ExpansionPanelSampleActivityRecycler : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerAdapter()
        recyclerView.adapter = adapter
        //fill with empty objects
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
                    return RecyclerHolder(LayoutInflater.from(viewGroup.context).inflate(LAYOUT, viewGroup, false))
                }
            }

        }

        init {
            expansionsCollection.openOnlyOne(true)
        }
    }
}
