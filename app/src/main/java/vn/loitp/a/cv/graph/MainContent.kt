package vn.loitp.a.cv.graph

import vn.loitp.a.cv.graph.algorithms.BuchheimWalkerActivityFont
import vn.loitp.a.cv.graph.algorithms.FruchtermanReingoldActivityFont
import vn.loitp.a.cv.graph.algorithms.SugiyamaActivityFont

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
                BuchheimWalkerActivityFont::class.java
            )
        )
        ITEMS.add(
            GraphItem(
                "FruchtermanReingold",
                "Directed graph drawing by simulating attraction/repulsion forces",
                FruchtermanReingoldActivityFont::class.java
            )
        )
        ITEMS.add(
            GraphItem(
                "Sugiyama et al.",
                "Algorithm for drawing multilayer graphs, taking advantage of the hierarchical structure of the graph.",
                SugiyamaActivityFont::class.java
            )
        )
    }
}
