package vn.loitp.app.activity.demo.epubreader;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.function.epub.BookSection;
import vn.loitp.function.epub.CssStatus;
import vn.loitp.function.epub.Reader;
import vn.loitp.function.epub.exception.OutOfPagesException;
import vn.loitp.function.epub.exception.ReadingException;

public class EpubReaderReadActivity extends BaseFontActivity implements PageFragment.OnFragmentReadyListener {
    public static final String FILE_PATH = "filePath";
    public static final String IS_WEBVIEW = "isWebView";
    private Reader reader;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private int pageCount = Integer.MAX_VALUE;
    private int pxScreenWidth;

    private boolean isPickedWebView = false;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    private boolean isSkippedToPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pxScreenWidth = getResources().getDisplayMetrics().widthPixels;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String filePath = getIntent().getExtras().getString(FILE_PATH);
            isPickedWebView = getIntent().getExtras().getBoolean(IS_WEBVIEW);

            try {
                reader = new Reader();

                // Setting optionals once per file is enough.
                reader.setMaxContentPerSection(1250);
                reader.setCssStatus(isPickedWebView ? CssStatus.INCLUDE : CssStatus.OMIT);
                reader.setIsIncludingTextContent(true);
                reader.setIsOmittingTitleTag(true);

                // This method must be called before readSection.
                reader.setFullContent(filePath);

//                int lastSavedPage = reader.setFullContentWithProgress(filePath);
                if (reader.isSavedProgressFound()) {
                    int lastSavedPage = reader.loadProgress();
                    mViewPager.setCurrentItem(lastSavedPage);
                }

            } catch (ReadingException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

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
        return R.layout.activity_epub_reader_read;
    }

    @Override
    public View onFragmentReady(int position) {

        BookSection bookSection = null;

        try {
            bookSection = reader.readSection(position);
        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            this.pageCount = e.getPageCount();

            if (isSkippedToPage) {
                Toast.makeText(activity, "Max page number is: " + this.pageCount, Toast.LENGTH_LONG).show();
            }

            mSectionsPagerAdapter.notifyDataSetChanged();
        }

        isSkippedToPage = false;

        if (bookSection != null) {
            return setFragmentView(isPickedWebView, bookSection.getSectionContent(), "text/html", "UTF-8"); // reader.isContentStyled
        }

        return null;
    }

    @Override
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
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            loseFocusOnSearchView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            reader.saveProgress(mViewPager.getCurrentItem());
            Toast.makeText(activity, "Saved page: " + mViewPager.getCurrentItem() + "...", Toast.LENGTH_LONG).show();
        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Progress is not saved: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Progress is not saved. Out of Bounds. Page Count: " + e.getPageCount(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View setFragmentView(boolean isContentStyled, String data, String mimeType, String encoding) {

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (isContentStyled) {
            WebView webView = new WebView(activity);
            webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            }

            webView.setLayoutParams(layoutParams);

            return webView;
        } else {
            ScrollView scrollView = new ScrollView(activity);
            scrollView.setLayoutParams(layoutParams);

            TextView textView = new TextView(activity);
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

            int pxPadding = dpToPx(12);

            textView.setPadding(pxPadding, pxPadding, pxPadding, pxPadding);

            scrollView.addView(textView);
            return scrollView;
        }
    }

    private void loseFocusOnSearchView() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setIconified(true);
        MenuItemCompat.collapseActionView(searchMenuItem);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

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
}
