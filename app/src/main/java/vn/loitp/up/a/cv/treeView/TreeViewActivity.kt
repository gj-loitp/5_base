package vn.loitp.up.a.cv.treeView

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.treeView.BaseTreeAdapter
import com.loitp.views.treeView.TreeNode
import vn.loitp.R
import vn.loitp.databinding.ATreeViewBinding

// https://github.com/Team-Blox/TreeView
@LogTag("TreeViewActivity")
@IsFullScreen(false)
class TreeViewActivity : BaseActivityFont() {

    private var nodeCount = 0
    private lateinit var binding: ATreeViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATreeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = TreeViewActivity::class.java.simpleName
        }

        val adapter: BaseTreeAdapter<*> =
            object : BaseTreeAdapter<ViewHolder>(this, R.layout.l_v_node) {
                override fun onCreateViewHolder(view: View): ViewHolder {
                    return ViewHolder(view)
                }

                override fun onBindViewHolder(viewHolder: ViewHolder?, data: Any?, position: Int) {
                    viewHolder?.textView?.text = data.toString()
                }
            }
        binding.treeView.adapter = adapter

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
