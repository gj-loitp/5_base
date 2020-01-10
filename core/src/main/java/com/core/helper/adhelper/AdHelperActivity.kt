package com.core.helper.adhelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.R
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.utils.util.AppUtils
import com.views.button.roundedbutton.LRoundedButton
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_ad_helper.*
import java.util.*

class AdHelperActivity : BaseFontActivity() {
    private val adPageList = ArrayList<AdPage>()
    private var isEnglishLanguage: Boolean = false

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_ad_helper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false

        isEnglishLanguage = intent.getBooleanExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, false)

        setupData()

        btBack.setSafeOnClickListener {
            finish()
            LActivityUtil.tranOut(activity)
        }
        btPrevScreen.setSafeOnClickListener { viewPager.currentItem = viewPager.currentItem - 1 }
        btNextScreen.setSafeOnClickListener {
            if (viewPager.currentItem == adPageList.size - 1) {
                finish()
                LActivityUtil.tranOut(activity)
            } else {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        viewPager.adapter = SlidePagerAdapter()
        LUIUtil.setPullLikeIOSHorizontal(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //do nothing
            }

            override fun onPageSelected(position: Int) {
                tvPage.text = (position + 1).toString() + "/" + adPageList.size
                when (position) {
                    0 -> btPrevScreen.visibility = View.INVISIBLE
                    adPageList.size - 1 -> btNextScreen.visibility = View.INVISIBLE
                    else -> {
                        btPrevScreen.visibility = View.VISIBLE
                        btNextScreen.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                //do nothing
            }
        })
        tvPage.text = (viewPager.currentItem + 1).toString() + "/" + adPageList.size
    }

    private fun setupData() {
        val adPage0 = AdPage()
        adPage0.urlAd = "https://c2.staticflickr.com/2/1748/41585991345_1e3d93a5d9_o.png"
        if (isEnglishLanguage) {
            adPage0.title = "Advertising is an important part of a free app"
            adPage0.msg = "To create a quality application, we have to spend a lot of time, effort on it.\n\nIn this process, we need funds to pay for the personnel, location and maintenance of the system.\n\nAdvertising is the most important revenue source for us to make up for these costs."
        } else {
            adPage0.title = "Quảng cáo là một phần quan trọng đối với một ứng dụng miễn phí"
            adPage0.msg = "Để tạo ra một ứng dụng chất lượng, chúng tôi phải bỏ nhiều thời gian, công sức cho nó.\n\nTrong quá trình này, chúng tôi cần kinh phí để chi trả cho nhân sự, địa điểm và duy trì hệ thống.\n\nQuảng cáo là nguồn thu quan trọng nhất đối với chúng tôi để bù đắp vào các chi phí này."
        }
        adPageList.add(adPage0)

        val adPage1 = AdPage()
        adPage1.urlAd = "https://c2.staticflickr.com/2/1732/41766077754_da53b9da82_o.png"
        val appName = AppUtils.getAppName()
        if (isEnglishLanguage) {
            adPage1.title = "Without ads, we will not have $appName"
            adPage1.msg = "Paying to use an application is not yet common in Vietnam. Without revenue, it is difficult to maintain and upgrade applications.\n\nAnd then, you will not experience the useful features that $appName brings."
        } else {
            adPage1.title = "Không có quảng cáo, chúng ta sẽ không có $appName"
            adPage1.msg = "Việc trả phí để sử dụng một ứng dụng chưa phổ biến tại Việt Nam. Khi không có nguồn thu, chúng tôi khó có thể duy trì và nâng cấp ứng dụng.\n\nVà khi đó, bạn sẽ không được trải nghiệm những tính năng hữu ích mà $appName đem lại."
        }
        adPageList.add(adPage1)

        val adPage2 = AdPage()
        adPage2.urlAd = "https://c2.staticflickr.com/2/1734/41766077524_09572156d6_o.png"
        if (isEnglishLanguage) {
            adPage2.title = "Ads can be annoying.\nWe understand this."
            adPage2.msg = "Currently,$appName uses the advertising systems of some third parties such as Google, Facebook. Occasionally, you'll see ads that are not right for you.\n\nAd systems can also be buggy and display a lot, we will try to minimize such incidents as soon as possible."
        } else {
            adPage2.title = "Quảng cáo có thể gây khó chịu.\nChúng tôi thấu hiểu điều này."
            adPage2.msg = "Hiện tại, $appName sử dụng các hệ thống quảng cáo của một số bên thứ ba như Google, Facebook. Đôi khi, bạn sẽ nhìn thấy quảng cáo không phù hợp với mình.\n\nHệ thống quảng cáo cũng có thể bị lỗi và hiển thị khá nhiều, chúng tôi sẽ cố gắng giảm thiểu các sự cố như vậy trong thời gian sớm nhất."
        }
        adPageList.add(adPage2)

        val adPage3 = AdPage()
        adPage3.urlAd = "https://c2.staticflickr.com/2/1723/41766077684_54c007d2db_o.png"
        if (isEnglishLanguage) {
            adPage3.title = "The team $appName is looking forward to receiving your sympathy and support"
            adPage3.msg = "We need ads, just like you need $appName for your daily life.\n\nWe will work to make you happy when using $appName\n\nSincerely thank."
        } else {
            adPage3.title = "Đội ngũ $appName rất mong nhận được sự đồng cảm và hỗ trợ của bạn"
            adPage3.msg = "Chúng tôi cần quảng cáo, giống như bạn cần $appName cho cuộc sống thường nhật của mình.\n\nChúng tôi sẽ nỗ lực để khiến bạn ngày một hài lòng khi sử dụng $appName\n\nXin chân thành cảm ơn."
        }
        adPageList.add(adPage3)
    }

    private inner class SlidePagerAdapter : PagerAdapter() {

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val adPage = adPageList[position]
            val inflater = LayoutInflater.from(activity)
            val layout = inflater.inflate(R.layout.l_item_photo_ad_helper, collection, false) as ViewGroup

            val imageView = layout.findViewById<ImageView>(R.id.imageView)
            val tv = layout.findViewById<TextView>(R.id.tv)
            val tvMsg = layout.findViewById<TextView>(R.id.tvMsg)
            val btOkay = layout.findViewById<LRoundedButton>(R.id.btOkay)

            adPage.urlAd?.let {
                LImageUtil.load(activity, it, imageView)
            }

            tv.text = adPage.title
            LUIUtil.setTextShadow(tv, ContextCompat.getColor(activity, R.color.White))

            tvMsg.text = adPage.msg
            LUIUtil.setTextShadow(tvMsg, ContextCompat.getColor(activity, R.color.White))

            if (isEnglishLanguage) {
                btOkay.text = "I understand"
            } else {
                btOkay.text = "Tôi đã hiểu"
            }
            if (position == adPageList.size - 1) {
                btOkay.visibility = View.VISIBLE
            } else {
                btOkay.visibility = View.GONE
            }
            btOkay.setSafeOnClickListener {
                finish()
                LActivityUtil.tranOut(activity)
            }

            collection.addView(layout)
            return layout
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return adPageList.size
        }

        override fun isViewFromObject(view: View, any: Any): Boolean {
            return view === any
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}
