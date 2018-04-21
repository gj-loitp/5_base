package vn.loitp.app.activity.customviews.treeview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.treeview.BaseTreeAdapter;
import vn.loitp.views.treeview.TreeNode;
import vn.loitp.views.treeview.TreeView;

//https://github.com/Team-Blox/TreeView
public class TreeViewActivity extends BaseActivity {
    private int nodeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TreeView treeView = findViewById(R.id.treeview);

        BaseTreeAdapter adapter = new BaseTreeAdapter<ViewHolder>(this, R.layout.node) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(View view) {
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
                viewHolder.mTextView.setText(data.toString());
            }
        };
        treeView.setAdapter(adapter);

        // example tree
        TreeNode rootNode = new TreeNode(getNodeText());
        rootNode.addChild(new TreeNode(getNodeText()));
        final TreeNode child3 = new TreeNode(getNodeText());
        child3.addChild(new TreeNode(getNodeText()));
        final TreeNode child6 = new TreeNode(getNodeText());
        child6.addChild(new TreeNode(getNodeText()));
        child6.addChild(new TreeNode(getNodeText()));
        child3.addChild(child6);
        rootNode.addChild(child3);
        final TreeNode child4 = new TreeNode(getNodeText());
        child4.addChild(new TreeNode(getNodeText()));
        child4.addChild(new TreeNode(getNodeText()));
        rootNode.addChild(child4);

        adapter.setRootNode(rootNode);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_tree_view;
    }

    private String getNodeText() {
        return "Node " + nodeCount++;
    }

    private class ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.textView);
        }
    }
}
