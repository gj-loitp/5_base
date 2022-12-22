package com.loitp.views.treeView

/**
 * Class to save additional data used by the buchheim-walker algorithm.
 */
internal class BuchheimWalkerNodeData {
    var ancestor: TreeNode? = null
    var thread: TreeNode? = null
    var number = 0
    var depth = 0
    var prelim = 0.0
    var modifier = 0.0
    var shift = 0.0
    var change = 0.0
}
