package com.core.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RectShape
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.R
import com.core.common.Constants
import com.data.AdmobData
import com.google.ads.interactivemedia.v3.internal.it
import com.google.android.gms.ads.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.interfaces.CallbackPull
import com.interfaces.CallbackRecyclerView
import com.utils.util.ConvertUtils
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.calligraphy3.CalligraphyUtils
import io.github.inflationx.viewpump.ViewPump
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * File created on 17/7/2019.
 *
 * @author loitp
 */
class LUIUtil {
    companion object {
        private val logTag = LUIUtil::class.java.simpleName

        private var lastOffset = 0.0f
        private var isUp = false

        private val colors = intArrayOf(R.color.lightBlue, R.color.lightCoral, R.color.lightCyan,
                R.color.lightGoldenrodYellow, R.color.lightGreen, R.color.lightGrey, R.color.lightPink,
                R.color.lightSalmon, R.color.lightSeaGreen, R.color.lightSlateGray, R.color.lightSteelBlue,
                R.color.lightYellow, R.color.lightSkyBlue)

        var fontForAll: String? = null
            set(fontForAll) {
                field = fontForAll
                ViewPump.init(ViewPump.builder()
                        .addInterceptor(CalligraphyInterceptor(
                                CalligraphyConfig.Builder()
                                        .setDefaultFontPath(fontForAll)
                                        .setFontAttrId(R.attr.fontPath)
                                        .build()))
                        .build())
            }

//        fun createAdBanner(activity: Activity, adViewId: Int): AdView {
//            val adView = activity.findViewById<AdView>(adViewId)
//            createAdBanner(adView)
//            return adView
//        }

        private fun getListTestDevice(): ArrayList<String> {
            val listTestDevice = ArrayList<String>()
            listTestDevice.add(AdRequest.DEVICE_ID_EMULATOR)
            listTestDevice.add(Constants.TEST_0)
            listTestDevice.add(Constants.TEST_1)
            listTestDevice.add(Constants.TEST_2)
            listTestDevice.add(Constants.TEST_3)
            listTestDevice.add(Constants.TEST_4)
            listTestDevice.add(Constants.TEST_5)
            listTestDevice.add(Constants.TEST_6)
            listTestDevice.add(Constants.TEST_7)
            listTestDevice.add(Constants.TEST_8)
            listTestDevice.add(Constants.TEST_9)
            listTestDevice.add(Constants.TEST_10)
            listTestDevice.add(Constants.TEST_11)
            listTestDevice.add(Constants.TEST_12)
            listTestDevice.add(Constants.TEST_13)
            return listTestDevice
        }

        fun createAdBanner(adView: AdView): AdView {
            val requestConfiguration = RequestConfiguration.Builder()
                    .setTestDeviceIds(getListTestDevice())
                    .build()
            MobileAds.setRequestConfiguration(requestConfiguration)

            adView.loadAd(AdRequest.Builder().build())
            return adView
        }

        fun createAdFull(context: Context): InterstitialAd {
            val interstitial = InterstitialAd(context)
            interstitial.adUnitId = AdmobData.instance.idAdmobFull

            val requestConfiguration = RequestConfiguration.Builder()
                    .setTestDeviceIds(getListTestDevice())
                    .build()
            MobileAds.setRequestConfiguration(requestConfiguration)

            val adRequest = AdRequest.Builder().build()
            interstitial.loadAd(adRequest)
            return interstitial
        }

        @JvmOverloads
        fun displayInterstitial(interstitial: InterstitialAd?, maxNumber: Int = 100) {
            interstitial?.let { i ->
                if (i.isLoaded) {
                    val r = Random()
                    val x = r.nextInt(100)
                    if (x < maxNumber) {
                        i.show()
                    } else {
                        //don't use LLog here
                        Log.d("interstitial", "displayInterstitial: interstitial isLoaded() but $x > $maxNumber")
                    }
                } else {
                    //don't use LLog here
                    Log.d("interstitial", "displayInterstitial: interstitial !isLoaded()")
                }
            }
        }

        /*
         * set text marquee
         */
        fun setMarquee(tv: TextView?, text: String?) {
            tv?.let { t ->
                t.text = text
                setMarquee(t)
            }
        }

        fun setMarquee(tv: TextView?) {
            tv?.let {
                it.isSelected = true
                it.ellipsize = TextUtils.TruncateAt.MARQUEE
                it.isSingleLine = true
                it.marqueeRepeatLimit = -1//no limit loop
            }
        }

        fun createGradientDrawableWithRandomColor(): GradientDrawable {
            val color = LStoreUtil.randomColor
            val gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(color)
            gradientDrawable.cornerRadius = 0f
            gradientDrawable.setStroke(1, color)
            return gradientDrawable
        }

        fun createGradientDrawableWithColor(colorMain: Int, colorStroke: Int): GradientDrawable {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(colorMain)
            gradientDrawable.cornerRadius = 90f
            gradientDrawable.setStroke(3, colorStroke)
            return gradientDrawable
        }

        fun setCircleViewWithColor(view: View, colorMain: Int, colorStroke: Int) {
            try {
                view.setBackgroundDrawable(createGradientDrawableWithColor(colorMain, colorStroke))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun setGradientBackground(v: View) {
            val layers = arrayOfNulls<Drawable>(1)
            val sf = object : ShapeDrawable.ShaderFactory() {
                override fun resize(width: Int, height: Int): Shader {
                    return LinearGradient(0f, 0f, 0f, v.height.toFloat(), intArrayOf(LStoreUtil.randomColor,
                            LStoreUtil.randomColor, LStoreUtil.randomColor, LStoreUtil.randomColor),
                            floatArrayOf(0f, 0.49f, 0.50f, 1f), Shader.TileMode.CLAMP)
                }
            }
            val p = PaintDrawable()
            p.shape = RectShape()
            p.shaderFactory = sf
            p.setCornerRadii(floatArrayOf(5f, 5f, 5f, 5f, 0f, 0f, 0f, 0f))
            layers[0] = p
            val composite = LayerDrawable(layers)
            v.setBackgroundDrawable(composite)
        }

        @Suppress("DEPRECATION")
        fun setTextFromHTML(textView: TextView?, bodyData: String) {
            textView?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    it.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    it.text = Html.fromHtml(bodyData)
                }
            }
        }

        fun setImageFromAsset(fileName: String, imageView: ImageView?) {
            imageView?.let { iv ->
                run {
                    val drawable: Drawable?
                    var stream: InputStream? = null
                    try {
                        stream = LAppResource.application.assets.open("img/$fileName")
                        drawable = Drawable.createFromStream(stream, null)
                        drawable?.let {
                            iv.setImageDrawable(it)
                        }
                    } catch (e: Exception) {
                        Log.e(logTag, "setImageFromAsset: $e")
                        e.printStackTrace()
                    } finally {
                        try {
                            stream?.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            }
        }

        fun fixSizeTabLayout(context: Context, tabLayout: TabLayout, titleList: Array<String>) {
            if (titleList.size > 3) {
                tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            } else {
                tabLayout.tabMode = TabLayout.MODE_FIXED
                tabLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            }
            //settext allcap = false
            /*for (int tabIndex = 0; tabIndex < tabLayout.getTabCount(); tabIndex++) {
                TextView tabTextView = (TextView) (((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tabIndex)).getChildAt(1));
                tabTextView.setAllCaps(false);
                setTextAppearance(context, tabTextView, android.R.style.TextAppearance_Medium);
            }*/
        }

        @Suppress("DEPRECATION")
        fun setTextAppearance(context: Context, textView: TextView, resId: Int) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(context, resId)
            } else {
                textView.setTextAppearance(resId)
            }
        }

        fun setDelay(mls: Int, runnable: Runnable) {
            val handler = Handler()
            handler.postDelayed({ runnable.run() }, mls.toLong())
        }

        fun setSoftInputMode(activity: Activity, mode: Int) {
            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            activity.window.setSoftInputMode(mode)
        }

        fun setLastCursorEditText(editText: EditText?) {
            editText?.let {
                if (it.text.toString().isNotEmpty()) {
                    it.setSelection(it.text.length)
                }
            }
        }

        fun setColorForSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout?) {
            swipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary, R.color.vip1, R.color.vip2,
                    R.color.vip3, R.color.vip4, R.color.vip5)
        }

