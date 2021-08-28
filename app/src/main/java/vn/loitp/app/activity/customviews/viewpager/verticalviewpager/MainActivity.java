package vn.loitp.app.activity.customviews.viewpager.verticalviewpager;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.views.viewpager.vertical.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager viewPager;
    private Context context;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        viewPager = findViewById(R.id.viewPager);
        addData();
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), context, stringList));
    }

    private void addData() {
        for (int i = 0; i < 8; i++) {
            stringList.add(String.valueOf(i));
        }
    }
}
