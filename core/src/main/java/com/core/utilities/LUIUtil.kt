package com.core.utilities

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
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.core.common.Constants
import com.data.AdmobData
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.utils.util.ConvertUtils
import com.views.overscroll.OverScrollDecoratorHelper
import loitp.core.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyUtils
import java.io.InputStream
import java.util.*

/**
 * File created on 17/7/2019.
 *
 * @author loitp
 */
object LUIUtil {
    private val TAG = LUIUtil::class.java.simpleName

    private var lastOffset = 0.0f
    private var isUp = false

    private val colors = intArrayOf(R.color.LightBlue, R.color.LightCoral, R.color.LightCyan,
            R.color.LightGoldenrodYellow, R.color.LightGreen, R.color.LightGrey, R.color.LightPink,
            R.color.LightSalmon, R.color.LightSeaGreen, R.color.LightSlateGray, R.color.LightSteelBlue,
            R.color.LightYellow, R.color.LightSkyBlue)

    var fontForAll: String? = null
        set(fontForAll) {
            field = fontForAll
            CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setDefaultFontPath(fontForAll).setFontAttrId(R.attr.fontPath).build())
        }

    fun createAdBanner(activity: Activity, adViewId: Int): AdView {
        val adView = activity.findViewById<AdView>(adViewId)
        createAdBanner(adView)
        return adView
    }

    fun createAdBanner(adView: AdView): AdView {
        adView.loadAd(AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constants.TEST_0)
                .addTestDevice(Constants.TEST_1)
                .addTestDevice(Constants.TEST_2)
                .addTestDevice(Constants.TEST_3)
                .addTestDevice(Constants.TEST_4)
                .addTestDevice(Constants.TEST_5)
                .addTestDevice(Constants.TEST_6)
                .addTestDevice(Constants.TEST_7)
                .addTestDevice(Constants.TEST_8)
                .addTestDevice(Constants.TEST_9)
                .build())
        return adView
    }

    fun createAdFull(context: Context): InterstitialAd {
        val interstitial = InterstitialAd(context)
        interstitial.adUnitId = AdmobData.instance.idAdmobFull
        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constants.TEST_0)
                .addTestDevice(Constants.TEST_1)
                .addTestDevice(Constants.TEST_2)
                .addTestDevice(Constants.TEST_3)
                .addTestDevice(Constants.TEST_4)
                .addTestDevice(Constants.TEST_5)
                .addTestDevice(Constants.TEST_6)
                .addTestDevice(Constants.TEST_7)
                .addTestDevice(Constants.TEST_8)
                .addTestDevice(Constants.TEST_9)
                .build()
        interstitial.loadAd(adRequest)
        return interstitial
    }

    @JvmOverloads
    fun displayInterstitial(interstitial: InterstitialAd?, maxNumber: Int = 100) {
        interstitial?.let {
            if (it.isLoaded) {
                val r = Random()
                val x = r.nextInt(100)
                if (x < maxNumber) {
                    it.show()
                } else {
                    //dont use LLog here
                    Log.d("interstitial", "displayInterstitial: interstitial isLoaded() but $x > $maxNumber")
                }
            } else {
                //dont use LLog here
                Log.d("interstitial", "displayInterstitial: interstitial !isLoaded()")
            }
        }
    }

    /*
     * settext marquee
     */
    fun setMarquee(tv: TextView?, text: String?) {
        tv?.let {
            it.text = text
            setMarquee(it)
        }
    }

    fun setMarquee(tv: TextView?) {
        tv?.let {
            it.isSelected = true
            it.ellipsize = TextUtils.TruncateAt.MARQUEE
            it.setSingleLine(true)
            it.marqueeRepeatLimit = -1//no limit loop
        }
    }

    fun createGradientDrawableWithRandomColor(): GradientDrawable {
        val color = LStoreUtil.getRandomColor()
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

    /*@SuppressWarnings("deprecation")
    public static void setCircleViewWithColor(View arr[]) {
        try {
            for (View view : arr) {
                view.setBackgroundDrawable(createGradientDrawableWithRandomColor());
            }
        } catch (Exception e) {
            LLog.d(TAG, "setCircleViewWithColor setBkgColor: " + e.toString());
        }
    }*/

    fun setCircleViewWithColor(view: View, colorMain: Int, colorStroke: Int) {
        try {
            view.setBackgroundDrawable(createGradientDrawableWithColor(colorMain, colorStroke))
        } catch (e: Exception) {
            Log.e(TAG, "setCircleViewWithColor setBkgColor: $e")
        }
    }

    /*
      get screenshot
       */
    /*public Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        Drawable backgroundDrawable = view.getBackground();
        Canvas canvas = new Canvas(bitmap);
        if (backgroundDrawable != null) {
            backgroundDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }*/

    fun setGradientBackground(v: View) {
        val layers = arrayOfNulls<Drawable>(1)
        val sf = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(0f, 0f, 0f, v.height.toFloat(), intArrayOf(LStoreUtil.getRandomColor(),
                        LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor()),
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

    fun setTextFromHTML(textView: TextView?, bodyData: String) {
        textView?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY)
            } else {
                it.text = Html.fromHtml(bodyData)
            }
        }
    }

    /*public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            LLog.d(TAG, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            LLog.d(TAG, "Unable to change value of shift mode");
        }
    }*/

    fun setImageFromAsset(context: Context, fileName: String, imageView: ImageView?) {
        imageView?.let { iv ->
            run {
                val drawable: Drawable?
                var stream: InputStream? = null
                try {
                    stream = context.assets.open("img/$fileName")
                    drawable = Drawable.createFromStream(stream, null)
                    drawable?.let {
                        iv.setImageDrawable(it)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "setImageFromAsset: $e")
                } finally {
                    try {
                        stream?.close()
                    } catch (e: Exception) {
                        LLog.d(TAG, "setImageFromAsset: $e")
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

    fun setTextShadow(textView: TextView?, color: Int = Color.BLACK) {
        textView?.setShadowLayer(1f, // radius
                1f, // dx
                1f, // dy
                color // shadow color
        )
    }

    fun setTextShadow(textView: TextView?) {
        textView?.setShadowLayer(1f, // radius
                1f, // dx
                1f, // dy
                Color.BLACK // shadow color
        )
    }

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

    fun setPullLikeIOSHorizontal(viewPager: ViewPager, callback: Callback?) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        val mDecor = OverScrollDecoratorHelper.setUpOverScroll(viewPager)
        callback?.let {
            mDecor.setOverScrollUpdateListener { decor, state, offset ->
                val view = decor.view
                when {
                    offset > 0 -> {
                        // 'view' is currently being over-scrolled from the top.
                        lastOffset = offset
                        isUp = true
                        //LLog.d(TAG, "________________>0 " + lastOffset + " " + isUp);
                    }
                    offset < 0 -> {
                        // 'view' is currently being over-scrolled from the bottom.
                        lastOffset = offset
                        isUp = false
                        //LLog.d(TAG, "________________<0 " + lastOffset + " " + isUp);
                    }
                    else -> {
                        // No over-scroll is in-effect.
                        // This is synonymous with having (state == STATE_IDLE).
                        //LLog.d(TAG, "________________STATE_IDLE" + lastOffset + " " + isUp);
                        if (isUp) {
                            //LLog.d(TAG, "________________ up " + lastOffset);
                            if (lastOffset > 1.8f) {
                                callback.onUpOrLeftRefresh(lastOffset)
                                LSoundUtil.startMusicFromAsset(viewPager.context, "ting.ogg")
                            } else {
                                callback.onUpOrLeft(lastOffset)
                            }
                        } else {
                            //LLog.d(TAG, "________________ down " + lastOffset);
                            if (lastOffset < -1.8f) {
                                callback.onDownOrRightRefresh(lastOffset)
                            } else {
                                callback.onDownOrRight(lastOffset)
                            }
                        }
                        lastOffset = 0f
                        isUp = false
                    }
                }
            }
        }
    }

    fun setPullLikeIOSVertical(recyclerView: RecyclerView, callback: Callback?) {
        val mDecor = OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
        if (callback != null) {
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
                val view = decor.view
                if (offset > 0) {
                    // 'view' is currently being over-scrolled from the top.
                    lastOffset = offset
                    isUp = true
                    //LLog.d(TAG, "________________>0 " + lastOffset + " " + isUp);
                } else if (offset < 0) {
                    // 'view' is currently being over-scrolled from the bottom.
                    lastOffset = offset
                    isUp = false
                    //LLog.d(TAG, "________________<0 " + lastOffset + " " + isUp);
                } else {
                    // No over-scroll is in-effect.
                    // This is synonymous with having (state == STATE_IDLE).
                    //LLog.d(TAG, "________________STATE_IDLE" + lastOffset + " " + isUp);
                    if (isUp) {
                        //LLog.d(TAG, "________________ up " + lastOffset);
                        if (lastOffset > 1.8f) {
                            callback.onUpOrLeftRefresh(lastOffset)
                            LSoundUtil.startMusicFromAsset(recyclerView.context, "ting.ogg")
                        } else {
                            callback.onUpOrLeft(lastOffset)
                        }
                    } else {
                        //LLog.d(TAG, "________________ down " + lastOffset);
                        if (lastOffset < -1.8f) {
                            callback.onDownOrRightRefresh(lastOffset)
                        } else {
                            callback.onDownOrRight(lastOffset)
                        }
                    }
                    lastOffset = 0f
                    isUp = false
                }
            }
        }
    }

    interface Callback {
        fun onUpOrLeft(offset: Float)

        fun onUpOrLeftRefresh(offset: Float)

        fun onDownOrRight(offset: Float)

        fun onDownOrRightRefresh(offset: Float)
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

    fun getColor(context: Context): Int {
        val random = Random()
        val c = random.nextInt(colors.size)
        return ContextCompat.getColor(context, colors[c])
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
    fun setTextSize(textView: TextView, typedValue: Int, size: Int) {
        if (size < 0) {
            return
        }
        textView.setTextSize(typedValue, size.toFloat())
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
    fun playYoutube(activity: Activity?, url: String) {
        activity?.let {
            if (url.isEmpty()) {
                return
            }
            it.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            LActivityUtil.tranIn(it)
        }
    }

    fun playYoutubeWithId(activity: Activity?, id: String) {
        activity?.let {
            playYoutube(it, "http://www.youtube.com/watch?v=$id")
        }
    }

    //ViewGroup.LayoutParams.MATCH_PARENT
    fun setSize(view: View, w: Int, h: Int) {
        view.layoutParams.width = w
        view.layoutParams.height = h
        view.requestLayout()
    }
}