        fun setProgressViewOffset(swipeRefreshLayout: SwipeRefreshLayout?, topMargin: Int) {
            swipeRefreshLayout?.setProgressViewOffset(false, 0, topMargin)
        }

        fun setTextShadow(textView: TextView?, color: Int = if (isDarkTheme()) {
            Color.BLACK
        } else {
            Color.WHITE
        }) {
            textView?.setShadowLayer(1f, // radius
                    1f, // dx
                    1f, // dy
                    color // shadow color
            )
        }

//        fun setTextShadow(textView: TextView?) {
//            textView?.setShadowLayer(1f, // radius
//                    1f, // dx
//                    1f, // dy
//                    Color.BLACK // shadow color
//            )
//        }

        fun setTextBold(textBold: TextView) {
            textBold.setTypeface(null, Typeface.BOLD)
        }

        fun printBeautyJson(o: Any, textView: TextView?) {
            textView?.let {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(o)
                it.text = json
            }
        }

        @SuppressLint("SetTextI18n")
        fun printBeautyJson(o: Any, textView: TextView?, tag: String) {
            textView?.let {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.toJson(o)
                it.text = "$tag :\n$json"
            }
        }

        fun setPullLikeIOSVertical(recyclerView: RecyclerView) {
            //guide: https://github.com/EverythingMe/overscroll-decor

            // Horizontal
            //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

            // Vertical
            OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        }

