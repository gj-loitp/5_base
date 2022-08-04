package com.loitpcore.core.utilities

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
import android.os.Looper
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
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.loitpcore.R
import com.loitpcore.core.common.Constants
import com.loitpcore.data.AdmobData
import com.loitpcore.utils.util.ConvertUtils
import com.loitpcore.views.setSafeOnClickListener
import com.skydoves.elasticviews.elasticAnimation
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.calligraphy3.CalligraphyUtils
import io.github.inflationx.viewpump.ViewPump
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.io.InputStream
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LUIUtil {
    companion object {
        private val logTag = LUIUtil::class.java.simpleName

        private var lastOffset = 0.0f
        private var isUp = false

        private val listColorLight = intArrayOf(
            R.color.lightBlue,
            R.color.lightCoral,
            R.color.lightCyan,
            R.color.lightGoldenrodYellow,
            R.color.lightGreen,
            R.color.lightGrey,
            R.color.lightPink,
            R.color.lightSalmon,
            R.color.lightSeaGreen,
            R.color.lightSlateGray,
            R.color.lightSteelBlue,
            R.color.lightYellow,
            R.color.lightSkyBlue,
            R.color.ripple_material_dark,
            R.color.grey,
            R.color.scratchEndGradient,
            R.color.lemonChiffon,
            R.color.bisque,
            R.color.hotPink,
            R.color.salmon,
            R.color.wheat,
            R.color.honeydew,
            R.color.paleGoldenrod,
            R.color.violet,
            R.color.lavender,
            R.color.gainsboro,
            R.color.orchid,
            R.color.tan,
            R.color.indianRed,
            R.color.darkKhaki,
            R.color.mediumOrchid,
            R.color.powderBlue,
            R.color.greenYellow,
            R.color.paleGreen,
            R.color.mediumPurple,
            R.color.darkSeaGreen,
            R.color.skyBlue,
            R.color.slateGray,
            R.color.oliveDrab,
            R.color.cornflowerBlue,
            R.color.mediumTurquoise,
            R.color.mediumSeaGreen,
            R.color.dodgerBlue,
            R.color.aqua,
            R.color.springGreen,
            R.color.periwinkle,
            R.color.pink,
            R.color.default_selected_day_background_start_color,
            R.color.default_selection_bar_month_title_text_color
        )

        var fontForAll: String? = null
            set(fontForAll) {
                field = fontForAll
                ViewPump.init(
                    ViewPump.builder()
                        .addInterceptor(
                            CalligraphyInterceptor(
                                CalligraphyConfig.Builder()
                                    .setDefaultFontPath(fontForAll)
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()
                            )
                        )
                        .build()
                )
            }

        private fun getListTestDevice(): ArrayList<String> {
            val listTestDevice = ArrayList<String>()
            listTestDevice.add(AdRequest.DEVICE_ID_EMULATOR)
            listTestDevice.add(Constants.TEST_22)
            listTestDevice.add(Constants.TEST_XIAOMI_REDMI_NOTE_8_PRO)
            listTestDevice.add(Constants.TEST_SAMSUNG_A50S)
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

        fun createAdFull(
            context: Context,
            onAdLoaded: ((InterstitialAd) -> Unit),
            onAdFailedToLoad: ((LoadAdError) -> Unit)? = null,
        ) {
            val requestConfiguration = RequestConfiguration.Builder()
                .setTestDeviceIds(getListTestDevice())
                .build()
            MobileAds.setRequestConfiguration(requestConfiguration)

            InterstitialAd.load(
                context,
                AdmobData.instance.idAdmobFull ?: "",
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        interstitialAd.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    // do sth
                                }
                            }
                        interstitialAd.setOnPaidEventListener {
                            // do sth
                        }
                        onAdLoaded.invoke(interstitialAd)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        onAdFailedToLoad?.invoke(loadAdError)
                    }
                }
            )
        }

        @JvmOverloads
        fun displayInterstitial(
            activity: Activity,
            interstitial: InterstitialAd?,
            maxNumber: Int = 100
        ) {
            interstitial?.let { i ->
                val r = Random()
                val x = r.nextInt(100)
                if (x < maxNumber) {
                    i.show(activity)
                } else {
                    // don't use LLog here
                    Log.d("interstitial", "displayInterstitial but $x > $maxNumber")
                }
            }
        }

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
                it.marqueeRepeatLimit = -1 // no limit loop
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
                    return LinearGradient(
                        0f, 0f, 0f, v.height.toFloat(),
                        intArrayOf(
                            LStoreUtil.randomColor,
                            LStoreUtil.randomColor, LStoreUtil.randomColor, LStoreUtil.randomColor
                        ),
                        floatArrayOf(0f, 0.49f, 0.50f, 1f), Shader.TileMode.CLAMP
                    )
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
                it.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY)
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

        fun fixSizeTabLayout(tabLayout: TabLayout, titleList: Array<String>) {
            if (titleList.size > 3) {
                tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            } else {
                tabLayout.tabMode = TabLayout.MODE_FIXED
                tabLayout.layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        }

        @Suppress("DEPRECATION")
        fun setTextAppearance(textView: TextView, resId: Int) {
            textView.setTextAppearance(resId)
        }

        fun setDelay(mls: Int, runnable: Runnable) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({ runnable.run() }, mls.toLong())
        }

        fun setSoftInputMode(activity: Activity, mode: Int) {
            // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            // activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
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
            swipeRefreshLayout?.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.vip1,
                R.color.vip2,
                R.color.vip3,
                R.color.vip4,
                R.color.vip5
            )
        }

        fun setProgressViewOffset(swipeRefreshLayout: SwipeRefreshLayout?, topMargin: Int) {
            swipeRefreshLayout?.setProgressViewOffset(false, 0, topMargin)
        }

        fun setTextShadow(textView: TextView?, color: Int?) {
            val mColor: Int = color
                ?: if (isDarkTheme()) {
                    Color.BLACK
                } else {
                    Color.WHITE
                }
            textView?.setShadowLayer(
                1f, // radius
                1f, // dx
                1f, // dy
                mColor // shadow color
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

        @SuppressLint("SetTextI18n")
        fun printBeautyJson(o: Any?, textView: TextView?, tag: String) {
            textView?.let { tv ->
                o?.let {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val json = gson.toJson(it)
                    tv.text = "$tag :\n$json"
                }
            }
        }

        fun setPullLikeIOSVertical(
            recyclerView: RecyclerView
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(
                recyclerView,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
        }

        fun setPullLikeIOSHorizontal(
            recyclerView: RecyclerView
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(
                recyclerView,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL
            )
        }

        fun setPullLikeIOSHorizontal(
            viewPager: ViewPager,
            onUpOrLeft: ((Float) -> Unit)? = null,
            onUpOrLeftRefresh: ((Float) -> Unit)? = null,
            onDownOrRight: ((Float) -> Unit)? = null,
            onDownOrRightRefresh: ((Float) -> Unit)? = null
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            val mDecor = OverScrollDecoratorHelper.setUpOverScroll(viewPager)
            mDecor.setOverScrollUpdateListener { _, _, offset ->
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
                                onUpOrLeftRefresh?.invoke(lastOffset)
                                LSoundUtil.startMusicFromAsset("ting.ogg")
                            } else {
                                onUpOrLeft?.invoke(lastOffset)
                            }
                        } else {
                            if (lastOffset < -1.8f) {
                                onDownOrRightRefresh?.invoke(lastOffset)
                            } else {
                                onDownOrRight?.invoke(lastOffset)
                            }
                        }
                        lastOffset = 0f
                        isUp = false
                    }
                }
            }
        }

        fun setPullLikeIOSVertical(
            recyclerView: RecyclerView,
            onUpOrLeft: ((Float) -> Unit)? = null,
            onUpOrLeftRefresh: ((Float) -> Unit)? = null,
            onDownOrRight: ((Float) -> Unit)? = null,
            onDownOrRightRefresh: ((Float) -> Unit)? = null
        ) {
            val mDecor = OverScrollDecoratorHelper.setUpOverScroll(
                recyclerView,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
            mDecor.setOverScrollUpdateListener { _, _, offset ->
                // val view = decor.view
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
                                onUpOrLeftRefresh?.invoke(lastOffset)
                                LSoundUtil.startMusicFromAsset("ting.ogg")
                            } else {
                                onUpOrLeft?.invoke(lastOffset)
                            }
                        } else {
                            if (lastOffset < -1.8f) {
                                onDownOrRightRefresh?.invoke(lastOffset)
                            } else {
                                onDownOrRight?.invoke(lastOffset)
                            }
                        }
                        lastOffset = 0f
                        isUp = false
                    }
                }
            }
        }

        fun setPullLikeIOSHorizontal(
            viewPager: ViewPager
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(viewPager)
        }

        fun setPullLikeIOSVertical(
            scrollView: ScrollView
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(scrollView)
        }

        fun setPullLikeIOSVertical(
            listView: ListView
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(listView)
        }

        fun setPullLikeIOSVertical(
            gridView: GridView
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(gridView)
        }

        fun setPullLikeIOSVertical(
            scrollView: HorizontalScrollView
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor
            OverScrollDecoratorHelper.setUpOverScroll(scrollView)
        }

        fun setPullLikeIOSVertical(
            view: View
        ) {
            // guide: https://github.com/EverythingMe/overscroll-decor

            // Horizontal
            // OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

            // Vertical
            OverScrollDecoratorHelper.setUpStaticOverScroll(
                view,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
        }

        fun getRandomColorLight(): Int {
            val random = Random()
            val index = random.nextInt(listColorLight.size)
            return listColorLight[index]
        }

        // it.imeOptions = EditorInfo.IME_ACTION_SEARCH
        fun setImeiActionEditText(
            editText: EditText? = null,
            imeOptions: Int,
            runnable: Runnable? = null
        ) {
            editText?.let {
                it.imeOptions = imeOptions
                it.setOnEditorActionListener(
                    TextView.OnEditorActionListener { _, actionId, _ ->
                        if (actionId == imeOptions) {
                            runnable?.run()
                            return@OnEditorActionListener true
                        }
                        false
                    }
                )
            }
        }

        fun setImeiActionSearch(
            editText: EditText? = null,
            actionSearch: Runnable? = null
        ) {
            editText?.let {
                it.imeOptions = EditorInfo.IME_ACTION_SEARCH
                it.setOnEditorActionListener(
                    TextView.OnEditorActionListener { _, actionId, _ ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            actionSearch?.run()
                            return@OnEditorActionListener true
                        }
                        false
                    }
                )
            }
        }

        fun setColorProgressBar(
            progressBar: ProgressBar? = null,
            color: Int
        ) {
            progressBar?.indeterminateDrawable?.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }

        fun setProgressBarVisibility(
            progressBar: ProgressBar? = null,
            visibility: Int
        ) {
            progressBar?.visibility = visibility
        }

        fun setColorSeekBar(seekBar: SeekBar?, color: Int) {
            seekBar?.let {
                it.progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                it.thumb.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }

        // Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 25);//25dp
        // Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_SP, 25);//25sp
        // Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PX, 25);//25px
        // Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PT, 25);//25points
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
                    p.setMargins(
                        ConvertUtils.dp2px(leftDp.toFloat()),
                        ConvertUtils.dp2px(topDp.toFloat()),
                        ConvertUtils.dp2px(rightDp.toFloat()),
                        ConvertUtils.dp2px(bottomDp.toFloat())
                    )
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

        fun setNavMenuItemThemeColors(
            navigationView: NavigationView,
            colorDefault: Int,
            color: Int
        ) {
            // Setting default colors for menu item Text and Icon

            // Defining ColorStateList for menu item Text
            val navMenuTextList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_focused),
                    intArrayOf(android.R.attr.state_pressed)
                ),
                intArrayOf(color, colorDefault, colorDefault, colorDefault, colorDefault)
            )

            // Defining ColorStateList for menu item Icon
            val navMenuIconList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_focused),
                    intArrayOf(android.R.attr.state_pressed)
                ),
                intArrayOf(color, colorDefault, colorDefault, colorDefault, colorDefault)
            )
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

        // playYoutube(activity, "http://www.youtube.com/watch?v=Hxy8BZGQ5Jo");
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
        fun setRipple(view: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                val outValue = TypedValue()
                view.context.theme.resolveAttribute(
                    android.R.attr.selectableItemBackground,
                    outValue,
                    true
                )
                view.setBackgroundResource(outValue.resourceId)
            }
        }

        @SuppressLint("RestrictedApi")
        fun setCheckBoxColor(checkBox: AppCompatCheckBox, uncheckedColor: Int, checkedColor: Int) {
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked), // unchecked
                    intArrayOf(android.R.attr.state_checked) // checked
                ),
                intArrayOf(uncheckedColor, checkedColor)
            )
            checkBox.supportButtonTintList = colorStateList
        }

        fun setChangeStatusBarTintToDark(window: Window, shouldChangeStatusBarTintToDark: Boolean) {
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

        // ViewGroup.LayoutParams.MATCH_PARENT
//        fun setSize(view: View, w: Int, h: Int) {
//            view.layoutParams.width = w
//            view.layoutParams.height = h
//            view.requestLayout()
//        }

        fun setScrollChange(
            recyclerView: RecyclerView,
            onTop: ((Unit) -> Unit)? = null,
            onBottom: ((Unit) -> Unit)? = null,
            onScrolled: ((isScrollDown: Boolean) -> Unit)? = null
        ) {
            var isScrollDown = false
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (!recyclerView.canScrollVertically(1)) {
                            onBottom?.invoke(Unit)
                        }
                        if (!recyclerView.canScrollVertically(-1)) {
                            onTop?.invoke(Unit)
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy < 0) {
                        if (isScrollDown) {
                            isScrollDown = false
                            onScrolled?.invoke(isScrollDown)
                        }
                    } else if (dy > 0) {
                        if (!isScrollDown) {
                            isScrollDown = true
                            onScrolled?.invoke(isScrollDown)
                        }
                    }
                }
            })
        }

        fun addTextChangedListener(
            editText: EditText?,
            delayInMls: Long,
            afterTextChanged: (String) -> Unit
        ) {
            if (delayInMls > 0) {
                editText?.let { et ->
                    et.addTextChangedListener(object : TextWatcher {
                        private var timer = Timer()
                        override fun afterTextChanged(editable: Editable?) {
                            timer.cancel()
                            timer = Timer()
                            timer.schedule(
                                object : TimerTask() {
                                    override fun run() {
                                        editable?.let { e ->
                                            et.post {
                                                afterTextChanged.invoke(e.toString())
                                            }
                                        }
                                    }
                                },
                                delayInMls
                            )
                        }

                        override fun beforeTextChanged(
                            charSequence: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                        }

                        override fun onTextChanged(
                            charSequence: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
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
//            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            return LSharedPrefsUtil.instance.getBoolean(Constants.IS_DARK_THEME, false)
        }

        fun setDarkTheme(isDarkTheme: Boolean) {
            if (isDarkTheme) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                LSharedPrefsUtil.instance.putBoolean(Constants.IS_DARK_THEME, true)
            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                LSharedPrefsUtil.instance.putBoolean(Constants.IS_DARK_THEME, false)
            }
        }

        fun setOnClickListenerElastic(
            view: View? = null,
            scaleX: Float = 0.8f,
            scaleY: Float = 0.8f,
            duration: Int = 100,
            runnable: Runnable? = null
        ) {
            view?.let { v ->
                v.setOnClickListener {
                    val anim = v.elasticAnimation(
                        scaleX = scaleX,
                        scaleY = scaleY,
                        duration = duration
                    ) {
                        runnable?.run()
                    }
                    anim.doAction()
                }
            }
        }

        fun setSafeOnClickListenerElastic(
            view: View? = null,
            scaleX: Float = 0.8f,
            scaleY: Float = 0.8f,
            duration: Int = 100,
            runnable: Runnable? = null
        ) {
            view?.let { v ->
                v.setSafeOnClickListener {
                    val anim = v.elasticAnimation(
                        scaleX = scaleX,
                        scaleY = scaleY,
                        duration = duration
                    ) {
                        runnable?.run()
                    }
                    anim.doAction()
                }
            }
        }

        fun Snackbar.withBackground(resId: Int): Snackbar {
//            this.view.setBackgroundColor(colorInt)
            this.view.setBackgroundResource(resId)
            return this
        }

        fun Snackbar.allowInfiniteLines(): Snackbar {
            return apply {
                (view.findViewById<View?>(R.id.snackbar_text) as? TextView?)?.let {
                    it.isSingleLine = false
                    it.setTextColor(Color.WHITE)
                    setTextSize(
                        textView = it,
                        size = LAppResource.getDimenValue(R.dimen.txt_medium).toFloat()
                    )
                    it.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }
        }

        fun setDrawableTintColor(textView: TextView, color: Int) {
            for (drawable in textView.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
            }
        }

        fun setTypeface(textView: TextView, pathFontAsset: String) {
            val type = Typeface.createFromAsset(textView.context.assets, pathFontAsset)
            textView.typeface = type
        }
    }
}
