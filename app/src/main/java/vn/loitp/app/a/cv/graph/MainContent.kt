package vn.loitp.app.a.cv.graph

import vn.loitp.app.a.cv.graph.algorithms.BuchheimWalkerActivity
import vn.loitp.app.a.cv.graph.algorithms.FruchtermanReingoldActivity
import vn.loitp.app.a.cv.graph.algorithms.SugiyamaActivity

object MainContent {
    val ITEMS: MutableList<GraphItem> = ArrayList()

    class GraphItem(
        val title: String,
        val description: String,
        val clazz: Class<*>,
    ) {
        override fun toString(): String {
            return title
        }
    }

    init {
        ITEMS.add(
            GraphItem(
                "BuchheimWalker",
                "Algorithm for drawing tree structures",
                BuchheimWalkerActivity::class.java
            )
        )
        ITEMS.add(
            GraphItem(
                "FruchtermanReingold",
                "Directed graph drawing by simulating attraction/repulsion forces",
                FruchtermanReingoldActivity::class.java
            )
        )
        ITEMS.add(
            GraphItem(
                "Sugiyama et al.",
                "Algorithm for drawing multilayer graphs, taking advantage of the hierarchical structure of the graph.",
                SugiyamaActivity::class.java
            )
        )
    }
}