        fun setPullLikeIOSHorizontal(recyclerView: RecyclerView) {
            //guide: https://github.com/EverythingMe/overscroll-decor

            // Horizontal
            OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
        }

        fun setPullLikeIOSHorizontal(viewPager: ViewPager, callbackPull: CallbackPull?) {
            //guide: https://github.com/EverythingMe/overscroll-decor
            val mDecor = OverScrollDecoratorHelper.setUpOverScroll(viewPager)
            callbackPull?.let {
                mDecor.setOverScrollUpdateListener { decor, state, offset ->
                    //val view = decor.view
                    when {
                        offset > 0 -> {
                            // 'view' is currently being over-scrolled from the top.
                            lastOffset = offset
                            isUp = true
                        }
                        offset < 0 -> {
                            // 'view' is currently being over-scrolled from the bottom.
                            lastOffset = offset
                            isUp = false
                        }
                        else -> {
                            // No over-scroll is in-effect.
                            // This is synonymous with having (state == STATE_IDLE).
                            if (isUp) {
                                if (lastOffset > 1.8f) {
                                    callbackPull.onUpOrLeftRefresh(lastOffset)
                                    LSoundUtil.startMusicFromAsset("ting.ogg")
                                } else {
                                    callbackPull.onUpOrLeft(lastOffset)
                                }
                            } else {
                                if (lastOffset < -1.8f) {
                                    callbackPull.onDownOrRightRefresh(lastOffset)
                                } else {
                                    callbackPull.onDownOrRight(lastOffset)
                                }
                            }
                            lastOffset = 0f
                            isUp = false
                        }
                    }
                }
            }
        }

