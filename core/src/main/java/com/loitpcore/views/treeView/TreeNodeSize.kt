package com.loitpcore.views.treeView

class TreeNodeSize {
    var width = 0
        private set
    var height = 0
        private set

    constructor()

    constructor(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    operator fun set(width: Int, height: Int) {
        this.width = width
        this.height = height
    }
}
