package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Handler;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Random;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.views.overscroll.lib.overscroll.IOverScrollDecor;
import vn.loitp.views.overscroll.lib.overscroll.IOverScrollStateListener;
import vn.loitp.views.overscroll.lib.overscroll.IOverScrollUpdateListener;
import vn.loitp.views.overscroll.lib.overscroll.OverScrollDecoratorHelper;

import static vn.loitp.views.overscroll.lib.overscroll.IOverScrollState.STATE_BOUNCE_BACK;
import static vn.loitp.views.overscroll.lib.overscroll.IOverScrollState.STATE_DRAG_END_SIDE;
import static vn.loitp.views.overscroll.lib.overscroll.IOverScrollState.STATE_DRAG_START_SIDE;
import static vn.loitp.views.overscroll.lib.overscroll.IOverScrollState.STATE_IDLE;

/**
 * File created on 11/3/2016.
 *
 * @author loitp
 */
public class LUIUtil {
    private static String TAG = LUIUtil.class.getSimpleName();

    public static AdView createAdBanner(Activity activity, int adViewId) {
        AdView adView = (AdView) activity.findViewById(adViewId);
        createAdBanner(adView);
        return adView;
    }

    public static AdView createAdBanner(AdView adView) {
        adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constants.TEST_0)
                .addTestDevice(Constants.TEST_1)
                .addTestDevice(Constants.TEST_2)
                .addTestDevice(Constants.TEST_3)
                .addTestDevice(Constants.TEST_4)
                .addTestDevice(Constants.TEST_5)
                .addTestDevice(Constants.TEST_6)
                .build());
        return adView;
    }

    public static InterstitialAd createAdFull(Context context) {
        InterstitialAd interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId(context.getResources().getString(R.string.str_f));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constants.TEST_0)
                .addTestDevice(Constants.TEST_1)
                .addTestDevice(Constants.TEST_2)
                .addTestDevice(Constants.TEST_3)
                .addTestDevice(Constants.TEST_4)
                .addTestDevice(Constants.TEST_5)
                .addTestDevice(Constants.TEST_6)
                .build();
        interstitial.loadAd(adRequest);
        return interstitial;
    }

    public static void displayInterstitial(InterstitialAd interstitial) {
        displayInterstitial(interstitial, 100);
    }

    public static void displayInterstitial(InterstitialAd interstitial, int maxNumber) {
        /*if (LPref.getIsShowedGift(activity.getApplicationContext())) {
            return;
        }*/
        if (interstitial == null) {
            return;
        }
        if (interstitial.isLoaded()) {
            Random r = new Random();
            int x = r.nextInt(100);
            if (x < maxNumber) {
                interstitial.show();
            }
        }
    }

    /*
      * settext marquee
      */
    public static void setMarquee(TextView tv, String text) {
        tv.setText(text);
        setMarquee(tv);
    }

    public static void setMarquee(TextView tv) {
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
        int color = LStoreUtil.getRandomColor();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(0f);
        gradientDrawable.setStroke(1, color);
        return gradientDrawable;
    }

    public static GradientDrawable createGradientDrawableWithColor(int colorMain, int colorStroke) {
        GradientDrawable gradientDrawable = new GradientDrawable();
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
    public static void setCircleViewWithColor(View view, int colorMain, int colorStroke) {
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

    public static void setGradientBackground(View v) {
        final View view = v;
        Drawable[] layers = new Drawable[1];

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, 0, view.getHeight(), new int[]{LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor(), LStoreUtil.getRandomColor(), LStoreUtil
                        .getRandomColor()}, new float[]{0, 0.49f, 0.50f, 1}, Shader.TileMode.CLAMP);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        p.setCornerRadii(new float[]{5, 5, 5, 5, 0, 0, 0, 0});
        layers[0] = (Drawable) p;
        LayerDrawable composite = new LayerDrawable(layers);
        view.setBackgroundDrawable(composite);
    }

    public static void setTextFromHTML(TextView textView, String bodyData) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(bodyData));
        }
    }

    public static void transActivityNoAniamtion(Activity activity) {
        activity.overridePendingTransition(0, 0);
    }

    public static void transActivityFadeIn(Activity activity) {
        activity.overridePendingTransition(R.anim.tran_fade_in, R.anim.tran_fade_out);
    }

    /*public static void transActivityLeftToRightAniamtion(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void transActivityRightToLeftAniamtion(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void transActivityTopToBottomAniamtion(Activity activity) {
        activity.overridePendingTransition(0, R.anim.push_down_out);
    }

    public static void transActivityBottomToTopAniamtion(Activity activity) {
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }*/

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

    public static void setImageFromAsset(Context context, String fileName, ImageView imageView) {
        {
            Drawable drawable = null;
            InputStream stream = null;
            try {
                stream = context.getAssets().open("img/" + fileName);
                drawable = Drawable.createFromStream(stream, null);
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            } catch (Exception ignored) {
                LLog.d(TAG, "setImageFromAsset: " + ignored.toString());
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Exception ignored) {
                    LLog.d(TAG, "setImageFromAsset: " + ignored.toString());
                }
            }
        }
    }

    public static void fixSizeTabLayout(Context context, TabLayout tabLayout, String titleList[]) {
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

    public static void setTextAppearance(Context context, TextView textView, int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }

    public interface DelayCallback {
        public void doAfter(int mls);
    }

    public static void setDelay(final int mls, final DelayCallback delayCallback) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (delayCallback != null) {
                    delayCallback.doAfter(mls);
                }
            }
        }, mls);
    }

    public static void setSoftInputMode(Activity activity, int mode) {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        activity.getWindow().setSoftInputMode(mode);
    }

    public static void setLastCursorEditText(EditText editText) {
        if (editText == null) {
            return;
        }
        if (!editText.getText().toString().isEmpty()) {
            editText.setSelection(editText.getText().length());
        }
    }

    public static void removeUnderlineOfSearchView(SearchView searchView) {
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);
    }

    public static SearchView customizeWhiteSearchView(SearchView searchView, Context context, String hintText) {
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setHintTextColor(ContextCompat.getColor(context, R.color.LightGrey));

        //ImageView imgViewSearchView = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        //imgViewSearchView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.search_color));

        SpannableStringBuilder ssb = new SpannableStringBuilder("   ");
        if (hintText != null) {
            ssb.append(hintText);
        } else {
            ssb.append("Mínim 3 caràcters...");
        }

        Drawable searchIcon = ContextCompat.getDrawable(context, R.drawable.search_color);
        int textSize = (int) (searchEditText.getTextSize() * 1.05);
        searchIcon.setBounds(0, 0, textSize, textSize);
        ssb.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        searchEditText.setHint(ssb);

        ImageView close = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        close.setImageResource(R.drawable.delete_border);

        return searchView;
    }

    public static void setColorForSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.vip1,
                R.color.vip2,
                R.color.vip3,
                R.color.vip4,
                R.color.vip5);
    }

    public static void setTextShadow(TextView textView) {
        if (textView == null) {
            return;
        }
        textView.setShadowLayer(
                1f, // radius
                1f, // dx
                1f, // dy
                Color.BLACK // shadow color
        );
    }

    public static void setTextBold(TextView textBold) {
        textBold.setTypeface(null, Typeface.BOLD);
    }

    public static void printBeautyJson(Object o, TextView textView) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(o);
        textView.setText(json);
    }

    public static void setPullLikeIOSVertical(RecyclerView recyclerView) {
        //guide: https://github.com/EverythingMe/overscroll-decor

        // Horizontal
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        // Vertical
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    public static void setPullLikeIOSHorizontal(RecyclerView recyclerView) {
        //guide: https://github.com/EverythingMe/overscroll-decor

        // Horizontal
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }

    public static void setPullLikeIOSHorizontal(final ViewPager viewPager, final Callback callback) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(viewPager);
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

    public static void setPullLikeIOSVertical(final RecyclerView recyclerView, final Callback callback) {
        IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
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
        public void onUpOrLeft(float offset);

        public void onUpOrLeftRefresh(float offset);

        public void onDownOrRight(float offset);

        public void onDownOrRightRefresh(float offset);
    }

    public static void setPullLikeIOSVertical(ViewPager viewPager) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(viewPager);
    }

    public static void setPullLikeIOSVertical(ScrollView scrollView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
    }

    public static void setPullLikeIOSVertical(ListView listView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(listView);
    }

    public static void setPullLikeIOSVertical(HorizontalScrollView scrollView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
    }

    public static void setPullLikeIOSVertical(View view) {
        //guide: https://github.com/EverythingMe/overscroll-decor

        // Horizontal
        //OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        // Vertical
        OverScrollDecoratorHelper.setUpStaticOverScroll(view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }
}