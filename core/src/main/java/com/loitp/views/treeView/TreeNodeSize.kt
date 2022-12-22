package com.loitp.views.treeView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class TreeNodeSize {
    var width = 0
        private set
    var height = 0
        private set

    constructor()

    @Suppress("unused")
    constructor(
        width: Int,
        height: Int
    ) {
        this.width = width
        this.height = height
    }

    operator fun set(
        width: Int,
        height: Int
    ) {
        this.width = width
        this.height = height
    }
}
