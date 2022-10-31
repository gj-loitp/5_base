package com.loitpcore.views.treeView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object AlgorithmFactory {

    @Suppress("unused")
    fun createBuchheimWalker(configuration: BuchheimWalkerConfiguration?): Algorithm {
        return BuchheimWalkerAlgorithm(configuration)
    }

    @JvmStatic
    fun createDefaultBuchheimWalker(): Algorithm {
        return BuchheimWalkerAlgorithm()
    }
}