        fun setPullLikeIOSVertical(recyclerView: RecyclerView, callbackPull: CallbackPull?) {
            val mDecor = OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
            if (callbackPull != null) {
                /*decor.setOverScrollStateListener(new IOverScrollStateListener() {
                    @Override
                    public void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState) {
                        switch (newState) {
                            case STATE_IDLE:
                                // No over-scroll is in effect.
                                callback.onIdle();
                                break;
                            case STATE_DRAG_START_SIDE:
                                // Dragging started at the left-end.
                                callback.onDragStarSide();
                                break;
                            case STATE_DRAG_END_SIDE:
                                // Dragging started at the right-end.
                                callback.onDragEndSide();
                                break;
                            case STATE_BOUNCE_BACK:
                                if (oldState == STATE_DRAG_START_SIDE) {
                                    // Dragging stopped -- view is starting to bounce back from the *left-end* onto natural position.
                                } else { // i.e. (oldState == STATE_DRAG_END_SIDE)
                                    // View is starting to bounce back from the *right-end*.
                                }
                                break;
                        }
                    }
                });*/
                mDecor.setOverScrollUpdateListener { decor, state, offset ->
                    //val view = decor.view
                    when {
                        offset > 0 -> {
                            // 'view' is currently being over-scrolled from the top.
                            lastOffset = offset
                            isUp = true
                        }
                        offset < 0 -> {
                            // 'view' is currently being over-scrolled from the bottom.
                            lastOffset = offset
                            isUp = false
                        }
                        else -> {
                            // No over-scroll is in-effect.
                            // This is synonymous with having (state == STATE_IDLE).
                            if (isUp) {
                                if (lastOffset > 1.8f) {
                                    callbackPull.onUpOrLeftRefresh(lastOffset)
                                    LSoundUtil.startMusicFromAsset("ting.ogg")
                                } else {
                                    callbackPull.onUpOrLeft(lastOffset)
                                }
                            } else {
                                if (lastOffset < -1.8f) {
                                    callbackPull.onDownOrRightRefresh(lastOffset)
                                } else {
                                    callbackPull.onDownOrRight(lastOffset)
                                }
                            }
                            lastOffset = 0f
                            isUp = false
                        }
                    }
                }
            }
        }

        fun setPullLikeIOSHorizontal(viewPager: ViewPager) {
            //guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(viewPager)
        }

        fun setPullLikeIOSVertical(scrollView: ScrollView) {
            //guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(scrollView)
        }

        fun setPullLikeIOSVertical(listView: ListView) {
            //guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(listView)
        }

        fun setPullLikeIOSVertical(gridView: GridView) {
            //guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(gridView)
        }

        fun setPullLikeIOSVertical(scrollView: HorizontalScrollView) {
            //guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(scrollView)
        }

        fun setPullLikeIOSVertical(view: View) {
            //guide: https://github.com/EverythingMe/overscroll-decor

            // Horizontal
            //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

            // Vertical
            OverScrollDecoratorHelper.setUpStaticOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        }

        fun getColor(): Int {
            val random = Random()
            val c = random.nextInt(colors.size)
            return LAppResource.getColor(colors[c])
        }

