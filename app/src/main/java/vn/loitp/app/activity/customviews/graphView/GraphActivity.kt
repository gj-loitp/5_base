package vn.loitp.app.activity.customviews.graphView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import dev.bandb.graphview.AbstractGraphAdapter
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node
import kotlinx.android.synthetic.main.activity_graph.*
import vn.loitp.app.R
import java.util.*

@LogTag("GraphActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
abstract class GraphActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_graph
    }

    protected lateinit var adapter: AbstractGraphAdapter<NodeViewHolder>
    private var currentNode: Node? = null
    private var nodeCount = 1

    protected abstract fun createGraph(): Graph
    protected abstract fun setLayoutManager()
    protected abstract fun setEdgeDecoration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val graph = createGraph()
        setLayoutManager()
        setEdgeDecoration()
        setupGraphView(graph)

        setupFab(graph)
        setupToolbar()
    }

    private fun setupGraphView(graph: Graph) {
        adapter = object : AbstractGraphAdapter<NodeViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.node, parent, false)
                return NodeViewHolder(view)
            }

            override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
                holder.textView.text = Objects.requireNonNull(getNodeData(position)).toString()
            }
        }.apply {
            this.submitGraph(graph)
            recycler.adapter = this
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupFab(graph: Graph) {
        addNode.setOnClickListener {
            val newNode = Node(nodeText)
            if (currentNode != null) {
                graph.addEdge(currentNode!!, newNode)
            } else {
                graph.addNode(newNode)
            }
            adapter.notifyDataSetChanged()
        }
        addNode.setOnLongClickListener {
            currentNode?.let { n ->
                graph.removeNode(n)
                currentNode = null
                adapter.notifyDataSetChanged()
                addNode.hide()
            }
            true
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBaseBackPressed()
        return true
    }

    protected inner class NodeViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                if (!addNode.isShown) {
                    addNode.show()
                }
                currentNode = adapter.getNode(bindingAdapterPosition)
                Snackbar.make(
                    itemView,
                    "Clicked on " + adapter.getNodeData(bindingAdapterPosition)?.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    protected val nodeText: String
        get() = "Node " + nodeCount++
}
