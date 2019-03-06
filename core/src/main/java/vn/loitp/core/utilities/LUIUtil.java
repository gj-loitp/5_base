package vn.loitp.core.utilities;

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
import android.support.design.widget.NavigationView;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import loitp.core.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.uiza.UZCons;
import vn.loitp.core.loitp.uiza.UZPlayerActivity;
import vn.loitp.data.AdmobData;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.utils.util.ConvertUtils;
import vn.loitp.views.overscroll.lib.overscroll.IOverScrollDecor;
import vn.loitp.views.overscroll.lib.overscroll.IOverScrollUpdateListener;
import vn.loitp.views.overscroll.lib.overscroll.OverScrollDecoratorHelper;

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
                .addTestDevice(Constants.TEST_7)
                .addTestDevice(Constants.TEST_8)
                .addTestDevice(Constants.TEST_9)
                .build());
        return adView;
    }

    public static InterstitialAd createAdFull(Context context) {
        InterstitialAd interstitial = new InterstitialAd(context);
        interstitial.setAdUnitId(AdmobData.getInstance().getIdAdmobFull());
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
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
            //dont use LLog here
            Log.d("interstitial", "displayInterstitial err: interstitial == null");
            return;
        }
        if (interstitial.isLoaded()) {
            Random r = new Random();
            int x = r.nextInt(100);
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
        setTextShadow(textView, Color.BLACK);
    }

    public static void setTextShadow(TextView textView, int color) {
        if (textView == null) {
            return;
        }
        textView.setShadowLayer(
                1f, // radius
                1f, // dx
                1f, // dy
                color // shadow color
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

    public static void printBeautyJson(Object o, TextView textView, String tag) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(o);
        textView.setText(tag + " :\n" + json);
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

    public static void setPullLikeIOSHorizontal(ViewPager viewPager) {
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

    public static void setPullLikeIOSVertical(GridView gridView) {
        //guide: https://github.com/EverythingMe/overscroll-decor
        OverScrollDecoratorHelper.setUpOverScroll(gridView);
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

    private static int colors[] = {
            R.color.LightBlue,
            R.color.LightCoral,
            R.color.LightCyan,
            R.color.LightGoldenrodYellow,
            R.color.LightGreen,
            R.color.LightGrey,
            R.color.LightPink,
            R.color.LightSalmon,
            R.color.LightSeaGreen,
            R.color.LightSlateGray,
            R.color.LightSteelBlue,
            R.color.LightYellow,
            R.color.LightSkyBlue
    };

    public static int getColor(Context context) {
        Random random = new Random();
        int c = random.nextInt(colors.length);
        return ContextCompat.getColor(context, colors[c]);
    }

    public interface CallbackSearch {
        public void onSearch();
    }

    public static void setImeiActionSearch(EditText editText, final CallbackSearch callbackSearch) {
        if (editText == null) {
            return;
        }
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

    public static void setColorProgressBar(ProgressBar progressBar, int color) {
        if (progressBar == null) {
            return;
        }
        progressBar.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public static void setProgressBarVisibility(ProgressBar progressBar, int visibility) {
        if (progressBar == null) {
            return;
        }
        progressBar.setVisibility(visibility);
    }

    public static void setColorSeekBar(SeekBar seekBar, int color) {
        if (seekBar == null) {
            return;
        }
        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_DIP, 25);//25dp
    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_SP, 25);//25sp
    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PX, 25);//25px
    //Ex: setTextSize(tv, TypedValue.COMPLEX_UNIT_PT, 25);//25points
    public static void setTextSize(TextView textView, int typedValue, int size) {
        if (textView == null || size < 0) {
            return;
        }
        textView.setTextSize(typedValue, size);
    }

    public static void setMargins(View view, int leftPx, int topPx, int rightPx, int bottomPx) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(leftPx, topPx, rightPx, bottomPx);
            view.requestLayout();
        }
    }

    public static void setMarginsDp(View view, int leftDp, int topDp, int rightDp, int bottomDp) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(ConvertUtils.dp2px(leftDp), ConvertUtils.dp2px(topDp), ConvertUtils.dp2px(rightDp), ConvertUtils.dp2px(bottomDp));
            view.requestLayout();
        }
    }

    public static void changeTabsFont(TabLayout tabLayout, String fontName) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, fontName);
                }
            }
        }
    }

    private static String mFontForAll;

    public static void setFontForAll(String fontForAll) {
        mFontForAll = fontForAll;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(fontForAll)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static String getFontForAll() {
        return mFontForAll;
    }

    public static void setRandomBackground(View view) {
        if (view == null) {
            return;
        }
        int r = LStoreUtil.getRandomNumber(Constants.ARR_RANDOM_BKG.length);
        int bkg = Constants.ARR_RANDOM_BKG[r];
        view.setBackgroundResource(bkg);
    }

    public static void setNavMenuItemThemeColors(NavigationView navigationView, int colorDefault, int color) {
        //Setting default colors for menu item Text and Icon
        int navDefaultTextColor = colorDefault;
        int navDefaultIconColor = colorDefault;

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor
                }
        );
        navigationView.setItemTextColor(navMenuTextList);
        navigationView.setItemIconTintList(navMenuIconList);
    }

    public static ArrayList<View> getAllChildren(View v) {
        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }
        ArrayList<View> result = new ArrayList<View>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));
            result.addAll(viewArrayList);
        }
        return result;
    }

    public static int getWidthOfView(View view) {
        if (view == null) {
            return 0;
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public static int getHeightOfView(View view) {
        if (view == null) {
            return 0;
        }
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    public static void goToUZPlayerActivity(Activity activity, Data data, String admobBaner) {
        Intent intent = new Intent(activity, UZPlayerActivity.class);
        intent.putExtra(UZCons.ENTITY_DATA, data);
        intent.putExtra(UZCons.ENTITY_SHOULD_SHOW_COVER, true);
        if (admobBaner != null) {
            intent.putExtra(Constants.AD_UNIT_ID_BANNER, admobBaner);
        }
        activity.startActivity(intent);
        LActivityUtil.slideUp(activity);
    }

    //playYoutube(activity, "http://www.youtube.com/watch?v=Hxy8BZGQ5Jo");
    public static void playYoutube(Activity activity, String url) {
        if (activity == null || url == null || url.isEmpty()) {
            return;
        }
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        LActivityUtil.tranIn(activity);
    }

    public static void playYoutubeWithId(Activity activity, String id) {
        playYoutube(activity, "http://www.youtube.com/watch?v=" + id);
    }
}