        //it.imeOptions = EditorInfo.IME_ACTION_SEARCH
        fun setImeiActionEditText(editText: EditText?, imeOptions: Int, runnable: Runnable?) {
            editText?.let {
                it.imeOptions = imeOptions
                it.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == imeOptions) {
                        runnable?.run()
                        return@OnEditorActionListener true
                    }
                    false
                })
            }
        }

        fun setImeiActionSearch(editText: EditText?, actionSearch: Runnable?) {
            editText?.let {
                it.imeOptions = EditorInfo.IME_ACTION_SEARCH
                it.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        actionSearch?.run()
                        return@OnEditorActionListener true
                    }
                    false
                })
            }
        }

        fun setColorProgressBar(progressBar: ProgressBar?, color: Int) {
            progressBar?.indeterminateDrawable?.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }

        fun setProgressBarVisibility(progressBar: ProgressBar?, visibility: Int) {
            progressBar?.visibility = visibility
        }

        fun setColorSeekBar(seekBar: SeekBar?, color: Int) {
            seekBar?.let {
                it.progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                it.thumb.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }

        //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 25);//25dp
        //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_SP, 25);//25sp
        //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PX, 25);//25px
        //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PT, 25);//25points
//        fun setTextSize(textView: TextView?, typedValue: Int, size: Int) {
//            if (size < 0) {
//                return
//            }
//            textView?.setTextSize(typedValue, size.toFloat())
//        }

        fun setTextSize(textView: TextView?, size: Float) {
            if (size < 0 || textView == null) {
                return
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }

        fun setMargins(view: View?, leftPx: Int, topPx: Int, rightPx: Int, bottomPx: Int) {
            view?.let {
                if (it.layoutParams is ViewGroup.MarginLayoutParams) {
                    val p = it.layoutParams as ViewGroup.MarginLayoutParams
                    p.setMargins(leftPx, topPx, rightPx, bottomPx)
                    it.requestLayout()
                }
            }
        }

        fun setMarginsDp(view: View?, leftDp: Int, topDp: Int, rightDp: Int, bottomDp: Int) {
            view?.let {
                if (it.layoutParams is ViewGroup.MarginLayoutParams) {
                    val p = it.layoutParams as ViewGroup.MarginLayoutParams
                    p.setMargins(ConvertUtils.dp2px(leftDp.toFloat()), ConvertUtils.dp2px(topDp.toFloat()), ConvertUtils.dp2px(rightDp.toFloat()), ConvertUtils.dp2px(bottomDp.toFloat()))
                    it.requestLayout()
                }
            }
        }

        fun changeTabsFont(tabLayout: TabLayout?, fontName: String) {
            tabLayout?.let {
                val vg = it.getChildAt(0) as ViewGroup
                val tabsCount = vg.childCount
                for (j in 0 until tabsCount) {
                    val vgTab = vg.getChildAt(j) as ViewGroup
                    val tabChildsCount = vgTab.childCount
                    for (i in 0 until tabChildsCount) {
                        val tabViewChild = vgTab.getChildAt(i)
                        if (tabViewChild is TextView) {
                            CalligraphyUtils.applyFontToTextView(it.context, tabViewChild, fontName)
                        }
                    }
                }
            }
        }

        fun setRandomBackground(view: View?) {
            view?.let {
                val r = LStoreUtil.getRandomNumber(Constants.ARR_RANDOM_BKG.size)
                val bkg = Constants.ARR_RANDOM_BKG[r]
                it.setBackgroundResource(bkg)
            }
        }

        fun setNavMenuItemThemeColors(navigationView: NavigationView, colorDefault: Int, color: Int) {
            //Setting default colors for menu item Text and Icon

            //Defining ColorStateList for menu item Text
            val navMenuTextList = ColorStateList(arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf(android.R.attr.state_enabled), intArrayOf(android.R.attr.state_pressed), intArrayOf(android.R.attr.state_focused), intArrayOf(android.R.attr.state_pressed)), intArrayOf(color, colorDefault, colorDefault, colorDefault, colorDefault))

            //Defining ColorStateList for menu item Icon
            val navMenuIconList = ColorStateList(arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf(android.R.attr.state_enabled), intArrayOf(android.R.attr.state_pressed), intArrayOf(android.R.attr.state_focused), intArrayOf(android.R.attr.state_pressed)), intArrayOf(color, colorDefault, colorDefault, colorDefault, colorDefault))
            navigationView.itemTextColor = navMenuTextList
            navigationView.itemIconTintList = navMenuIconList
        }

        fun getAllChildren(v: View): ArrayList<View> {
            if (v !is ViewGroup) {
                val viewArrayList = ArrayList<View>()
                viewArrayList.add(v)
                return viewArrayList
            }
            val result = ArrayList<View>()
            for (i in 0 until v.childCount) {
                val child = v.getChildAt(i)
                val viewArrayList = ArrayList<View>()
                viewArrayList.add(v)
                viewArrayList.addAll(getAllChildren(child))
                result.addAll(viewArrayList)
            }
            return result
        }

        fun getWidthOfView(view: View): Int {
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            return view.measuredWidth
        }

        fun getHeightOfView(view: View): Int {
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            return view.measuredHeight
        }

        //playYoutube(activity, "http://www.youtube.com/watch?v=Hxy8BZGQ5Jo");
        fun playYoutube(activity: Activity?, url: String?) {
            activity?.let { a ->
                if (url.isNullOrEmpty()) {
                    return
                }
                a.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                LActivityUtil.tranIn(a)
            }
        }

        fun playYoutubeWithId(activity: Activity?, id: String) {
            activity?.let { a ->
                playYoutube(activity = a, url = "http://www.youtube.com/watch?v=$id")
            }
        }

        @SuppressLint("ObsoleteSdkInt")
        fun setRipple(context: Context?, view: View) {
            context?.let { c ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    val outValue = TypedValue()
                    c.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                    view.setBackgroundResource(outValue.resourceId)
                }
            }
        }

        @SuppressLint("RestrictedApi")
        fun setCheckBoxColor(checkBox: AppCompatCheckBox, uncheckedColor: Int, checkedColor: Int) {
            val colorStateList = ColorStateList(
                    arrayOf(intArrayOf(-android.R.attr.state_checked), //unchecked
                            intArrayOf(android.R.attr.state_checked)  //checked
                    ),
                    intArrayOf(uncheckedColor, checkedColor)
            )
            checkBox.supportButtonTintList = colorStateList
        }

        fun setChangeStatusBarTintToDark(window: Window, shouldChangeStatusBarTintToDark: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val decor = window.decorView
                if (shouldChangeStatusBarTintToDark) {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    // We want to change tint color to white again.
                    // You can also record the flags in advance so that you can turn UI back completely if
                    // you have set other flags before, such as translucent or full screen.
                    decor.systemUiVisibility = 0
                }
            }
        }

        fun setSizeOfView(
                view: View? = null,
                width: Int? = null,
                height: Int? = null
        ) {
            view?.let { v ->
                width?.let {
                    v.layoutParams.width = width
                }
                height?.let {
                    v.layoutParams.height = height
                }
                v.requestLayout()
            }
        }

        //ViewGroup.LayoutParams.MATCH_PARENT
