package com.function.epub.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LAnimationUtil;
import com.core.utilities.LConnectivityUtil;
import com.core.utilities.LLog;
import com.core.utilities.LPref;
import com.core.utilities.LReaderUtil;
import com.core.utilities.LUIUtil;
import com.daimajia.androidanimations.library.Techniques;
import com.function.epub.BookSection;
import com.function.epub.CssStatus;
import com.function.epub.Reader;
import com.function.epub.exception.OutOfPagesException;
import com.function.epub.exception.ReadingException;
import com.function.epub.model.BookInfo;
import com.function.epub.model.BookInfoData;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.utils.util.ConvertUtils;
import com.views.LToast;
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer;

import loitp.core.R;

public class EpubReaderReadActivity extends BaseFontActivity implements PageFragment.OnFragmentReadyListener {
    private Reader reader = new Reader();
    private boolean isSkippedToPage = false;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int pageCount = Integer.MAX_VALUE;
    private int pxScreenWidth;
    //private MenuItem searchMenuItem;
    //private SearchView searchView;
    private BookInfo bookInfo;
    private TextView tvPage;
    private TextView tvTitle;
    private ImageView ivCover;
    private RelativeLayout rlSplash;
    private AdView adView;

    private LinearLayout llGuide;

    private void setCoverBitmap() {
        boolean isCoverImageNotExists = bookInfo.isCoverImageNotExists();
        if (!isCoverImageNotExists) {
            // Not searched for coverImage for this position yet. It may exist.
            Bitmap savedBitmap = bookInfo.getCoverImageBitmap();
            if (savedBitmap != null) {
                ivCover.setImageBitmap(savedBitmap);
            } else {
                byte[] coverImageAsBytes = bookInfo.getCoverImage();
                if (coverImageAsBytes != null) {
                    Bitmap bitmap = LReaderUtil.decodeBitmapFromByteArray(coverImageAsBytes, 100, 200);
                    bookInfo.setCoverImageBitmap(bitmap);
                    bookInfo.setCoverImage(null);
                    ivCover.setImageBitmap(bitmap);
                } else {
                    // Searched and not found.
                    bookInfo.setCoverImageNotExists(true);
                    ivCover.setImageResource(LReaderUtil.getDefaultCover());
                }
            }
        } else {
            // Searched before and not found.
            ivCover.setImageResource(LReaderUtil.getDefaultCover());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pxScreenWidth = getResources().getDisplayMetrics().widthPixels;
        rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        llGuide = (LinearLayout) findViewById(R.id.ll_guide);
        LUIUtil.setTextShadow(tvTitle);
        bookInfo = BookInfoData.getInstance().getBookInfo();
        if (bookInfo == null) {
            LToast.INSTANCE.show(getActivity(), getString(R.string.err_unknow));
            onBackPressed();
        }
        setCoverBitmap();
        String titleBook = bookInfo.getTitle();
        if (titleBook == null) {
            titleBook = "Loading...";
        }
        tvTitle.setText(titleBook);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        LUIUtil.setPullLikeIOSHorizontal(mViewPager);
        tvPage = (TextView) findViewById(R.id.tv_page);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvPage.setText("" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final String adUnitId = getIntent().getStringExtra(Constants.INSTANCE.getAD_UNIT_ID_BANNER());
        //LLog.d(TAG, "adUnitId " + adUnitId);
        LinearLayout lnAdview = (LinearLayout) findViewById(R.id.ln_adview);
        if (adUnitId == null || adUnitId.isEmpty() || !LConnectivityUtil.isConnected(getActivity())) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(getActivity());
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(adUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            lnAdview.requestLayout();
            //int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
            //LLog.d(TAG, "navigationHeight " + navigationHeight);
            //LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 3);
            //LUIUtil.setMargins(lnAdview, 0, 0, 0, 0);
        }

        findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        findViewById(R.id.bt_zoom_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LAnimationUtil.play(view, Techniques.Pulse);
                PageFragment pageFragment = (PageFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                if (pageFragment != null) {
                    zoomIn(pageFragment);
                }
                PageFragment pageFragmentNext = (PageFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem() + 1);
                if (pageFragmentNext != null) {
                    zoomIn(pageFragmentNext);
                }
                PageFragment pageFragmentPrev = (PageFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem() - 1);
                if (pageFragmentPrev != null) {
                    zoomIn(pageFragmentPrev);
                }
            }
        });
        findViewById(R.id.bt_zoom_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LAnimationUtil.play(view, Techniques.Pulse);
                PageFragment pageFragment = (PageFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                if (pageFragment != null) {
                    zoomOut(pageFragment);
                }
                PageFragment pageFragmentNext = (PageFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem() + 1);
                if (pageFragmentNext != null) {
                    zoomOut(pageFragmentNext);
                }
                PageFragment pageFragmentPrev = (PageFragment) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem() - 1);
                if (pageFragmentPrev != null) {
                    zoomOut(pageFragmentPrev);
                }
            }
        });
        llGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LAnimationUtil.play(llGuide, Techniques.SlideOutLeft, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onEnd() {
                        if (llGuide != null) {
                            llGuide.setVisibility(View.GONE);
                            llGuide = null;
                        }
                    }

                    @Override
                    public void onRepeat() {
                    }

                    @Override
                    public void onStart() {
                    }
                });
            }
        });
        loadData = new LoadData();
        loadData.execute();
    }

    private LoadData loadData;

    @Override
    public View onFragmentReady(int position) {
        BookSection bookSection = null;
        try {
            bookSection = reader.readSection(position);
        } catch (ReadingException e) {
            e.printStackTrace();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            this.pageCount = e.getPageCount();
            if (isSkippedToPage) {
                LToast.INSTANCE.show(getActivity(), "Max page number is: " + this.pageCount);
            }
            mSectionsPagerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            LLog.INSTANCE.e(getTAG(), "onFragmentReady " + e.toString());
        }
        isSkippedToPage = false;
        if (bookSection != null) {
            return setFragmentView(true, bookSection.getSectionContent(), "text/html", "UTF-8"); // reader.isContentStyled
        }
        return null;
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {
        private int lastSavedPage = 0;

        @Override
        protected void onPreExecute() {
            //LLog.d(TAG, "onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //LLog.d(TAG, "doInBackground");
            try {
                // Setting optionals once per file is enough.
                reader.setMaxContentPerSection(1250);
                //reader.setMaxContentPerSection(1250 * 10);
                reader.setCssStatus(CssStatus.INCLUDE);
                reader.setIsIncludingTextContent(true);
                reader.setIsOmittingTitleTag(true);
                // This method must be called before readSection.
                reader.setFullContent(bookInfo.getFilePath());
                // int lastSavedPage = reader.setFullContentWithProgress(filePath);
                if (reader.isSavedProgressFound()) {
                    lastSavedPage = reader.loadProgress();
                }

            } catch (ReadingException e) {
                LLog.INSTANCE.e(getTAG(), "doInBackground " + e.toString());
                LToast.INSTANCE.show(getActivity(), "Error: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //LLog.d(TAG, "onPostExecute");
            super.onPostExecute(aVoid);
            LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    rlSplash.setVisibility(View.GONE);
                    rlSplash = null;
                }
            });
            if (mSectionsPagerAdapter != null) {
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
            mViewPager.setCurrentItem(lastSavedPage);
            if (lastSavedPage == 0) {
                tvPage.setText("0");
            }
            llGuide.setVisibility(View.VISIBLE);
            LLog.INSTANCE.d(getTAG(), "onPostExecute setCurrentItem " + lastSavedPage);
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (loadData == null) {
            loadData.cancel(true);
        }
        if (adView != null) {
            adView.destroy();
        }
        BookInfoData.getInstance().setBookInfo(null);
        super.onDestroy();
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_epub_reader_read;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_epub_reader, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query != null && !query.equals("")) {

                    if (TextUtils.isDigitsOnly(query)) {
                        loseFocusOnSearchView();

                        int skippingPage = Integer.valueOf(query);

                        if (skippingPage >= 0) {
                            isSkippedToPage = true;
                            mViewPager.setCurrentItem(skippingPage);
                        } else {
                            Toast.makeText(activity, "Page number can't be less than 0", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        loseFocusOnSearchView();
                        Toast.makeText(activity, "Only numbers are allowed", Toast.LENGTH_LONG).show();
                    }

                    return true;
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }*/

    /*@Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            loseFocusOnSearchView();
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onStop() {
        super.onStop();
        try {
            reader.saveProgress(mViewPager.getCurrentItem());
            LToast.INSTANCE.show(getActivity(), "Saved page: " + mViewPager.getCurrentItem() + "...");
        } catch (ReadingException e) {
            e.printStackTrace();
            LToast.INSTANCE.show(getActivity(), "Progress is not saved: " + e.getMessage());
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            LToast.INSTANCE.show(getActivity(), "Progress is not saved. Out of Bounds. Page Count: " + e.getPageCount());
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*private void loseFocusOnSearchView() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setIconified(true);
        MenuItemCompat.collapseActionView(searchMenuItem);
    }*/

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return PageFragment.newInstance(position);
        }
    }

    private final int idWebview = 696969;

    private View setFragmentView(boolean isContentStyled, String data, String mimeType, String encoding) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isContentStyled) {
            WebView webView = new WebView(getActivity());
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return true;
                }
            });
            webView.setId(idWebview);
            //webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
            webView.loadDataWithBaseURL(null, getStyledFont(data), mimeType, encoding, null);
            webView.setScrollBarSize(ConvertUtils.dp2px(2));
            webView.setLayoutParams(layoutParams);
            int size = LPref.getTextSizeEpub(getActivity());
            updateUIWevViewSize(webView, size);
            return webView;
        } else {
            ScrollView scrollView = new ScrollView(getActivity());
            scrollView.setLayoutParams(layoutParams);
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(layoutParams);
            textView.setText(Html.fromHtml(data, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    String imageAsStr = source.substring(source.indexOf(";base64,") + 8);
                    byte[] imageAsBytes = Base64.decode(imageAsStr, Base64.DEFAULT);
                    Bitmap imageAsBitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    int imageWidthStartPx = (pxScreenWidth - imageAsBitmap.getWidth()) / 2;
                    int imageWidthEndPx = pxScreenWidth - imageWidthStartPx;
                    Drawable imageAsDrawable = new BitmapDrawable(getResources(), imageAsBitmap);
                    imageAsDrawable.setBounds(imageWidthStartPx, 0, imageWidthEndPx, imageAsBitmap.getHeight());
                    return imageAsDrawable;
                }
            }, null));
            int pxPadding = ConvertUtils.dp2px(12);
            textView.setPadding(pxPadding, pxPadding, pxPadding, pxPadding);
            scrollView.addView(textView);
            return scrollView;
        }
    }

    private String getStyledFont(String html) {
        boolean addBodyStart = !html.toLowerCase().contains("<body>");
        boolean addBodyEnd = !html.toLowerCase().contains("</body");
        return "<style type=\"text/css\">@font-face {font-family: CustomFont;" +
                "src: url(\"file:///android_asset/" +
                LUIUtil.getFontForAll() +
                "\")}" +
                "body {font-family: CustomFont;font-size: medium;text-align: justify;}</style>" +
                (addBodyStart ? "<body>" : "") + html + (addBodyEnd ? "</body>" : "");
    }

    private void zoomIn(PageFragment pageFragment) {
        if (pageFragment == null || pageFragment.getView() == null) {
            //LLog.d(TAG, "getView null");
            return;
        }
        WebView webView = (WebView) pageFragment.getView().findViewById(idWebview);
        if (webView == null) {
            LLog.INSTANCE.d(getTAG(), "webView null");
            return;
        }
        WebSettings settings = webView.getSettings();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 14) {
            settings.setTextSize(WebSettings.TextSize.LARGER);
        } else {
            int size = (int) (settings.getTextZoom() * 1.1);
            if (size > 250) {
                size = 250;
            }
            //LLog.d(TAG, "webView size " + size);
            LPref.setTextSizeEpub(getActivity(), size);
            updateUIWevViewSize(webView, size);
        }
    }

    private void zoomOut(PageFragment pageFragment) {
        if (pageFragment == null || pageFragment.getView() == null) {
            //LLog.d(TAG, "getView null");
            return;
        }
        WebView webView = (WebView) pageFragment.getView().findViewById(idWebview);
        if (webView == null) {
            //LLog.d(TAG, "webView null");
            return;
        }
        WebSettings settings = webView.getSettings();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 14) {
            settings.setTextSize(WebSettings.TextSize.SMALLEST);
        } else {
            int size = (int) (settings.getTextZoom() / 1.1);
            if (size < 50) {
                size = 50;
            }
            //LLog.d(TAG, "webView size " + size);
            LPref.setTextSizeEpub(getActivity(), size);
            updateUIWevViewSize(webView, size);
        }
    }

    private void updateUIWevViewSize(WebView webView, int size) {
        WebSettings settings = webView.getSettings();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 14) {
            settings.setTextSize(WebSettings.TextSize.LARGER);
        } else {
            settings.setTextZoom(size);
        }
    }
}
