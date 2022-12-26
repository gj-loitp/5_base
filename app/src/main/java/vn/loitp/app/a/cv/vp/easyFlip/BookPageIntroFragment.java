package vn.loitp.app.a.cv.vp.easyFlip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vn.loitp.R;

public class BookPageIntroFragment extends Fragment {

    String title = "";
    String subtitle = "";
    int imageId;

    public static BookPageIntroFragment newInstance(String title, String subtitle, int imageId) {
        BookPageIntroFragment fragment = new BookPageIntroFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("subtitle", subtitle);
        args.putInt("imageId", imageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        imageId = getArguments().getInt("imageId", R.drawable.ic_launcher_background);
        title = getArguments().getString("title", "");
        subtitle = getArguments().getString("subtitle", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_page_layout, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        TextView txtTitle = rootView.findViewById(R.id.textView2);
        TextView txtSubTitle = rootView.findViewById(R.id.textView3);

        txtTitle.setText(title);
        txtSubTitle.setText(subtitle);
        imageView.setImageResource(imageId);

        if (imageId == R.drawable.all_about_reading) {
            rootView.setTag(21);
        } else {
            rootView.setTag(40);
        }

        return rootView;
    }
}
