package vn.loitp.a.cv.graph

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import kotlinx.android.synthetic.main.a_graph_view_menu.*
import vn.loitp.R

@LogTag("MenuGraphViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuGraphViewActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_graph_view_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        graphs.apply {
            layoutManager = LinearLayoutManager(this@MenuGraphViewActivity)
            adapter = GraphListAdapter()
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private inner class GraphListAdapter : RecyclerView.Adapter<GraphViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_main_item, parent, false)
            return GraphViewHolder(view)
        }

        override fun onBindViewHolder(holder: GraphViewHolder, position: Int) {
            val graphItem = MainContent.ITEMS[position]
            holder.title.text = graphItem.title
            holder.description.text = graphItem.description
            holder.itemView.setOnClickListener {
                startActivity(
                    Intent(
                        this@MenuGraphViewActivity,
                        graphItem.clazz
                    )
                )
            }
        }

        override fun getItemCount(): Int {
            return MainContent.ITEMS.size
        }
    }

    private inner class GraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
    }
}