//        fun setSize(view: View, w: Int, h: Int) {
//            view.layoutParams.width = w
//            view.layoutParams.height = h
//            view.requestLayout()
//        }

        fun setScrollChange(recyclerView: RecyclerView, callbackRecyclerView: CallbackRecyclerView) {
            var isScrollDown = false
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (!recyclerView.canScrollVertically(1)) {
                            callbackRecyclerView.onBottom()
                        }
                        if (!recyclerView.canScrollVertically(-1)) {
                            callbackRecyclerView.onTop()
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy < 0) {
                        if (isScrollDown) {
                            isScrollDown = false
                            callbackRecyclerView.onScrolled(isScrollDown)
                        }
                    } else if (dy > 0) {
                        if (!isScrollDown) {
                            isScrollDown = true
                            callbackRecyclerView.onScrolled(isScrollDown)
                        }
                    }
                }
            })
        }

        fun addTextChangedListener(editText: EditText?, delayInMls: Long, afterTextChanged: (String) -> Unit) {
            if (delayInMls > 0) {
                editText?.let { et ->
                    et.addTextChangedListener(object : TextWatcher {
                        private var timer = Timer()
                        override fun afterTextChanged(editable: Editable?) {
                            timer.cancel()
                            timer = Timer()
                            timer.schedule(object : TimerTask() {
                                override fun run() {
                                    editable?.let { e ->
                                        et.post {
                                            afterTextChanged.invoke(e.toString())
                                        }
                                    }
                                }
                            }, delayInMls)
                        }

                        override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }
                    }
                    )
                }
            }
        }

        fun ScrollView.scrollToBottom() {
            val lastChild = getChildAt(childCount - 1)
            val bottom = lastChild.bottom + paddingBottom
            val delta = bottom - (scrollY + height)
            smoothScrollBy(0, delta)
        }

        fun NestedScrollView.scrollToBottom() {
            val lastChild = getChildAt(childCount - 1)
            val bottom = lastChild.bottom + paddingBottom
            val delta = bottom - (scrollY + height)
            smoothScrollBy(0, delta)
        }

        fun isDarkTheme(): Boolean {
            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        }

        fun setDarkTheme(isDarkTheme: Boolean) {
            if (isDarkTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
