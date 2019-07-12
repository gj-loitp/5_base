package com.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.core.common.Constants;
import com.data.AdmobData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utils.util.ConvertUtils;
import com.views.overscroll.lib.overscroll.IOverScrollDecor;
import com.views.overscroll.lib.overscroll.IOverScrollUpdateListener;
import com.views.overscroll.lib.overscroll.OverScrollDecoratorHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import loitp.core.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

/**
 * File created on 11/3/2016.
 *
 * @author loitp
 */
public class LUIUtil {
    private static String TAG = LUIUtil.class.getSimpleName();

    public static AdView createAdBanner(@NonNull final Activity activity, final int adViewId) {
        final AdView adView = activity.findViewById(adViewId);
        createAdBanner(adView);
        return adView;
    }

    public static AdView createAdBanner(@NonNull final AdView adView) {
        adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice(Constants.getTEST_0()).addTestDevice(Constants.getTEST_1()).addTestDevice(Constants.getTEST_2()).addTestDevice(Constants.getTEST_3()).addTestDevice(Constants.getTEST_4()).addTestDevice(Constants.getTEST_5()).addTestDevice(Constants.getTEST_6()).addTestDevice(Constants.getTEST_7()).addTestDevice(Constants.getTEST_8()).addTestDevice(Constants.getTEST_9()).build());
        return adView;
    }

    public static InterstitialAd createAdFull(@NonNull final Context context) {
        final InterstitialAd interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId(AdmobData.Companion.getInstance().getIdAdmobFull());
        final AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice(Constants.getTEST_0()).addTestDevice(Constants.getTEST_1()).addTestDevice(Constants.getTEST_2()).addTestDevice(Constants.getTEST_3()).addTestDevice(Constants.getTEST_4()).addTestDevice(Constants.getTEST_5()).addTestDevice(Constants.getTEST_6()).addTestDevice(Constants.getTEST_7()).addTestDevice(Constants.getTEST_8()).addTestDevice(Constants.getTEST_9()).build();
        interstitial.loadAd(adRequest);
        return interstitial;
    }

    public static void displayInterstitial(@NonNull final InterstitialAd interstitial) {
        displayInterstitial(interstitial, 100);
    }

    public static void displayInterstitial(@NonNull final InterstitialAd interstitial, final int maxNumber) {
        if (interstitial.isLoaded()) {
            final Random r = new Random();
            final int x = r.nextInt(100);
            if (x < maxNumber) {
                interstitial.show();
            } else {
                //dont use LLog here
                Log.d("interstitial", "displayInterstitial: interstitial isLoaded() but " + x + " > " + maxNumber);
            }
        } else {
            //dont use LLog here
            Log.d("interstitial", "displayInterstitial: interstitial !isLoaded()");
        }
    }

    /*
     * settext marquee
     */
    public static void setMarquee(@NonNull final TextView tv, @NonNull final String text) {
        tv.setText(text);
        setMarquee(tv);
    }

    public static void setMarquee(@NonNull final TextView tv) {
        tv.setSelected(true);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setSingleLine(true);
        tv.setMarqueeRepeatLimit(-1);//no limit loop
    }

  /*public void setAnimation(View v) {
    Animation mAnimation;
    mAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
    v.startAnimation(mAnimation);
  }*/

    public static GradientDrawable createGradientDrawableWithRandomColor() {
        final int color = LStoreUtil.getRandomColor();
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(0f);
        gradientDrawable.setStroke(1, color);
        return gradientDrawable;
    }

    public static GradientDrawable createGradientDrawableWithColor(final int colorMain, final int colorStroke) {
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(colorMain);
        gradientDrawable.setCornerRadius(90f);
        gradientDrawable.setStroke(3, colorStroke);
        return gradientDrawable;
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

    @SuppressWarnings("deprecation")
    public static void setCircleViewWithColor(@NonNull final View view, final int colorMain, final int colorStroke) {
        try {
            view.setBackgroundDrawable(createGradientDrawableWithColor(colorMain, colorStroke));
        } catch (Exception e) {
            LLog.d(TAG, "setCircleViewWithColor setBkgColor: " + e.toString());
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

    public static void setGradientBackground(@NonNull final View v) {
        final Drawable[] layers = new Drawable[1];
        final ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                return new LinearGradient(0, 0, 0, v.getHeight(), new int[]{LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor()},
                        new float[]{0, 0.49f, 0.50f, 1}, Shader.TileMode.CLAMP);
            }
        };
        final PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        p.setCornerRadii(new float[]{5, 5, 5, 5, 0, 0, 0, 0});
        layers[0] = p;
        final LayerDrawable composite = new LayerDrawable(layers);
        v.setBackgroundDrawable(composite);
    }

    public static void setTextFromHTML(@NonNull final TextView textView, @NonNull final String bodyData) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(bodyData));
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

    public static void setImageFromAsset(@NonNull final Context context, @NonNull final String fileName, @NonNull final ImageView imageView) {
        {
            Drawable drawable;
            InputStream stream = null;
            try {
                stream = context.getAssets().open("img/" + fileName);
                drawable = Drawable.createFromStream(stream, null);
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            } catch (Exception e) {
                LLog.d(TAG, "setImageFromAsset: " + e.toString());
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Exception e) {
                    LLog.d(TAG, "setImageFromAsset: " + e.toString());
                }
            }
        }
    }

    public static void fixSizeTabLayout(@NonNull final Context context, @NonNull final TabLayout tabLayout, @NonNull final String titleList[]) {
        if (titleList.length > 3) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        //settext allcap = false
        /*for (int tabIndex = 0; tabIndex < tabLayout.getTabCount(); tabIndex++) {
            TextView tabTextView = (TextView) (((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tabIndex)).getChildAt(1));
            tabTextView.setAllCaps(false);
            setTextAppearance(context, tabTextView, android.R.style.TextAppearance_Medium);
        }*/
    }

    public static void setTextAppearance(@NonNull final Context context, @NonNull final TextView textView, final int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }

    public interface DelayCallback {
        void doAfter(int mls);
    }

    public static void setDelay(final int mls, @NonNull final DelayCallback delayCallback) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.doAfter(mls);
            }
        }, mls);
    }

    public static void setSoftInputMode(@NonNull final Activity activity, final int mode) {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        activity.getWindow().setSoftInputMode(mode);
    }

    public static void setLastCursorEditText(@NonNull final EditText editText) {
        if (!editText.getText().toString().isEmpty()) {
            editText.setSelection(editText.getText().length());
        }
    }

    public static void setColorForSwipeRefreshLayout(@NonNull final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.vip1, R.color.vip2, R.color.vip3, R.color.vip4, R.color.vip5);
    }

    public static void setTextShadow(@NonNull final TextView textView) {
        setTextShadow(textView, Color.BLACK);
    }

    public static void setTextShadow(@NonNull final TextView textView, final int color) {
        textView.setShadowLayer(1f, // radius
                1f, // dx
                1f, // dy
                color // shadow color
        );
    }

    public static void setTextBold(@NonNull final TextView textBold) {
        textBold.setTypeface(null, Typeface.BOLD);
    }

    public static void printBeautyJson(@NonNull final Object o, @NonNull final TextView textView) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson(o);
        textView.setText(json);
    }

    public static void printBeautyJson(@NonNull final Object o, @NonNull final TextView textView, @NonNull final String tag) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String json = gson.toJson(o);
        textView.setText(tag + " :\n" + json);
    }

    public static void setPullLikeIOSVertical(@NonNull final RecyclerView recyclerView) {
        //guide: https://github.com/EverythingMe/overscroll-decor

        // Horizontal
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        // Vertical
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    public static void setPullLikeIOSHorizontal(@NonNull final RecyclerView recyclerView) {
        //guide: https://github.com/EverythingMe/overscroll-decor

        // Horizontal
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }

    public static void setPullLikeIOSHorizontal(@NonNull final ViewPager viewPager, @Nullable final Callback callback) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        final IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(viewPager);
        if (callback != null) {
            decor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
                @Override
                public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                    final View view = decor.getView();
                    if (offset > 0) {
                        // 'view' is currently being over-scrolled from the top.
                        lastOffset = offset;
                        isUp = true;
                        //LLog.d(TAG, "________________>0 " + lastOffset + " " + isUp);
                    } else if (offset < 0) {
                        // 'view' is currently being over-scrolled from the bottom.
                        lastOffset = offset;
                        isUp = false;
                        //LLog.d(TAG, "________________<0 " + lastOffset + " " + isUp);
                    } else {
                        // No over-scroll is in-effect.
                        // This is synonymous with having (state == STATE_IDLE).
                        //LLog.d(TAG, "________________STATE_IDLE" + lastOffset + " " + isUp);
                        if (isUp) {
                            //LLog.d(TAG, "________________ up " + lastOffset);
                            if (lastOffset > 1.8f) {
                                callback.onUpOrLeftRefresh(lastOffset);
                                LSoundUtil.startMusicFromAsset(viewPager.getContext(), "ting.ogg");
                            } else {
                                callback.onUpOrLeft(lastOffset);
                            }
                        } else {
                            //LLog.d(TAG, "________________ down " + lastOffset);
                            if (lastOffset < -1.8f) {
                                callback.onDownOrRightRefresh(lastOffset);
                            } else {
                                callback.onDownOrRight(lastOffset);
                            }
                        }
                        lastOffset = 0;
                        isUp = false;
                    }
                }
            });
        }
    }

    private static float lastOffset = 0.0f;
    private static boolean isUp = false;

    public static void setPullLikeIOSVertical(@NonNull final RecyclerView recyclerView, @Nullable final Callback callback) {
        final IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
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
            decor.setOverScrollUpdateListener(new IOverScrollUpdateListener() {
                @Override
                public void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset) {
                    final View view = decor.getView();
                    if (offset > 0) {
                        // 'view' is currently being over-scrolled from the top.
                        lastOffset = offset;
                        isUp = true;
                        //LLog.d(TAG, "________________>0 " + lastOffset + " " + isUp);
                    } else if (offset < 0) {
                        // 'view' is currently being over-scrolled from the bottom.
                        lastOffset = offset;
                        isUp = false;
                        //LLog.d(TAG, "________________<0 " + lastOffset + " " + isUp);
                    } else {
                        // No over-scroll is in-effect.
                        // This is synonymous with having (state == STATE_IDLE).
                        //LLog.d(TAG, "________________STATE_IDLE" + lastOffset + " " + isUp);
                        if (isUp) {
                            //LLog.d(TAG, "________________ up " + lastOffset);
                            if (lastOffset > 1.8f) {
                                callback.onUpOrLeftRefresh(lastOffset);
                                LSoundUtil.startMusicFromAsset(recyclerView.getContext(), "ting.ogg");
                            } else {
                                callback.onUpOrLeft(lastOffset);
                            }
                        } else {
                            //LLog.d(TAG, "________________ down " + lastOffset);
                            if (lastOffset < -1.8f) {
                                callback.onDownOrRightRefresh(lastOffset);
                            } else {
                                callback.onDownOrRight(lastOffset);
                            }
                        }
                        lastOffset = 0;
                        isUp = false;
                    }
                }
            });
        }
    }

    public interface Callback {
        void onUpOrLeft(float offset);

        void onUpOrLeftRefresh(float offset);

        void onDownOrRight(float offset);

        void onDownOrRightRefresh(float offset);
    }

    public static void setPullLikeIOSHorizontal(@NonNull final ViewPager viewPager) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(viewPager);
    }

    public static void setPullLikeIOSVertical(@NonNull final ScrollView scrollView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
    }

    public static void setPullLikeIOSVertical(@NonNull final ListView listView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(listView);
    }

    public static void setPullLikeIOSVertical(@NonNull final GridView gridView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(gridView);
    }

    public static void setPullLikeIOSVertical(@NonNull final HorizontalScrollView scrollView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
    }

    public static void setPullLikeIOSVertical(@NonNull final View view) {
        //guide: https://github.com/EverythingMe/overscroll-decor

        // Horizontal
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        // Vertical
        OverScrollDecoratorHelper.setUpStaticOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    private static int colors[] = {R.color.LightBlue, R.color.LightCoral, R.color.LightCyan, R.color.LightGoldenrodYellow, R.color.LightGreen, R.color.LightGrey, R.color.LightPink,
            R.color.LightSalmon, R.color.LightSeaGreen, R.color.LightSlateGray, R.color.LightSteelBlue, R.color.LightYellow, R.color.LightSkyBlue};

    public static int getColor(@NonNull final Context context) {
        final Random random = new Random();
        final int c = random.nextInt(colors.length);
        return ContextCompat.getColor(context, colors[c]);
    }

    public interface CallbackSearch {
        void onSearch();
    }

    public static void setImeiActionSearch(@NonNull final EditText editText, @Nullable final CallbackSearch callbackSearch) {
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (callbackSearch != null) {
                        callbackSearch.onSearch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public static void setColorProgressBar(@Nullable final ProgressBar progressBar, final int color) {
        if (progressBar == null) {
            return;
        }
        progressBar.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public static void setProgressBarVisibility(@Nullable final ProgressBar progressBar, final int visibility) {
        if (progressBar == null) {
            return;
        }
        progressBar.setVisibility(visibility);
    }

    public static void setColorSeekBar(@NonNull final SeekBar seekBar, final int color) {
        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 25);//25dp
    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_SP, 25);//25sp
    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PX, 25);//25px
    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PT, 25);//25points
    public static void setTextSize(@NonNull final TextView textView, final int typedValue, final int size) {
        if (size < 0) {
            return;
        }
        textView.setTextSize(typedValue, size);
    }

    public static void setMargins(@NonNull final View view, final int leftPx, final int topPx, final int rightPx, final int bottomPx) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(leftPx, topPx, rightPx, bottomPx);
            view.requestLayout();
        }
    }

    public static void setMarginsDp(@NonNull final View view, final int leftDp, final int topDp, final int rightDp, final int bottomDp) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(ConvertUtils.dp2px(leftDp), ConvertUtils.dp2px(topDp), ConvertUtils.dp2px(rightDp), ConvertUtils.dp2px(bottomDp));
            view.requestLayout();
        }
    }

    public static void changeTabsFont(@NonNull final TabLayout tabLayout, @NonNull final String fontName) {
        final ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        final int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            final ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            final int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                final View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, fontName);
                }
            }
        }
    }

    private static String mFontForAll;

    public static void setFontForAll(@NonNull final String fontForAll) {
        mFontForAll = fontForAll;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath(fontForAll).setFontAttrId(R.attr.fontPath).build());
    }

    public static String getFontForAll() {
        return mFontForAll;
    }

    public static void setRandomBackground(@NonNull final View view) {
        final int r = LStoreUtil.getRandomNumber(Constants.INSTANCE.getARR_RANDOM_BKG().length);
        final int bkg = Constants.INSTANCE.getARR_RANDOM_BKG()[r];
        view.setBackgroundResource(bkg);
    }

    public static void setNavMenuItemThemeColors(@NonNull final NavigationView navigationView, final int colorDefault, final int color) {
        //Setting default colors for menu item Text and Icon

        //Defining ColorStateList for menu item Text
        final ColorStateList navMenuTextList = new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_focused}, new int[]{android.R.attr.state_pressed}}, new int[]{color, colorDefault, colorDefault, colorDefault
                , colorDefault});

        //Defining ColorStateList for menu item Icon
        final ColorStateList navMenuIconList = new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_focused}, new int[]{android.R.attr.state_pressed}}, new int[]{color, colorDefault, colorDefault, colorDefault
                , colorDefault});
        navigationView.setItemTextColor(navMenuTextList);
        navigationView.setItemIconTintList(navMenuIconList);
    }

    public static ArrayList<View> getAllChildren(@NonNull final View v) {
        if (!(v instanceof ViewGroup)) {
            final ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }
        final ArrayList<View> result = new ArrayList<View>();
        final ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            final View child = viewGroup.getChildAt(i);
            final ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));
            result.addAll(viewArrayList);
        }
        return result;
    }

    public static int getWidthOfView(@NonNull final View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public static int getHeightOfView(@NonNull final View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    //playYoutube(activity, "http://www.youtube.com/watch?v=Hxy8BZGQ5Jo");
    public static void playYoutube(@NonNull final Activity activity, @NonNull final String url) {
        if (url.isEmpty()) {
            return;
        }
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        LActivityUtil.tranIn(activity);
    }

    public static void playYoutubeWithId(@NonNull final Activity activity, @NonNull final String id) {
        playYoutube(activity, "http://www.youtube.com/watch?v=" + id);
    }

    //ViewGroup.LayoutParams.MATCH_PARENT
    public static void setSize(@NonNull View view, final int w, final int h) {
        view.getLayoutParams().width = w;
        view.getLayoutParams().height = h;
        view.requestLayout();
    }
}