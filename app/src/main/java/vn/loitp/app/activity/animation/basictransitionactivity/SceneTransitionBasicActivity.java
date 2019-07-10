package vn.loitp.app.activity.animation.basictransitionactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;

import loitp.basemaster.R;

public class SceneTransitionBasicActivity extends BaseFontActivity implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGridView = (GridView) findViewById(R.id.gv);
        mGridView.setOnItemClickListener(this);
        mAdapter = new GridAdapter();
        mGridView.setAdapter(mAdapter);
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
        return R.layout.activity_scene_transition_basic;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Item item = (Item) adapterView.getItemAtPosition(position);

        // Construct an Intent as normal
        Intent intent = new Intent(this, SceneTransitionBasicDetailActivity.class);
        intent.putExtra(SceneTransitionBasicDetailActivity.EXTRA_PARAM_ID, item.getId());

        // BEGIN_INCLUDE(start_activity)
        /**
         * Now create an {@link android.app.ActivityOptions} instance using the
         * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory
         * method.
         */
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,

                // Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<View, String>(view.findViewById(R.id.imageview_item),
                        SceneTransitionBasicDetailActivity.VIEW_NAME_HEADER_IMAGE),
                new Pair<View, String>(view.findViewById(R.id.textview_name),
                        SceneTransitionBasicDetailActivity.VIEW_NAME_HEADER_TITLE));

        // Now we can start the Activity, providing the activity options as a bundle
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        // END_INCLUDE(start_activity)
    }

    /**
     * {@link android.widget.BaseAdapter} which displays items.
     */
    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Item.ITEMS.length;
        }

        @Override
        public Item getItem(int position) {
            return Item.ITEMS[position];
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.item_grid_view, viewGroup, false);
            }

            final Item item = getItem(position);

            // Load the thumbnail image
            ImageView image = (ImageView) view.findViewById(R.id.imageview_item);
            LImageUtil.load(activity, item.getPhotoUrl(), image);

            // Set the TextView's contents
            TextView name = (TextView) view.findViewById(R.id.textview_name);
            name.setText(item.getName());

            return view;
        }
    }
}
