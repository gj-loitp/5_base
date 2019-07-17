package vn.loitp.app.activity.demo.firebase.admob;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.VisibleForTesting;

import com.core.base.BaseFontActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import loitp.basemaster.R;

public class FirebaseAdmobActivity extends BaseFontActivity {
    private AdView mAdView;
    // [START_EXCLUDE]
    private InterstitialAd mInterstitialAd;
    private Button mLoadInterstitialButton;

    // [END_EXCLUDE]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // [END load_banner_ad]

        // AdMob ad unit IDs are not currently stored inside the google-services.json file.
        // Developers using AdMob can store them as custom values in a string resource file or
        // simply use constants. Note that the ad units used here are configured to return only test
        // ads, and should not be used outside this sample.

        // [START instantiate_interstitial_ad]
        // Create an InterstitialAd object. This same object can be re-used whenever you want to
        // show an interstitial.
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.str_f));
        // [END instantiate_interstitial_ad]

        // [START create_interstitial_ad_listener]
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                beginSecondActivity();
            }

            @Override
            public void onAdLoaded() {
                // Ad received, ready to display
                // [START_EXCLUDE]
                if (mLoadInterstitialButton != null) {
                    mLoadInterstitialButton.setEnabled(true);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onAdFailedToLoad(int i) {
                // See https://goo.gl/sCZj0H for possible error codes.
                Log.w(getTAG(), "onAdFailedToLoad:" + i);
            }
        });
        // [END create_interstitial_ad_listener]

        // [START display_interstitial_ad]
        mLoadInterstitialButton = findViewById(R.id.load_interstitial_button);
        mLoadInterstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    beginSecondActivity();
                }
            }
        });
        // [END display_interstitial_ad]

        // Disable button if an interstitial ad is not loaded yet.
        mLoadInterstitialButton.setEnabled(mInterstitialAd.isLoaded());
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
        return R.layout.activity_firebase_admob;
    }

    /**
     * Load a new interstitial ad asynchronously.
     */
    // [START request_new_interstitial]
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    // [END request_new_interstitial]

    private void beginSecondActivity() {
        Intent intent = new Intent(this, FirebaseAdmobActivity2.class);
        startActivity(intent);
    }

    // [START add_lifecycle_methods]

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        if (!mInterstitialAd.isLoaded()) {
            requestNewInterstitial();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    // [END add_lifecycle_methods]

    @VisibleForTesting
    AdView getAdView() {
        return mAdView;
    }
}
