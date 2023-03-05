package vn.loitp.up.a.cv.graph

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import dev.bandb.graphview.AbstractGraphAdapter
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node
import vn.loitp.R
import vn.loitp.databinding.AGraphBinding
import java.util.*

@LogTag("GraphActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
abstract class GraphActivity : BaseActivityFont() {
    lateinit var binding: AGraphBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    protected lateinit var adapter: AbstractGraphAdapter<NodeViewHolder>
    private var currentNode: Node? = null
    private var nodeCount = 1

    protected abstract fun createGraph(): Graph
    protected abstract fun setLayoutManager()
    protected abstract fun setEdgeDecoration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    .inflate(R.layout.l_v_node, parent, false)
                return NodeViewHolder(view)
            }

            override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
                holder.textView.text = Objects.requireNonNull(getNodeData(position)).toString()
            }
        }.apply {
            this.submitGraph(graph)
            binding.recycler.adapter = this
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupFab(graph: Graph) {
        binding.addNode.setOnClickListener {
            val newNode = Node(nodeText)
            if (currentNode != null) {
                graph.addEdge(currentNode!!, newNode)
            } else {
                graph.addNode(newNode)
            }
            adapter.notifyDataSetChanged()
        }
        binding.addNode.setOnLongClickListener {
            currentNode?.let { n ->
                graph.removeNode(n)
                currentNode = null
                adapter.notifyDataSetChanged()
                binding.addNode.hide()
            }
            true
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
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
                if (!binding.addNode.isShown) {
                    binding.addNode.show()
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
