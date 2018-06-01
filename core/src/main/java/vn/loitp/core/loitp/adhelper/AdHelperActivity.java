package vn.loitp.core.loitp.adhelper;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.AppUtils;

/**
 * Created by LENOVO on 5/31/2018.
 */

public class AdHelperActivity extends BaseActivity {
    private List<AdPage> adPageList = new ArrayList<>();
    private ImageButton btPrevScreen;
    private ImageButton btNextScreen;
    private TextView tvPage;
    private ViewPager viewPager;

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
        return R.layout.activity_ad_helper;
    }

    private void setupData() {
        AdPage adPage0 = new AdPage();
        adPage0.setUrlAd("https://c2.staticflickr.com/2/1748/41585991345_1e3d93a5d9_o.png");
        adPage0.setTitle("Quảng cáo là một phần quan trọng đối với một ứng dụng miễn phí");
        adPage0.setMsg("Để tạo ra một ứng dụng chất lượng, chúng tôi phải bỏ nhiều thời gian, công sức cho nó.\n\nTrong quá trình này, chúng tôi cần kinh phí để chi trả cho nhân sự, địa điểm và duy trì hệ thống.\n\nQuảng cáo là nguồn thu quan trọng nhất đối với chúng tôi để bù đắp vào các chi phí này.");
        adPageList.add(adPage0);

        AdPage adPage1 = new AdPage();
        adPage1.setUrlAd("https://c2.staticflickr.com/2/1732/41766077754_da53b9da82_o.png");
        String appName = AppUtils.getAppName();
        adPage1.setTitle("Không có quảng cáo, chúng ta sẽ không có " + appName);
        adPage1.setMsg("Việc trả phí để sử dụng một ứng dụng chưa phổ biến tại Việt Nam. Khi không có nguồn thu, chúng tôi khó có thể duy trì và nâng cấp ứng dụng.\n\nVà khi đó, bạn sẽ không được trải nghiệm những tính năng hữu ích mà " + appName + " đem lại.");
        adPageList.add(adPage1);

        AdPage adPage2 = new AdPage();
        adPage2.setUrlAd("https://c2.staticflickr.com/2/1734/41766077524_09572156d6_o.png");
        adPage2.setTitle("Quảng cáo có thể gây khó chịu.\nChúng tôi thấu hiểu điều này.");
        adPage2.setMsg("Hiện tại, " + appName + " sử dụng các hệ thống quảng cáo của một số bên thứ ba như Google, Facebook. Đôi khi, bạn sẽ nhìn thấy quảng cáo không phù hợp với mình.\n\nHệ thống quảng cáo cũng có thể bị lỗi và hiển thị khá nhiều, chúng tôi sẽ cố gắng giảm thiểu các sự cố như vậy trong thời gian sớm nhất.");
        adPageList.add(adPage2);

        AdPage adPage3 = new AdPage();
        adPage3.setUrlAd("https://c2.staticflickr.com/2/1723/41766077684_54c007d2db_o.png");
        adPage3.setTitle("Đội ngũ " + appName + " rất mong nhận được sự đồng cảm và hỗ trợ của bạn");
        adPage3.setMsg("Chúng tôi cần quảng cáo, giống như bạn cần " + appName + " cho cuộc sống thường nhật của mình.\n\nChúng tôi sẽ nỗ lực để khiến bạn ngày một hài lòng khi sử dụng " + appName + "\n\nXin chân thành cảm ơn.");
        adPageList.add(adPage3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        btPrevScreen = (ImageButton) findViewById(R.id.bt_prev_screen);
        btNextScreen = (ImageButton) findViewById(R.id.bt_next_screen);
        tvPage = (TextView) findViewById(R.id.tv_page);
        //LUIUtil.setTextShadow(tvPage);

        setupData();

        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                LActivityUtil.tranOut(activity);
            }
        });
        btPrevScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        btNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == (adPageList.size() - 1)) {
                    finish();
                    LActivityUtil.tranOut(activity);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SlidePagerAdapter());

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                tvPage.setText((position + 1) + "/" + adPageList.size());
                if (position == 0) {
                    btPrevScreen.setVisibility(View.INVISIBLE);
                } else if (position == adPageList.size() - 1) {
                    btNextScreen.setVisibility(View.INVISIBLE);
                } else {
                    btPrevScreen.setVisibility(View.VISIBLE);
                    btNextScreen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
        tvPage.setText((viewPager.getCurrentItem() + 1) + "/" + adPageList.size());
    }

    private class SlidePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            AdPage adPage = adPageList.get(position);

            LayoutInflater inflater = LayoutInflater.from(activity);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_photo_ad_helper, collection, false);

            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
            LImageUtil.load(activity, adPage.getUrlAd(), imageView);

            TextView tv = (TextView) layout.findViewById(R.id.tv);
            tv.setText(adPage.getTitle());
            LUIUtil.setTextShadow(tv, ContextCompat.getColor(activity, R.color.White));

            TextView tvMsg = (TextView) layout.findViewById(R.id.tv_msg);
            tvMsg.setText(adPage.getMsg());
            LUIUtil.setTextShadow(tvMsg, ContextCompat.getColor(activity, R.color.White));

            Button btOkay = (Button) layout.findViewById(R.id.bt_okay);
            if (position == adPageList.size() - 1) {
                btOkay.setVisibility(View.VISIBLE);
            } else {
                btOkay.setVisibility(View.GONE);
            }
            btOkay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    LActivityUtil.tranOut(activity);
                }
            });

            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return adPageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
