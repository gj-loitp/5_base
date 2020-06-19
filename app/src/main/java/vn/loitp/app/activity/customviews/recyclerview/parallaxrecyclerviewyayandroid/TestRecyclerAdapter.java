package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LImageUtil;
import com.views.recyclerview.parallaxyay.ParallaxViewHolder;

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;

public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    /*
    private int[] imageIds = new int[]{R.mipmap.test_image_1,
            R.mipmap.test_image_2, R.mipmap.test_image_3,
            R.mipmap.test_image_4, R.mipmap.test_image_5};
    */

    /*private String[] imageUrls = new String[]{
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_3.png",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_4.jpg",
            "http://yayandroid.com/data/github_library/parallax_listview/test_image_5.png",
    };*/

    TestRecyclerAdapter(Context context, Callback callback) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.row_parrallax_yayandroid, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // viewHolder.getBackgroundImage().setImageResource(imageIds[position % imageIds.length]);
        LImageUtil.INSTANCE.load(context, FakeData.Companion.getInstance().getStringList().get(position), viewHolder.getBackgroundImage());
        viewHolder.getTextView().setText("Row " + position);

        viewHolder.rootView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onClick(position);
            }
        });
        viewHolder.rootView.setOnLongClickListener(v -> {
            if (callback != null) {
                callback.onLongClick(position);
            }
            return true;
        });

        // # CAUTION:
        // Important to call this method
        viewHolder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return FakeData.Companion.getInstance().getStringList().size();
    }

    /**
     * # CAUTION:
     * ViewHolder must extend from ParallaxViewHolder
     */
    public static class ViewHolder extends ParallaxViewHolder {

        private final TextView textView;
        private final RelativeLayout rootView;

        public ViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.label);
            rootView = v.findViewById(R.id.rootView);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public interface Callback {
        void onClick(int pos);

        void onLongClick(int pos);
    }

    private Callback callback;
}