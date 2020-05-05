package vn.loitp.app.activity.animation.basictransitionactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import vn.loitp.app.R

class SceneTransitionBasicActivity : BaseFontActivity(), AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mGridView = findViewById<GridView>(R.id.gv)
        mGridView.onItemClickListener = this
        val mAdapter = GridAdapter()
        mGridView.adapter = mAdapter
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scene_transition_basic
    }

    override fun onItemClick(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        val item = adapterView.getItemAtPosition(position) as Item
        val intent = Intent(this, SceneTransitionBasicDetailActivity::class.java)
        intent.putExtra(SceneTransitionBasicDetailActivity.EXTRA_PARAM_ID, item.id)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,

                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                Pair(view.findViewById(R.id.imageViewItem),
                        SceneTransitionBasicDetailActivity.VIEW_NAME_HEADER_IMAGE),
                Pair(view.findViewById(R.id.textview_name),
                        SceneTransitionBasicDetailActivity.VIEW_NAME_HEADER_TITLE))
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle())
    }

    /**
     * [android.widget.BaseAdapter] which displays items.
     */
    private inner class GridAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return Item.ITEMS.size
        }

        override fun getItem(position: Int): Item {
            return Item.ITEMS[position]
        }

        override fun getItemId(position: Int): Long {
            return getItem(position).id.toLong()
        }

        override fun getView(position: Int, v: View?, viewGroup: ViewGroup): View {
            var view = v
            if (view == null) {
                view = layoutInflater.inflate(R.layout.item_grid_view, viewGroup, false)
            }

            val item = getItem(position)

            // Load the thumbnail image
            val image = view!!.findViewById<ImageView>(R.id.imageViewItem)
            LImageUtil.load(activity, item.photoUrl, image)

            // Set the TextView's contents
            val name = view.findViewById<TextView>(R.id.textview_name)
            name.text = item.name

            return view
        }
    }
}
