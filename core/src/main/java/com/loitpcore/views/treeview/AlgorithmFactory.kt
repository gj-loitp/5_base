package com.loitpcore.views.treeview

object AlgorithmFactory {

    fun createBuchheimWalker(configuration: BuchheimWalkerConfiguration?): Algorithm {
        return BuchheimWalkerAlgorithm(configuration)
    }

    @JvmStatic
    fun createDefaultBuchheimWalker(): Algorithm {
        return BuchheimWalkerAlgorithm()
    }
}
