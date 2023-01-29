package vn.loitp.a.cv.vp.easyFlip;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.wajahatkarim3.easyflipviewpager.CardFlipPageTransformer;

import java.util.ArrayList;

import vn.loitp.R;

public class PokerCardDemoActivity extends AppCompatActivity {

    ViewPager pokerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_poker_card_demo);

        pokerViewPager = findViewById(R.id.pokerViewPager);
        PokerPagerAdapter pagerAdapter = new PokerPagerAdapter(this);
        pokerViewPager.setAdapter(pagerAdapter);

        CardFlipPageTransformer cardFlipPageTransformer = new CardFlipPageTransformer();
        cardFlipPageTransformer.setScalable(false);
        cardFlipPageTransformer.setFlipOrientation(CardFlipPageTransformer.VERTICAL);
        pokerViewPager.setPageTransformer(true, cardFlipPageTransformer);

    }

    public class PokerPagerAdapter extends PagerAdapter {
        Context context;
        LayoutInflater mLayoutInflater;
        ArrayList<Object> pages = new ArrayList<>();

        public PokerPagerAdapter(Context context) {
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);

            pages.add(new Object());
            pages.add(new Object());
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        // This method should create the page for the given position passed to it as an argument.
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {

            View rootView = mLayoutInflater.inflate(R.layout.card_image_layout, container, false);
            AppCompatImageView imgCardSide = rootView.findViewById(R.id.imgCardSide);
            imgCardSide.setOnClickListener(view -> {
                if (position == 0) {
                    pokerViewPager.setCurrentItem(1, true);
                } else {
                    pokerViewPager.setCurrentItem(0, true);
                }
            });
            int[] sides = {R.drawable.poker_card_front, R.drawable.poker_card_back};
            imgCardSide.setImageResource(sides[position]);
            container.addView(rootView);
            return rootView;
        }

        // Removes the page from the container for the given position.
        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
