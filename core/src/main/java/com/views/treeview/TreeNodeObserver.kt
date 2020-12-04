package com.views.treeview

internal interface TreeNodeObserver {
    fun notifyDataChanged(node: TreeNode?)
    fun notifyNodeAdded(node: TreeNode?, parent: TreeNode?)
    fun notifyNodeRemoved(node: TreeNode?, parent: TreeNode?)
}
