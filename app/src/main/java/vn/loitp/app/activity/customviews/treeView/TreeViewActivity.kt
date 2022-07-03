package vn.loitp.app.activity.customviews.treeView

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.treeview.BaseTreeAdapter
import com.loitpcore.views.treeview.TreeNode
import kotlinx.android.synthetic.main.activity_tree_view.*
import vn.loitp.app.R

// https://github.com/Team-Blox/TreeView
@LogTag("TreeViewActivity")
@IsFullScreen(false)
class TreeViewActivity : BaseFontActivity() {

    private var nodeCount = 0

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_tree_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val adapter: BaseTreeAdapter<*> =
            object : BaseTreeAdapter<ViewHolder>(this, R.layout.node) {
                override fun onCreateViewHolder(view: View): ViewHolder {
                    return ViewHolder(view)
                }

                override fun onBindViewHolder(viewHolder: ViewHolder?, data: Any?, position: Int) {
                    viewHolder?.textView?.text = data.toString()
                }
            }
        treeView.adapter = adapter

        // example tree
        val rootNode = TreeNode(nodeText)
        rootNode.addChild(TreeNode(nodeText))

        val child3 = TreeNode(nodeText)
        child3.addChild(TreeNode(nodeText))

        val child6 = TreeNode(nodeText)
        child6.addChild(TreeNode(nodeText))
        child6.addChild(TreeNode(nodeText))
        child3.addChild(child6)
        rootNode.addChild(child3)

        val child4 = TreeNode(nodeText)
        child4.addChild(TreeNode(nodeText))
        child4.addChild(TreeNode(nodeText))
        rootNode.addChild(child4)
        adapter.setRootNode(rootNode)
    }

    private val nodeText: String
        get() = "Node " + nodeCount++

    private inner class ViewHolder(view: View) {
        var textView: TextView = view.findViewById(R.id.textView)
    }
}
