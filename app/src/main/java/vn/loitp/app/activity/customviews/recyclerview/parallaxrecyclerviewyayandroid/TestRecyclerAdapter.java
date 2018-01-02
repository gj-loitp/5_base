package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid;

/**
 * Created by www.muathu@gmail.com on 1/2/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxViewHolder;

/**
 * Created by yahyabayramoglu on 14/04/15.
 */
public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    /*
    private int[] imageIds = new int[]{R.mipmap.test_image_1,
            R.mipmap.test_image_2, R.mipmap.test_image_3,
            R.mipmap.test_image_4, R.mipmap.test_image_5};
    */

    private String[] imageUrls = new String[]{
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_3.png",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_4.jpg",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_5.png",
    };

    public TestRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.row_parrallax_yayandroid, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // viewHolder.getBackgroundImage().setImageResource(imageIds[position % imageIds.length]);
        LImageUtil.load((Activity) context, imageUrls[position % imageUrls.length], viewHolder.getBackgroundImage());
        viewHolder.getTextView().setText("Row " + position);

        // # CAUTION:
        // Important to call this method
        viewHolder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    public static class ViewHolder extends ParallaxViewHolder {

        private final TextView textView;

        public ViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.label);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }

        public TextView getTextView() {
            return textView;
        }
    }


}