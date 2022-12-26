package com.loitp.views.treeView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface TreeNodeObserver {
    fun notifyDataChanged(node: TreeNode?)
    fun notifyNodeAdded(
        node: TreeNode?,
        parent: TreeNode?
    )

    fun notifyNodeRemoved(
        node: TreeNode?,
        parent: TreeNode?
    )
}
