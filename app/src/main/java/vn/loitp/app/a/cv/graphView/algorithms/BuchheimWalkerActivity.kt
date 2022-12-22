package vn.loitp.app.a.cv.graphView.algorithms

import android.view.Menu
import android.view.MenuItem
import dev.bandb.graphview.graph.Graph
import dev.bandb.graphview.graph.Node
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration
import kotlinx.android.synthetic.main.activity_graph.*
import vn.loitp.R
import vn.loitp.app.a.cv.graphView.GraphActivity

class BuchheimWalkerActivity : GraphActivity() {

    public override fun setLayoutManager() {
        val configuration = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(100)
            .setSubtreeSeparation(100)
            .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
            .build()
        recycler.layoutManager = BuchheimWalkerLayoutManager(this, configuration)
    }

    public override fun setEdgeDecoration() {
        recycler.addItemDecoration(TreeEdgeDecoration())
    }

    public override fun createGraph(): Graph {
        val graph = Graph()
        val node1 = Node(nodeText)
        val node2 = Node(nodeText)
        val node3 = Node(nodeText)
        val node4 = Node(nodeText)
        val node5 = Node(nodeText)
        val node6 = Node(nodeText)
        val node8 = Node(nodeText)
        val node7 = Node(nodeText)
        val node9 = Node(nodeText)
        val node10 = Node(nodeText)
        val node11 = Node(nodeText)
        val node12 = Node(nodeText)
        graph.addEdge(node1, node2)
        graph.addEdge(node1, node3)
        graph.addEdge(node1, node4)
        graph.addEdge(node2, node5)
        graph.addEdge(node2, node6)
        graph.addEdge(node6, node7)
        graph.addEdge(node6, node8)
        graph.addEdge(node4, node9)
        graph.addEdge(node4, node10)
        graph.addEdge(node4, node11)
        graph.addEdge(node11, node12)
        return graph
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_buchheim_walker_orientations, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(300)
            .setSubtreeSeparation(300)
        when (item.itemId) {
            R.id.topToBottom -> {
                builder.setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
            }
            R.id.bottomToTop -> {
                builder.setOrientation(BuchheimWalkerConfiguration.ORIENTATION_BOTTOM_TOP)
            }
            R.id.leftToRight -> {
                builder.setOrientation(BuchheimWalkerConfiguration.ORIENTATION_LEFT_RIGHT)
            }
            R.id.rightToLeft -> {
                builder.setOrientation(BuchheimWalkerConfiguration.ORIENTATION_RIGHT_LEFT)
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        recycler.layoutManager = BuchheimWalkerLayoutManager(this, builder.build())
        recycler.adapter = adapter
        return true
    }
}
