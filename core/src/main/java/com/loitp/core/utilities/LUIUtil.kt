package com.loitp.core.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.*
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.cardview.widget.CardView
import androidx.core.graphics.ColorUtils
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.loitp.R
import com.loitp.core.common.Constants
import com.loitp.core.ext.*
import com.loitp.core.utils.ConvertUtils
import com.loitp.func.wallpo.Wallpo
import com.simmorsal.recolor_project.OnReColorFinish
import com.simmorsal.recolor_project.ReColor
import com.skydoves.elasticviews.elasticAnimation
import io.github.inflationx.calligraphy3.CalligraphyUtils
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

        fun setColorSeekBar(
            seekBar: SeekBar?,
            color: Int
        ) {
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

        fun setTextSize(
            textView: TextView?,
            size: Float
        ) {
            if (size < 0 || textView == null) {
                return
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }

        fun setMargins(
            view: View?,
            leftPx: Int,
            topPx: Int,
            rightPx: Int,
            bottomPx: Int
        ) {
            view?.let {
                if (it.layoutParams is ViewGroup.MarginLayoutParams) {
                    val p = it.layoutParams as ViewGroup.MarginLayoutParams
                    p.setMargins(leftPx, topPx, rightPx, bottomPx)
                    it.requestLayout()
                }
            }
        }

        fun setMarginsDp(
            view: View?,
            leftDp: Int,
            topDp: Int,
            rightDp: Int,
            bottomDp: Int
        ) {
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

        fun changeTabsFont(
            tabLayout: TabLayout?,
            fontName: String
        ) {
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

        @Suppress("unused")
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
        fun playYoutube(
            activity: Activity?,
            url: String?
        ) {
            activity?.let { a ->
                if (url.isNullOrEmpty()) {
                    return
                }
                a.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                a.tranIn()
            }
        }

        fun playYoutubeWithId(
            activity: Activity?,
            id: String
        ) {
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
        fun setCheckBoxColor(
            checkBox: AppCompatCheckBox,
            uncheckedColor: Int,
            checkedColor: Int
        ) {
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked), // unchecked
                    intArrayOf(android.R.attr.state_checked) // checked
                ),
                intArrayOf(uncheckedColor, checkedColor)
            )
            checkBox.supportButtonTintList = colorStateList
        }

        fun setChangeStatusBarTintToDark(
            window: Window,
            shouldChangeStatusBarTintToDark: Boolean
        ) {
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
                            onScrolled?.invoke(false)
                        }
                    } else if (dy > 0) {
                        if (!isScrollDown) {
                            isScrollDown = true
                            onScrolled?.invoke(true)
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

        @Suppress("unused")
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
            duration: Int = 50,
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
                        size = getDimenValue(R.dimen.txt_medium).toFloat()
                    )
                    it.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }
        }

        fun setDrawableTintColor(
            textView: TextView,
            color: Int
        ) {
            for (drawable in textView.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
            }
        }

        @Suppress("unused")
        fun setTypeface(
            textView: TextView,
            pathFontAsset: String
        ) {
            val type = Typeface.createFromAsset(textView.context.assets, pathFontAsset)
            textView.typeface = type
        }

        fun setWallpaperAndLockScreen(
            context: Context,
            imageView: ImageView,
            isSetWallpaper: Boolean = true,
            isSetLockScreen: Boolean = true,
        ) {
            if (isSetWallpaper) {
                Wallpo.setMainScreenWallpaper(
                    context = context,
                    imageView = imageView,
                )
            }
            if (isSetLockScreen) {
                Wallpo.setLockScreenWallpaper(
                    context = context,
                    imageView = imageView,
                )
            }
        }

        fun setWallpaperAndLockScreen(
            context: Context,
            color: Int,
            isSetWallpaper: Boolean = true,
            isSetLockScreen: Boolean = true,
        ) {
            if (isSetWallpaper) {
                Wallpo.setMainScreenWallpaper(
                    context = context,
                    color = color,
                )
            }
            if (isSetLockScreen) {
                Wallpo.setLockScreenWallpaper(
                    context = context,
                    color = color,
                )
            }
        }

        fun setAlphaComponent(
            color: Int,
            alpha: Int = 50
        ): Int {
            return ColorUtils.setAlphaComponent(/* color = */ color, /* alpha = */ alpha)
        }

        fun setTextUnderline(tv: TextView) {
            tv.paintFlags = tv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }

        fun setCornerCardView(
            cardView: MaterialCardView,
            radiusTL: Float,
            radiusTR: Float,
            radiusBL: Float,
            radiusBR: Float,
        ) {
            cardView.shapeAppearanceModel = cardView.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, radiusTL)
                .setTopRightCorner(CornerFamily.ROUNDED, radiusTR)
                .setBottomRightCorner(CornerFamily.ROUNDED, radiusBL)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radiusBR)
                .build()
        }

        fun recolorStatusBarPulse(
            context: Context,
            pulseColor: Int,
            pulseSpeed: Int = 300,
            pulseCount: Int = 2,
        ) {
            val pulseColorString = java.lang.String.format("#%08X", -0x1 and pulseColor)
            ReColor(context).pulseStatusBar(
                pulseColorString,
                pulseSpeed,
                pulseCount,
            )
        }

        fun recolorStatusBar(
            context: Context,
            startColor: Int? = null,
            endColor: Int,
            duration: Int = 300,
            onReColorFinish: OnReColorFinish? = null
        ) {
            // if starting color is null, color will be automatically retrieved from status bar
            // same is true for navigation bar
            var hexColorStart: String? = null
            if (startColor == null) {
                //do nothing
            } else {
                hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
            }
            val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
            ReColor(context).setStatusBarColor(
                /* startingColor = */ hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }

        fun recolorNavigationBarPulse(
            context: Context,
            pulseColor: Int,
            pulseSpeed: Int = 300,
            pulseCount: Int = 2,
        ) {
            val pulseColorString = java.lang.String.format("#%08X", -0x1 and pulseColor)
            ReColor(context).pulseNavigationBar(
                pulseColorString,
                pulseSpeed,
                pulseCount,
            )
        }

        fun recolorNavigationBar(
            context: Context,
            startColor: Int? = null,
            endColor: Int,
            duration: Int = 300,
            onReColorFinish: OnReColorFinish? = null
        ) {
            // if starting color is null, color will be automatically retrieved from status bar
            // same is true for navigation bar
            var hexColorStart: String? = null
            if (startColor == null) {
                //do nothing
            } else {
                hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
            }
            val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
            ReColor(context).setNavigationBarColor(
                /* startingColor = */ hexColorStart,
                /* endingColor = */hexColorEnd,
                /* duration = */duration
            ).setOnReColorFinish(onReColorFinish)
        }

        fun recolor(
            view: View,
            startColor: Int,
            endColor: Int,
            duration: Int = 300,
            onReColorFinish: OnReColorFinish? = null
        ) {
            val hexColorStart = java.lang.String.format("#%08X", -0x1 and startColor)
            val hexColorEnd = java.lang.String.format("#%08X", -0x1 and endColor)
//            LLog.e("loitpp", "hexColorStart $hexColorStart")
//            LLog.e("loitpp", "hexColorEnd $hexColorEnd")
            when (view) {
                is ImageButton -> {
                    ReColor(view.context).setImageButtonColorFilter(
                        view,
                        /* startingColor = */hexColorStart,
                        /* endingColor = */hexColorEnd,
                        /* duration = */duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                is ImageView -> {
                    ReColor(view.context).setImageViewColorFilter(
                        /* imageView = */ view,
                        /* startingColor = */ hexColorStart,
                        /* endingColor = */ hexColorEnd,
                        /* duration = */ duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                is TextView -> {
                    ReColor(view.context).setTextViewColor(
                        /* textView = */ view,
                        /* startingColor = */hexColorStart,
                        /* endingColor = */hexColorEnd,
                        /* duration = */duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                is CardView -> {
                    ReColor(view.context).setCardViewColor(
                        view,
                        /* startingColor = */hexColorStart,
                        /* endingColor = */hexColorEnd,
                        /* duration = */duration
                    ).setOnReColorFinish(onReColorFinish)
                }
                else -> {
                    ReColor(view.context).setViewBackgroundColor(
                        /* view = */ view,
                        /* startingColor = */ hexColorStart,
                        /* endingColor = */ hexColorEnd,
                        /* duration = */ duration
                    ).setOnReColorFinish(onReColorFinish)
                }
            }

        }
    }
}
