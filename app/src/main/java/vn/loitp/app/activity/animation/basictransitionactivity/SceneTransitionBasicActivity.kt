package vn.loitp.app.activity.animation.basictransitionactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_animation_scene_transition_basic.*
import vn.loitp.app.R

@LogTag("SceneTransitionBasicActivity")
@IsFullScreen(false)
class SceneTransitionBasicActivity : BaseFontActivity(), AdapterView.OnItemClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_scene_transition_basic
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews(){
        gridView.onItemClickListener = this
        val gridAdapter = GridAdapter()
        gridView.adapter = gridAdapter
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
                Pair(view.findViewById(R.id.textViewName),
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

        override fun getView(position: Int, v: View?, viewGroup: ViewGroup): View? {
            var view = v
            if (view == null) {
                view = layoutInflater.inflate(R.layout.view_row_item_grid_view, viewGroup, false)
            }

            val item = getItem(position)

            // Load the thumbnail image
            val imageViewItem = view?.findViewById<ImageView>(R.id.imageViewItem)
            LImageUtil.load(context = this@SceneTransitionBasicActivity, any = item.photoUrl, imageView = imageViewItem)

            // Set the TextView's contents
            val textViewName = view?.findViewById<TextView>(R.id.textViewName)
            textViewName?.text = item.name

            return view
        }
    }
}
